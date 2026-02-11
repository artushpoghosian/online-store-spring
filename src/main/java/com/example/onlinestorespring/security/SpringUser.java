package com.example.onlinestorespring.security;

import com.example.onlinestorespring.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public class SpringUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public SpringUser(User user) {
        super(user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + user.getUserType().name()));
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpringUser that = (SpringUser) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }
}