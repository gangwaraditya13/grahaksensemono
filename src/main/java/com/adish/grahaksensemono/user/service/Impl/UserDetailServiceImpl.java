package com.adish.grahaksensemono.user.service.Impl;

import com.adish.grahaksensemono.user.entity.User;
import com.adish.grahaksensemono.user.entity.enums.Role;
import com.adish.grahaksensemono.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByuserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        String[] roles;
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            roles = new String[]{Role.USER.name()};
        } else {
            roles = user.getRoles()
                    .stream()
                    .map(Role::name)
                    .toArray(String[]::new);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
