package com.exam.service;

import com.exam.model.user.User;
import com.exam.model.user.UserRole;

import java.util.Set;

public interface UserService {
    //creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;
    public  User getUser(String username);

    public void deletUser(Long userId);
    public void deletUser(String username);

    public void updateUser(User user);

    public User getUserByEmail(String email);

    public String confirmToken(String token);

    public String VerificationMailForPasswordUpdate(String password);

    public String getDomain();

}
