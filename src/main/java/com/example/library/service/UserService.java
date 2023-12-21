package com.example.library.service;

import com.example.library.dto.UserDTO;
import com.example.library.enums.Role;
import com.example.library.model.Customer;
import com.example.library.model.LibStaff;
import com.example.library.model.User;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.LibStaffRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private LibStaffRepository libStaffRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       LibStaffRepository libStaffRepository,
                       CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.libStaffRepository = libStaffRepository;
        this.customerRepository = customerRepository;
    }

    public boolean create(User user) {
        if (userRepository.existsByLogin(user.getLogin())) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        if (user.getRoles().contains(Role.ROLE_CUSTOMER)) {
            Customer customer = new Customer();
            customer.setUserId(user.getId());
            customerRepository.save(customer);
        } else {
            LibStaff libStaff = new LibStaff();
            libStaff.setUserId(user.getId());
            libStaffRepository.save(libStaff);
        }
        return true;
    }

    public UserDTO makeDTO(String username) {
        User user = userRepository.findByLogin(username).get();
        if (user.getRoles().contains(Role.ROLE_CUSTOMER)) {
            Customer customer = customerRepository.findByUserId(user.getId()).get();
            return new UserDTO(
                    customer.getEmail(),
                    user.getLogin(),
                    customer.getPhone(),
                    customer.getStatus(),
                    customer.getBirthDate(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getPatronymic(),
                    customer.getBookAmount()
            );
        } else {
            LibStaff libStaff = libStaffRepository.findByUserId(user.getId()).get();
            return new UserDTO(
                    "no email",
                    user.getLogin(),
                    libStaff.getPhone(),
                    null,
                    null,
                    libStaff.getFirstName(),
                    libStaff.getLastName(),
                    libStaff.getPatronymic(),
                   null
            );
        }

    }
}
