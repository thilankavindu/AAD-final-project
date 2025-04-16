package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String email);
}