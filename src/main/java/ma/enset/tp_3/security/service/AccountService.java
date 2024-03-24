package ma.enset.tp_3.security.service;

import ma.enset.tp_3.security.entities.AppRole;
import ma.enset.tp_3.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username,String password,String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username,String Role);
    void RemoveRoleFromUser(String username,String role);
    AppUser loadUserByUsername(String username);
}
