package io.aquariuz.beerhub.services.services;

import io.aquariuz.beerhub.data.models.Customer;
import io.aquariuz.beerhub.data.repositories.CustomerRepository;
import io.aquariuz.beerhub.errors.Constants;
import io.aquariuz.beerhub.services.factories.CustomerService;
import io.aquariuz.beerhub.services.factories.RoleService;
import io.aquariuz.beerhub.services.models.CustomerServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Override
    public CustomerServiceModel registerCustomer(CustomerServiceModel customerServiceModel) {
        roleService.seedRolesInDb();

//        if (this.customerRepository.count() == 0){
//            customerServiceModel.setAuthorities(this.roleService.findAllRoles());
//        } else {
            customerServiceModel.setAuthorities(new LinkedHashSet<>());

            customerServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_CUSTOMER"));
//        }

        Customer customer = this.modelMapper.map(customerServiceModel, Customer.class);
        customer.setPassword(this.bCryptPasswordEncoder.encode(customerServiceModel.getPassword()));

        return this.modelMapper.map(this.customerRepository.saveAndFlush(customer), CustomerServiceModel.class);
    }

    @Override
    public CustomerServiceModel findUserByUserName(String username) {
        return this.customerRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, CustomerServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return this.userRepository
//                .findByUsername(s)
//                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
//    }
}