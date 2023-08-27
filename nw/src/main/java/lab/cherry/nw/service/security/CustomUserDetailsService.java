package lab.cherry.nw.service.security;


import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        UserEntity user = userRepository
                .findByUserId(userid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userid : " + userid));

        RoleEntity role = user.getRole();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserid())
                .password(user.getPassword())
                .authorities(getSimpleGrantedAuthorities(role))
                .accountExpired(false)
                .accountLocked(false)
                .disabled(false)
                .credentialsExpired(false)
                .build();

    }
        private Set<GrantedAuthority> getSimpleGrantedAuthorities(RoleEntity role){
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return grantedAuthorities;
    }
}