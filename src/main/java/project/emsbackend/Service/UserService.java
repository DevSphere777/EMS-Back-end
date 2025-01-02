package project.emsbackend.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.emsbackend.Model.User;
import project.emsbackend.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final JWTService jwtService;
    UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    public UserService(JWTService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public boolean addUser(User user) {
        if(!userRepository.existsByEmail(user.getEmail()) && !userRepository.existsByPhone(user.getPhone())) {
            user.setUsername(user.getLastName() + user.getFirstName().charAt(0)
                    + (Integer.parseInt(String.valueOf(user.getPhone().charAt(user.getPhone().length()-1))) + 4));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public String verify(User user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if( authentication.isAuthenticated())
            return jwtService.generateToken(user.getEmail());
        return "fail";
    }
}
