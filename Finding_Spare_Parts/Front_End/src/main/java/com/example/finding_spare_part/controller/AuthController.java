package com.example.finding_spare_part.controller;

import com.example.finding_spare_part.dto.AuthDTO;
import com.example.finding_spare_part.dto.ResponseDTO;
import com.example.finding_spare_part.dto.UserDTO;
import com.example.finding_spare_part.service.impl.UserServiceImpl;
import com.example.finding_spare_part.util.JwtUtil;
import com.example.finding_spare_part.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;

    // Constructor injection
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Authentication Error", e.getMessage()));
        }

        UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail(loadedUser.getEmail());
        authDTO.setToken(token);
        authDTO.setRole(loadedUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", authDTO));
    }
}