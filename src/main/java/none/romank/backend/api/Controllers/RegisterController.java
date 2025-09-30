package none.romank.backend.api.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import none.romank.backend.api.Domain.RegistrationForm;
import none.romank.backend.api.Repositories.UserDetailsRepository;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {
    private PasswordEncoder encoder;
    private final UserDetailsRepository userRepo;

    @Autowired
    public RegisterController(PasswordEncoder encoder,UserDetailsRepository userRepo){
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    @GetMapping()
    public String getRegisterPage(Model model) {
        return "register";
    }

    @PostMapping()
    public String postRegistrationForm(RegistrationForm form) {
        userRepo.save(RegistrationForm.of(form,encoder));
        return "redirect:/login";
    }
    
}
