package none.romank.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/login")
@Controller
public class LoginController {
    @GetMapping("")
    public String login(Model model) {
        return "login";
    }
    
}
