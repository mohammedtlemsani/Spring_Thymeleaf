package ma.enset.tp_3.web;

import ma.enset.tp_3.entities.Patient;
import ma.enset.tp_3.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @GetMapping(path = "/index")
    public String index(Model model){
        List<Patient> patients =patientRepository.findAll();
        model.addAttribute("patients",patients);
        return "patients";
    }
}
