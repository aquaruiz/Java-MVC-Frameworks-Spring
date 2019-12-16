package biz.bar.beerhub.services;

import io.bar.beerhub.data.models.Role;
import io.bar.beerhub.data.models.User;
import io.bar.beerhub.data.repositories.CashRepository;
import io.bar.beerhub.data.repositories.RoleRepository;
import io.bar.beerhub.data.repositories.UserRepository;
import io.bar.beerhub.errors.UsernameAlreadyExistException;
import io.bar.beerhub.services.factories.UserService;
import io.bar.beerhub.services.models.UserServiceModel;
import io.bar.beerhub.services.services.CashServiceImpl;
import io.bar.beerhub.services.services.RoleServiceImpl;
import io.bar.beerhub.services.services.UserServiceImpl;
import io.bar.beerhub.util.EscapeCharsUtilImpl;
import io.bar.beerhub.web.models.UserChangeRoleModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    private static final String USER_ID = "0000-0000-0000";
    private static final String USERNAME = "mad_hatter";
    private static final String PASSWORD = "123qwerty";
    private static final String HASHED_PASSWORD = "$2a$10$f54Op9iL/Onp4bnEqndGpO7anGwYyA/7n2FLxyeOm3OE27eqHf8sS";
    private static final String ROLE = "ROLE_CUSTOMER";
    private static final String ROLE_2 = "ROLE_WAITRESS";

    private User user;
    private UserServiceModel userServiceModel;
    private Optional<User> optionalUser;
    private Optional<Role> optionalRole;
    private Role role;

    UserRepository userRepositoryMock;
    RoleRepository roleRepositoryMock;
    CashRepository cashRepositoryMock;
    UserService userService;

    @Before
    public void before() {
        role = new Role() {{
            setAuthority(ROLE);
        }};

        user = new User();
        user.setUsername(USERNAME);
        user.setPassword(HASHED_PASSWORD);
        user.setAuthorities(new HashSet<>(){{
            add(role);
        }});

        optionalUser = Optional.of(new User() {{
            setId(USER_ID);
            setUsername(USERNAME);
            setPassword(PASSWORD);
            setAuthorities(new HashSet<>(){{
                add(role);
            }});
        }});

        userServiceModel = new UserServiceModel() {{
            setId(USER_ID);
            setUsername(USERNAME);
            setPassword(PASSWORD);
        }};

        this.userRepositoryMock = Mockito.mock(UserRepository.class);
        this.roleRepositoryMock = Mockito.mock(RoleRepository.class);
        this.cashRepositoryMock = Mockito.mock(CashRepository.class);

        userService = new UserServiceImpl(
                roleRepositoryMock,
                userRepositoryMock,
                new CashServiceImpl(cashRepositoryMock),
                new RoleServiceImpl(roleRepositoryMock, new ModelMapper()),
                new ModelMapper(),
                new EscapeCharsUtilImpl(),
                new BCryptPasswordEncoder()
        );
    }

    @Test
    public void register_withNameAndPassword_shouldRegisterNewUser() {
        UserServiceModel userServiceModel = new UserServiceModel() {{
            setId(USER_ID);
            setUsername(USERNAME);
            setPassword(PASSWORD);
        }};

        when(userRepositoryMock.findByUsername(userServiceModel.getUsername())).thenReturn(null);
        when(userRepositoryMock.saveAndFlush(any(User.class))).thenReturn(user);
        when(userRepositoryMock.count()).thenReturn(5l);
        when(roleRepositoryMock.findByAuthority("ROLE_CUSTOMER")).thenReturn(role);

        UserServiceModel savedUser = userService.register(userServiceModel);

        Assert.assertEquals(USERNAME, savedUser.getUsername());
    }

    @Test(expected = UsernameAlreadyExistException.class)
    public void register_withDuplicateUsername_shouldThrowUsernameAlreadyExistException() {
        UserServiceModel userServiceModel = new UserServiceModel() {{
            setId(USER_ID);
            setUsername(USERNAME);
            setPassword(PASSWORD);
        }};

        when(userRepositoryMock.findByUsername(any()))
                .thenReturn(new User());

        userService.register(userServiceModel);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_withNonExistingUser_shouldThrowUsernameNotFoundException() {
        when(userRepositoryMock.findByUsername(any()))
                .thenReturn(null);
        userService.loadUserByUsername(USERNAME);
    }

    @Test
    public void getAllUsers_WhenUsersInDb_shouldReturnListUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);

        List<UserServiceModel> usersModels = new ArrayList<>();
        usersModels.add(userServiceModel);

        when(userRepositoryMock.findAll())
            .thenReturn(users);

        List<UserServiceModel> savedUsers = userService.getAllUsers();

        Assert.assertEquals(savedUsers.get(0).getUsername(), usersModels.get(0).getUsername());
        Assert.assertEquals(savedUsers.size(), usersModels.size());
    }

    @Test
    public void getAllUsers_WhenNoUsersInDb_ShouldReturnEmptyList() {
        List<UserServiceModel> users = new ArrayList<>();

        when(userRepositoryMock.findAll())
                .thenReturn(new ArrayList<>());

        List<UserServiceModel> savedUsers = userService.getAllUsers();
        Assert.assertEquals(savedUsers, new ArrayList<>());
    }

    @Test
    public void addDelUserRole_WhenDeleteRole_ShouldExcludeRoleFromUser() {
        UserChangeRoleModel userChangeRoleModel = new UserChangeRoleModel() {{
            setRoleName(ROLE);
            setUserId(USER_ID);
        }};

        optionalUser.get().getAuthorities().add(role);
        when(userRepositoryMock.findById(USER_ID)).thenReturn(optionalUser);
        when(roleRepositoryMock.findByAuthority(ROLE)).thenReturn(role);

        Boolean resultDeleteRole = userService.addDelUserRole(userChangeRoleModel);
        Assert.assertEquals(true, resultDeleteRole);
    }

    @Test
    public void addDelUserRole_WhenDeleteRole_ShouldReturnTrue() {
        UserChangeRoleModel userChangeRoleModel = new UserChangeRoleModel() {{
            setRoleName(ROLE);
            setUserId(USER_ID);
        }};

        when(userRepositoryMock.findById(USER_ID)).thenReturn(optionalUser);
        when(roleRepositoryMock.findByAuthority(ROLE)).thenReturn(role);

        Boolean resultAddRole = userService.addDelUserRole(userChangeRoleModel);
        Assert.assertEquals(true, resultAddRole);
    }
}
