package com.csaba.blog.spring_blog.controller.api;

import com.csaba.blog.spring_blog.dto.requestDto.AuthRequestDto;
import com.csaba.blog.spring_blog.dto.requestDto.AuthResponseDto;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.service.JwtService;
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

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public AuthRestController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

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
