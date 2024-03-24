package ma.enset.tp_3.security.service;

import lombok.AllArgsConstructor;
import ma.enset.tp_3.security.entities.AppRole;
import ma.enset.tp_3.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        if(appUser==null) throw new RuntimeException("User not Found");
        List<String> appRoles = appUser.getAppRoles().stream().map(u->u.getRole()).toList();
        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(Arrays.toString(appRoles.toArray()))
                .build();
       return userDetails;
    }
}
