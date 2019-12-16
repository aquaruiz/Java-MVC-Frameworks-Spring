package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Role;
import io.bar.beerhub.data.models.User;
import io.bar.beerhub.data.repositories.RoleRepository;
import io.bar.beerhub.data.repositories.UserRepository;
import io.bar.beerhub.errors.UserRegistrationException;
import io.bar.beerhub.errors.UsernameAlreadyExistException;
import io.bar.beerhub.services.factories.CashService;
import io.bar.beerhub.services.factories.RoleService;
import io.bar.beerhub.services.factories.UserService;
import io.bar.beerhub.services.models.UserServiceModel;
import io.bar.beerhub.util.factory.EscapeCharsUtil;
import io.bar.beerhub.web.models.UserChangeRoleModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CashService cashService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final EscapeCharsUtil escapeCharsUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository,
                           CashService cashService,
                           RoleService roleService,
                           ModelMapper modelMapper,
                           EscapeCharsUtil escapeCharsUtil,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.cashService = cashService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.escapeCharsUtil = escapeCharsUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User savedUser = this.userRepository.findByUsername(username);

        if (savedUser == null) {
            throw new UsernameNotFoundException("User does not exists!");
        }

        return savedUser;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        userServiceModel = escapeCharsUtil.escapeChars(userServiceModel);
        User user = this.modelMapper.map(userServiceModel, User.class);

        User saved = this.userRepository.findByUsername(userServiceModel.getUsername());

        if (saved != null) {
            throw new UsernameAlreadyExistException("User with username " + saved.getUsername()+ " already exists!");
        }

        if (this.userRepository.count() == 0) {
            this.roleService.seedRolesInDb();
            this.cashService.initCashInDb();

            user.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        } else {
            user.setAuthorities(new HashSet<>(Set.of(this.roleRepository.findByAuthority("ROLE_CUSTOMER"))));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser;
        try {
            savedUser = this.userRepository.saveAndFlush(user);
        } catch (Exception ignored) {
            throw new UserRegistrationException("Cannot register user with username " + user.getUsername());
        }

        return this.modelMapper.map(savedUser, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean addDelUserRole(UserChangeRoleModel userChangeRoleModel) {

        User user = this.userRepository.findById(userChangeRoleModel.getUserId()).get();
        Role role = this.roleRepository.findByAuthority(userChangeRoleModel.getRoleName());

        if (user == null || role == null) {
            return false;
        }

        Set<Role> currentUserRoles = user.getAuthorities();

        if (currentUserRoles.contains(role)) {
            if (currentUserRoles.size() == 1) {
                return false;
            }

            user.getAuthorities().remove(role);
        } else {
            user.getAuthorities().add(role);
        }

        this.userRepository.saveAndFlush(user);
        return true;
    }
}
