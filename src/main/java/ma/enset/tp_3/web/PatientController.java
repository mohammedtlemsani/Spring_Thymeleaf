package ma.enset.tp_3.web;

import jakarta.validation.Valid;
import ma.enset.tp_3.entities.Patient;
import ma.enset.tp_3.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @GetMapping(path = "/user/index")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0")int  page,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name = "keyword",defaultValue = "") String keyword) {
        Page<Patient> patients = patientRepository.findByNomContainsOrPrenomContainsIgnoreCase(keyword,keyword,PageRequest.of(page,size));
        model.addAttribute("patients", patients.getContent());
        model.addAttribute("pages", new int[patients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }
    @GetMapping(path = "/admin/deletePatient")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(Long id,int page,String keyword ){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/admin/formPatient")
    @PreAuthorize("hasRole('ADMIN')")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatient";
    }
    @PostMapping(path = "/admin/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/index";
    }
    @GetMapping(path = "/admin/editPatient")
    @PreAuthorize("hasRole('ADMIN')")
    public String editPatient(Model model,Long id){
        Patient patient = patientRepository.findById(id).orElse(null);
        model.addAttribute("patient",patient);
        return "editPatient";
    }
    @GetMapping(path = "/")
    public String home(){
        return "redirect:/user/index";
    }
}
