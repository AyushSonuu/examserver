package com.exam.service.impl;

import com.exam.email.EmailSender;
import com.exam.model.user.User;
import com.exam.model.user.UserRole;
import com.exam.repo.user.RoleRepository;
import com.exam.repo.user.UserRepository;
import com.exam.service.UserService;
import com.exam.model.token.ConfirmationToken;
import com.exam.email.validator.EmailBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private  ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EmailBuilder emailBuilder;

    @Autowired
    private HttpServletRequest request;


    public String getDomain() {
        String domain = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return (domain);
    }


    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {

        User local = this.userRepository.findByUsername(user.getUsername());
        User mailUser = userRepository.findByEmail(user.getEmail());
        if(local!=null || mailUser!=null ){
            if(!mailUser.isEnabled()){

                throw new IllegalStateException("please Enable your account using email Verification");

            }
      throw new IllegalStateException("User is already registered try to log in ");

        }else{
            for(UserRole ur: userRoles){
                roleRepository.save(ur.getRole());
            }
            user.getUserRole().addAll(userRoles);
            local = this.userRepository.save(user);
        }
        return local;
    }

    //getting by user name
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deletUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    /**
     * @param username
     */
    @Override
    public void deletUser(String username) {
        this.userRepository.deleteByUsername(username);

    }

    @Override
    public void updateUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            // Update properties that can be modified
//            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
//            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setProfile(user.getProfile());

            // Save the updated user
            userRepository.save(existingUser);
            emailSender.send(user.getEmail(),emailBuilder.buildEmail(user));
        }

    }


    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user!=null){
            return user;
        }
        throw new IllegalStateException("User With This Email Is Not Found");

    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        User euser = userRepository.findByEmail(confirmationToken.getUser().getEmail());
        if(euser!=null){
            euser.setEnabled(true);
        }else{
            throw new IllegalStateException("user not fopund with this email");
        }

//        appUserService.enableAppUser(
//                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

    /**
     * @return
     */
    @Override
    public String VerificationMailForPasswordUpdate(String password) {
        return null;
    }

}
