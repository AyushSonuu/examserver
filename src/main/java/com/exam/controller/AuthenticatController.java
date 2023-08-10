package com.exam.controller;

import com.exam.config.JwtUtil;
import com.exam.exception.UserNotFoundException;
import com.exam.model.JwtRequest;
import com.exam.model.JwtResponse;
import com.exam.model.User;
import com.exam.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1")
public class AuthenticatController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generate_token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try{
            System.out.println(jwtRequest.getPassword()+" "+jwtRequest.getUsername());
            this.authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
        }catch(UserNotFoundException e){
            e.printStackTrace();
            throw new Exception("User Not Found");

        }
        ///authenticated
        UserDetails userDetails =this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }



    private void authenticate(String username, String password) throws Exception {
        try{
            System.out.println(username+" "+password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch(DisabledException e){
            throw new Exception("USER DISABLED");
        }catch(BadCredentialsException e){
            throw new Exception("INVALID CREDETIALS");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @GetMapping("/current-user")
    public User getCUrrentUser(Principal principal){

        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
