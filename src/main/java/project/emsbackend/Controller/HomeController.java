package project.emsbackend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String Homepage(HttpServletRequest request){
        return "Hello to my page!" + request.getSession().getId();
    }
}
