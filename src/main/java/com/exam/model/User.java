package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled = true;
    private String profile;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    private Set<UserRole> userRole = new HashSet<>();


    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Authority> set= new HashSet<>();
        this.userRole.forEach(userRole1 ->{
            set.add(new Authority(userRole1.getRole().getRoleName()));

        });


        return set;
    }

    /**
     * @return
     */
    @Override
    public String getUsername() {
        return userName;
    }


    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
