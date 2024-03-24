package ma.enset.tp_3.security.service;

import lombok.AllArgsConstructor;
import ma.enset.tp_3.security.entities.AppRole;
import ma.enset.tp_3.security.entities.AppUser;
import ma.enset.tp_3.security.repo.AppRoleRepository;
import ma.enset.tp_3.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImp implements AccountService{
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String confirmPassword) {
        AppUser appUser =appUserRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("this user already exist");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Password not match");
        AppUser user = AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        appUserRepository.save(user);
        return user;
    }

    @Override
    public AppRole addNewRole(String role) {
        if(appRoleRepository.findById(role).isPresent()) throw new RuntimeException("role already exist");
        return appRoleRepository.save(AppRole.builder().role(role).build());
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getAppRoles().add(appRole);
        //appUserRepository.save(appUser);
    }

    @Override
    public void RemoveRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
