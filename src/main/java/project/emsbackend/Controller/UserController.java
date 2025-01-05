package project.emsbackend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.emsbackend.Model.Assignment;
import project.emsbackend.Model.User;
import project.emsbackend.Service.UserService;

import java.util.List;
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
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


    @PutMapping("{id}")
    private ResponseEntity<String> updateUser(@PathVariable long id,@RequestBody User user){
        User existingUser = userService.getUserById(id);
        if(existingUser == null)
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
            else{
                userService.updateUser(existingUser, user);
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

    @GetMapping("{id}/assignment")
    private ResponseEntity<List<Assignment>> getAssignments(@PathVariable long id){
        if(userService.getAssignmentById(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(userService.getAssignmentById(id), HttpStatus.OK);
    }

}
