package ma.enset.tp_3.web;

import ma.enset.tp_3.entities.Patient;
import ma.enset.tp_3.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @GetMapping(path = "/index")
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
    @GetMapping(path = "/deletePatient")
    public String delete(Long id,int page,String keyword ){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/formPatient")
    public String formPatient(){
        return "formPatient";
    }
}
