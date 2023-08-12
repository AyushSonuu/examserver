package com.exam.service.impl;

import com.exam.repo.user.ConfirmationTokenRepository;
import com.exam.model.token.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    private  ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
    public ConfirmationToken getTokenByEmail(String email){
        ConfirmationToken confirmationToken =  confirmationTokenRepository.findByUserEmail(email);
        return confirmationToken;
    }

}
