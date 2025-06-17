package com.example.blogbackend.service;

import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList; // For authorities if not using roles extensively yet

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        // For simplicity, using an empty list of authorities.
        // In a real app, you would map user roles/permissions to GrantedAuthority objects.
        // Example: List<GrantedAuthority> authorities = user.getRoles().stream()
        //              .map(role -> new SimpleGrantedAuthority(role.getName()))
        //              .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>() // Empty authorities list for now
        );
    }
}
