package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Users;
import ru.skypro.homework.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostgresUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        List<SimpleGrantedAuthority> authorityList = Arrays.asList(new SimpleGrantedAuthority("users"));

        return new User(users.getUsername(), users.getPassword(), authorityList);
    }
}
