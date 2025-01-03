package project.emsbackend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.emsbackend.Model.User;
import project.emsbackend.Service.UserService;

@RestController
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String Homepage(HttpServletRequest request){
        return "Hello to my page!" + request.getSession().getId();
    }
    @PostMapping("/register")
    private ResponseEntity<String > register(@RequestBody User user){
        if(userService.addUser(user))
            return new ResponseEntity<>("User successfully added.", HttpStatus.OK);

        return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return new ResponseEntity<>(userService.verify(user), HttpStatus.OK);
    }
}
