package project.emsbackend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return ResponseEntity.noContent().build();
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
            return new ResponseEntity<>("User successfully added.", HttpStatus.CREATED);
        return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    private ResponseEntity<String> updateUser(@PathVariable long id,@RequestBody User user){
        User existingUser = userService.getUserById(id);
        if(existingUser == null)
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
        userService.updateUser(user);
        return new ResponseEntity<>("User successfully updated.", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<String> deleteUser(@PathVariable long id){
        if(userService.getUserById(id) == null)
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
        userService.deleteUser(id);
        return new ResponseEntity<>("User successfully deleted.", HttpStatus.OK);
    }
}
