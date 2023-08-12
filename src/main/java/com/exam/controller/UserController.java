package com.exam.controller;

import com.exam.email.EmailSender;
import com.exam.exception.UserNotFoundException;
import com.exam.model.user.*;
import com.exam.service.UserService;
import com.exam.model.token.ConfirmationToken;
import com.exam.service.impl.ConfirmationTokenService;
import com.exam.email.validator.EmailBuilder;
import com.exam.email.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin("*")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private  PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private  ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailBuilder emailBuilder;

    @PostMapping("/forgot_user_name")
    public ResponseEntity<CustomResponse> forgotUserName(@RequestParam("email") String email ) throws Exception {
        boolean isValidEmail = emailValidator.test(email);
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        User user = userService.getUserByEmail(email);
        emailSender.send(
                user.getEmail(),
                emailBuilder.buildEmail(user));

        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"User Name Sent To Email",null));
    }

    @PutMapping("/update_credentials")
    public ResponseEntity<CustomResponse> updateCreds(@RequestBody User user ) throws Exception {
        User ourUser = userService.getUser(user.getUsername());
        if(ourUser==null || user.getEmail()!=ourUser.getEmail()){
            throw new IllegalStateException("You Cannot Change Username And Password");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.updateUser(user);

        emailSender.send(
                user.getEmail(),
                emailBuilder.buildEmail(user));

        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Updated Your Credentials",null));
    }


    //creating user
    @PostMapping("/")
    public ResponseEntity<CustomResponse> createUser(@RequestBody User user){

        boolean isValidEmail = emailValidator.test(user.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
            Set<UserRole> roles = new HashSet<>();

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            Role role = new Role();
            role.setRoleId(45l);
            role.setRoleName("STUDENT");

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);

            roles.add(userRole);

        try {
            user = this.userService.createUser(user,roles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );
        // TODO : Send Email
        String link = userService.getDomain()+"/v1/user/confirm?token=" + token;
        System.out.println(link);
        emailSender.send(
                user.getEmail(),
                emailBuilder.buildEmail(user.getFirstName(), link));


        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Registration Successfull",user));



    }


    @GetMapping("/resend_confirmation_email/{email}")
    public ResponseEntity<CustomResponse> resendConfirmationEmail(@RequestParam String email){
        User user = userService.getUserByEmail(email);
        if(user==null){
            throw new UserNotFoundException("User With Email "+email+"Is Not Found");
        }
        ConfirmationToken token = (confirmationTokenService.getTokenByEmail(email));
        String link = userService.getDomain()+"/v1/user/confirm?token=" + token;
        System.out.println(link);
        emailSender.send(
                user.getEmail(),
                emailBuilder.buildEmail(user.getFirstName(), link));
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Account Activation Link Sent To Your Mail",null)) ;

    }

    @GetMapping(path = "confirm")
    public ResponseEntity<CustomResponse> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),userService.confirmToken(token),null)) ;
    }

    @GetMapping("/{username}")
    public ResponseEntity<CustomResponse> getUser(@PathVariable String username){

        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"User Fetched Successfully", this.userService.getUser(username)));
    }

    @DeleteMapping("/delete_account")
    public ResponseEntity<CustomResponse> deleteUser(@RequestBody JwtRequest request){

        this.userService.deletUser(request.getUsername());
        emailSender.send(this.userService.getUser(request.getUsername()).getEmail(),"Thank you for being part og us your Account had been deleted");
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"your account deleted",null));
    }


    @PutMapping("/forgot_password")
    public ResponseEntity<CustomResponse> updatePassword(@RequestBody JwtRequest jwtRequest){

        User user = userService.getUser(jwtRequest.getUsername());
        if(user!=null){

            String link = userService.getDomain()+"/v1/user/confirm_password_email?username="+jwtRequest.getUsername()+"&password="+jwtRequest.getPassword() ;
            System.out.println(link);
            emailSender.send(
                    user.getEmail(),
                    emailBuilder.buildEmail(user.getFirstName(), link));

            return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"link sent to your mail kindly verify to update vredentials",null));

        }
        throw new IllegalStateException("No User Found With User Name "+jwtRequest.getUsername());


    }

    @GetMapping(path = "confirm_password_email")
    public ResponseEntity<CustomResponse> confirmPasswordEmail(@RequestParam("username") String username, @RequestParam("password") String password) {
        password = bCryptPasswordEncoder.encode(password);
        User user = userService.getUser(username);
        if(user!=null){
            userService.updateUser(user);
            return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Password Updated Successfully",null));
        }
        throw new IllegalStateException("invalid username");
    }
}
