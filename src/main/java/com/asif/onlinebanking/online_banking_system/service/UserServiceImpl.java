package com.asif.onlinebanking.online_banking_system.service;

import com.asif.onlinebanking.online_banking_system.dto.AuthRequest;
import com.asif.onlinebanking.online_banking_system.dto.AuthResponse;
import com.asif.onlinebanking.online_banking_system.dto.RegisterRequest;
import com.asif.onlinebanking.online_banking_system.entity.Role;
import com.asif.onlinebanking.online_banking_system.entity.User;
import com.asif.onlinebanking.online_banking_system.repository.RoleRepository;
import com.asif.onlinebanking.online_banking_system.repository.UserRepository;
import com.asif.onlinebanking.online_banking_system.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Override
    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists !");
        }
        User user=new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword());
        user.setUsername(request.getEmail());

        Role userRole=roleRepository.findByName("ROLE_USER")
                .orElseGet(()->{
                    Role r=new Role();
                    r.setName("ROLE_USER");
                    return roleRepository.save(r);
                });

        Set<Role>roles=new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        String token=jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        String token= jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
