package com.viendong.BUOI8.service;

import com.viendong.BUOI8.Role;
import com.viendong.BUOI8.model.User;
import com.viendong.BUOI8.repository.IRoleRepository;
import com.viendong.BUOI8.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void setDefaultRole(String username) {
        userRepository.findByUsername(username).ifPresentOrElse(user -> {
            user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
            userRepository.save(user);
        }, () -> {
            throw new UsernameNotFoundException("User  not found");
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User  not found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }

    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void assignRoleToUser(String username, Role roleToAssign) {
        if (!isCurrentUserSuperAdmin()) {
            throw new AccessDeniedException("You do not have permission to assign roles.");
        }

        userRepository.findByUsername(username).ifPresent(user -> {
            // Kiểm tra nếu vai trò đã được gán cho người dùng chưa
            if (!user.getRoles().contains(roleToAssign)) {
                user.getRoles().add(roleRepository.findRoleById(roleToAssign.value));
                userRepository.save(user);
            }
        });
    }

    private boolean isCurrentUserSuperAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SUPER_ADMIN"));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}