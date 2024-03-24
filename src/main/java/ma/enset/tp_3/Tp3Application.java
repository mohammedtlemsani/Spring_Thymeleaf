package ma.enset.tp_3;

import ma.enset.tp_3.entities.Patient;
import ma.enset.tp_3.repository.PatientRepository;
import ma.enset.tp_3.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class Tp3Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp3Application.class, args);
    }
    //@Bean
    public CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            Patient p1 = new Patient(null,"hamid","hamd",new Date());
            Patient p2 = new Patient(null,"zaid","mouadib",new Date());
            Patient p3 = new Patient(null,"soulaimane","chekaoui",new Date());
            patientRepository.save(p1);patientRepository.save(p2);patientRepository.save(p3);
            patientRepository.findAll().forEach(System.out::println);
        };

    }
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
    return args -> {
        UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("hamid");
        if(u1==null) {
            jdbcUserDetailsManager.createUser(User.withUsername("hamid").password(passwordEncoder().encode("1234")).roles("USER").build());
        }
        UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("tlemsani");
        if(u2==null) {
            jdbcUserDetailsManager.createUser(User.withUsername("tlemsani").password(passwordEncoder().encode("1234")).roles("ADMIN", "USER").build());
        }
    };
    }
    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService) {
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("hamid","1234","1234");
            accountService.addNewUser("tlemsani","1234","1234");
            accountService.addRoleToUser("hamid","USER");
            accountService.addRoleToUser("tlemsani","ADMIN");
            accountService.addRoleToUser("tlemsani","USER");

        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };
}
