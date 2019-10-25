package org.campusmarket.app.models;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CmarketUserDetails extends User implements UserDetails
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CmarketUserDetails(final User user)
    {
         super(user.getUsername(),
               user.getPassword(),
               user.getFirstname(),
               user.getLastname(),
               user.getEmail(),
               user.getUniversity(),
               user.getAdmin());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}