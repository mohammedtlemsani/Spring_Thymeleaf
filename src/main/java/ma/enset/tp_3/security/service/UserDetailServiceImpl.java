package ma.enset.tp_3.security.service;

import lombok.AllArgsConstructor;
import ma.enset.tp_3.security.entities.AppRole;
import ma.enset.tp_3.security.entities.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        if(appUser==null) throw new RuntimeException("User not Found");
        List<GrantedAuthority> authorities = appUser.getAppRoles().stream().map(u -> new SimpleGrantedAuthority(u.getRole())).collect(Collectors.toList());
        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(authorities)
                .build();
       return userDetails;
    }
}
