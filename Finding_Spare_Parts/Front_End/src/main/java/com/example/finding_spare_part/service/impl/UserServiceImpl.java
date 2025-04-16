package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.entity.User;
import com.example.finding_spare_part.service.UserService;
import com.example.finding_spare_part.dto.UserDTO;
import com.example.finding_spare_part.repo.UserRepo;
import com.example.finding_spare_part.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        try {
            if (userRepo.existsByEmail(userDTO.getEmail())) {
                return VarList.Not_Acceptable;  // Email already exists
            } else {
                // Hash the password
                String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
                userDTO.setPassword(encodedPassword);  // Set the encoded password

                // Map DTO to entity
                User user = modelMapper.map(userDTO, User.class);

                // Ensure UUID is set before saving
                user.ensureId();

                // Save the user
                userRepo.save(user);
                return VarList.Created;  // User successfully created
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public UserDTO searchUser(String email) {
        if (userRepo.existsByEmail(email)) {
            User user = userRepo.findByEmail(email);
            return modelMapper.map(user, UserDTO.class);
        } else {
            return null;  // Return null if user doesn't exist
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthority(user)
        );
    }

    public UserDTO loadUserDetailsByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }
}