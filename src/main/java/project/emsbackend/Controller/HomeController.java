package project.emsbackend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.emsbackend.Event.RegistrationCompleteEvent;
import project.emsbackend.Model.User;
import project.emsbackend.Service.UserService;

@RestController
@RequestMapping("/")
public class HomeController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public HomeController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.addUser(user)) {
            eventPublisher.publishEvent(new RegistrationCompleteEvent(user));
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Please verify your email.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed.");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if ("Valid".equals(result)) {
            return ResponseEntity.ok("Your account has been verified successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification token.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.verify(user));
    }
}
