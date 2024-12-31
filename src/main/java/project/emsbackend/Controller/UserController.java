package project.emsbackend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.emsbackend.Model.User;
import project.emsbackend.Service.UserService;

import java.util.List;
@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/all")
    private ResponseEntity<List<User>> getAllUsers(){
        if(userService.getUsers().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    private ResponseEntity<User> getUserById(@PathVariable long id){
        if(userService.getUserById(id) == null)
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("")
    private ResponseEntity<String > addUser(@RequestBody User user){
        if(userService.addUser(user))
            return new ResponseEntity<>("User successfully added.", HttpStatus.OK);
        return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    private ResponseEntity<String> updateUser(@PathVariable long id,@RequestBody User user){
        User existingUser = userService.getUserById(id);
        if(existingUser == null)
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
            else{
                if(user.getFirstName() != null && !user.getFirstName().isEmpty())
                    existingUser.setFirstName(user.getFirstName());
                if(user.getLastName() != null && !user.getLastName().isEmpty())
                    existingUser.setLastName(user.getLastName());
                if(user.getEmail() != null && !user.getEmail().isEmpty())
                    existingUser.setEmail(user.getEmail());
                if(user.getPhone() != null && !user.getPhone().isEmpty())
                    existingUser.setPhone(user.getPhone());
                if(user.getUsername() != null && !user.getUsername().isEmpty())
                    existingUser.setUsername(user.getUsername());
                if(user.getPassword() != null && !user.getPassword().isEmpty())
                    existingUser.setPassword(user.getPassword());
                if(!user.getRole().equals("USER"))
                    existingUser.setRole(user.getRole());
                if(user.getProfession() != null && !user.getProfession().isEmpty())
                    existingUser.setProfession(user.getProfession());
                user.setEnabled(existingUser.isEnabled());
                userService.updateUser(existingUser);
                return new ResponseEntity<>("User successfully updated.", HttpStatus.OK);
            }
    }

    @DeleteMapping("{id}")
    private ResponseEntity<String> deleteUser(@PathVariable long id){
        if(userService.getUserById(id) == null)
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
        userService.deleteUser(id);
        return new ResponseEntity<>("User successfully deleted.", HttpStatus.OK);
    }

}
