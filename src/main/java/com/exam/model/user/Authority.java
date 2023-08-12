package com.exam.model.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class Authority implements GrantedAuthority {

    private String authority;


    /**
     * @return
     */
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
