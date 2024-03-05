package ma.enset.tp_3;

import ma.enset.tp_3.entities.Patient;
import ma.enset.tp_3.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Tp3Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp3Application.class, args);
    }
    @Bean
    public CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            Patient p1 = new Patient(null,"hamid","hamd",new Date());
            Patient p2 = new Patient(null,"zaid","mouadib",new Date());
            Patient p3 = new Patient(null,"soulaimane","chekaoui",new Date());
            patientRepository.save(p1);patientRepository.save(p2);patientRepository.save(p3);
            patientRepository.findAll().forEach(System.out::println);
        };
    }
}
