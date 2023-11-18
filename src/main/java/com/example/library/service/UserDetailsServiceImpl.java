package com.example.library.service;

import com.example.library.model.UserDetailsImpl;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //TODO: Тут надо убрать репу юзеров, у нас в бд нету юзера, есть только стафф и кастомер
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository
                .findByLogin(login)
                .map(UserDetailsImpl::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + login)
                );
    }
}
