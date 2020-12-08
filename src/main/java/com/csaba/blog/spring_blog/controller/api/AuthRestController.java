package com.csaba.blog.spring_blog.controller.api;

import com.csaba.blog.spring_blog.dto.requestDto.AuthRequestDto;
import com.csaba.blog.spring_blog.dto.requestDto.AuthResponseDto;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(405).body("Error: Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDto.getUsername());

        if (!((BlogUser) userDetails).isAdmin()) {
            return ResponseEntity.status(405).body("You haven't got admin role!");
        }

        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDto(userDetails.getUsername(), jwt));
    }
}
