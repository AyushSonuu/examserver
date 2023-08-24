package com.exam.controller;

import com.exam.email.EmailSender;
import com.exam.exception.UserNotFoundException;
import com.exam.model.user.*;
import com.exam.service.StudentTeacherService;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Controller class responsible for handling user-related operations and endpoints.
 * This class acts as an interface between the front-end user interface and the backend services
 * for user management, registration, and authentication.
 */
@RestController
@RequestMapping("/v1/user")
@CrossOrigin("*")
public class UserController {

    // Injecting services and components through dependency injection

    /**
     * Service for handling user-related operations.
     */
    @Autowired
    private UserService userService;

    /**
     * Password encoder for securely hashing and checking passwords.
     */
    @Autowired
    private  PasswordEncoder bCryptPasswordEncoder;

    /**
     * Validator for email addresses to ensure their validity.
     */
    @Autowired
    private EmailValidator emailValidator;

    /**
     * Component for sending email notifications.
     */
    @Autowired
    private EmailSender emailSender;

    /**
     * Service for handling confirmation tokens for user registration.
     */
    @Autowired
    private  ConfirmationTokenService confirmationTokenService;

    /**
     * Builder for creating email content.
     */
    @Autowired
    private EmailBuilder emailBuilder;

    @Autowired
    private StudentTeacherService studentTeacherService;

    /**
     * Initiates the process of recovering a forgotten username by sending an email to the user.
     *
     * @param email The email address of the user requesting the forgotten username.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the operation's success,
     *         and null data. Returns 200 OK if the email is valid and the process starts successfully.
     * @throws IllegalStateException If the provided email is not valid.
     * @throws Exception If there's an issue with sending the email.
     */
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

    /**
     * Updates user credentials (password) and sends an email notification.
     *
     * @param user The User object containing the updated credentials.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the operation's success,
     *         and null data. Returns 200 OK if the user's credentials are updated and the email is sent successfully.
     * @throws IllegalStateException If the provided username does not exist or the email doesn't match the user's email.
     * @throws Exception If there's an issue with updating the credentials or sending the email.
     */
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

    /**
     * Creates a new user account, sends a confirmation email, and returns the registered user's details.
     *
     * @param user The User object representing the new user's details.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the registration's success,
     *         and the registered user's details. Returns 200 OK if the registration process is successful.
     * @throws IllegalStateException If the provided email is not valid.
     * @throws Exception If there's an issue with creating the user or sending the confirmation email.
     */
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

    /**
     * Resends a confirmation email for account activation.
     *
     * @param email The email address of the user requesting the email resend.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the email resend's success,
     *         and null data. Returns 200 OK if the email resend process starts successfully.
     * @throws UserNotFoundException If the user with the given email is not found.
     */
    @GetMapping("/resend_confirmation_email/{email}")
    public ResponseEntity<CustomResponse> resendConfirmationEmail(@RequestParam String email){
        User user = userService.getUserByEmail(email);
        if(user==null){
            throw new UserNotFoundException("User With Email "+email+"Is Not Found");
        }
        ConfirmationToken token = (confirmationTokenService.getTokenByEmail(email));
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationTokenService.saveConfirmationToken(token);
        String link = userService.getDomain()+"/v1/user/confirm?token=" + token.getToken();
        System.out.println(link);
        emailSender.send(
                user.getEmail(),
                emailBuilder.buildEmail(user.getFirstName(), link));
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Account Activation Link Sent To Your Mail",null)) ;

    }

    /**
     * Confirms a user's registration using a confirmation token.
     *
     * @param token The confirmation token sent to the user's email.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the confirmation's success,
     *         and null data. Returns 200 OK if the confirmation process is successful.
     * @throws IllegalStateException If the confirmation token is not found or has expired.
     */
    @GetMapping(path = "confirm")
    public ResponseEntity<CustomResponse> confirm(@RequestParam("token") String token) {
        String token1 = userService.confirmToken(token);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),token1,null)) ;
    }

    /**
     * Retrieves user details by username.
     *
     * @param username The username of the user to retrieve.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the retrieval's success,
     *         and the retrieved user's details. Returns 200 OK if the user details are fetched successfully.
     * @throws IllegalStateException If the user with the given username is not found.
     */
    @GetMapping("/{username}")
    public ResponseEntity<CustomResponse> getUser(@PathVariable String username){
        User user = this.userService.getUser(username);
        if(user==null){
            throw new IllegalStateException("User Not Found Exception");
        }
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"User Fetched Successfully", user));
    }

    /**
     * Deletes a user account and sends a deletion email notification.
     *
     * @param request The JwtRequest containing the username of the user to be deleted.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the deletion's success,
     *         and null data. Returns 200 OK if the user account is deleted successfully.
     * @throws UserNotFoundException If the user with the given username is not found.
     */
    @DeleteMapping("/delete_account")
    public ResponseEntity<CustomResponse> deleteUser(@RequestBody JwtRequest request){

        this.userService.deletUser(request.getUsername());
        emailSender.send(this.userService.getUser(request.getUsername()).getEmail(),"Thank you for being part of us your Account had been deleted");
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"your account deleted",null));
    }

    /**
     * Initiates the process of updating a forgotten password by sending an email to the user.
     *
     * @param jwtRequest The JwtRequest containing the username for password update.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the email sending's success,
     *         and null data. Returns 200 OK if the email is sent successfully.
     * @throws IllegalStateException If no user is found with the given username.
     */
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

    /**
     * Confirms updating the password based on an email link.
     *
     * @param username The username of the user requesting password update.
     * @param password The new password.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the password update's success,
     *         and null data. Returns 200 OK if the password is updated successfully.
     * @throws IllegalStateException If no user is found with the given username.
     */
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




    // student teacher related controleer
    /**
     * Creates a new user account, sends a confirmation email, and returns the registered user's details.
     *
     * @param user The User object representing the new user's details.
     * @return A ResponseEntity containing a CustomResponse with a timestamp, a message indicating the registration's success,
     *         and the registered user's details. Returns 200 OK if the registration process is successful.
     * @throws IllegalStateException If the provided email is not valid.
     * @throws Exception If there's an issue with creating the user or sending the confirmation email.
     */
    @PostMapping("/teacher/")
    public ResponseEntity<CustomResponse> createTeacher(@RequestBody User user){

        boolean isValidEmail = emailValidator.test(user.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        Set<UserRole> roles = new HashSet<>();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Role role = new Role();
        role.setRoleId(46l);
        role.setRoleName("TEACHER");

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


        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Registration Successfull Done as Teacher",user));



    }

    @PostMapping("/teacher/{teacherUsername}/{studentUsername}")
    public ResponseEntity<CustomResponse> associateStudentToTeacher(@PathVariable("teacherUsername") String teacherUsername, @PathVariable("studentUsername") String studentUsername){
        User teacher = userService.getUser(teacherUsername);
        User student = userService.getUser(studentUsername);
        studentTeacherService.createRelationshipBetweenStudentAndTeacher(student.getId(),teacher.getId());

    return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"student Added to the teacher",null));
    }

    @GetMapping("/teacher/{teacherUsername}/students")
    public ResponseEntity<CustomResponse> getStudentsOfTeacher(@PathVariable("teacherUsername") String teacherUsername) {
        User teacher = userService.getUser(teacherUsername);
        List<User> students = studentTeacherService.getStudentsOfTeacher(teacher.getId());
    return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"all students of this teacher",students));
    }
}
