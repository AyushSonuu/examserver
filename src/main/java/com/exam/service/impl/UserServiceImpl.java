package com.exam.service.impl;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /*
    creating user
     */
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {

        User local = this.userRepository.findByUserName(user.getUserName());
        if(local!=null){
            System.out.println("User is already there");
            throw new Exception("User Already Preasent");

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
        return this.userRepository.findByUserName(username);
    }

    /**
     * @param userId
     */
    @Override
    public void deletUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    /**
     * @param user
     */
    @Override
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);

        if (existingUser != null) {
            // Update properties that can be modified
            existingUser.setUserName(user.getUserName());
            existingUser.setPassword(user.getPassword());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setEnabled(user.isEnabled());
            existingUser.setProfile(user.getProfile());

            // Save the updated user
            userRepository.save(existingUser);
        }

    }
}
