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

    public void updateUser(User existingUser, User updatingUser) {
        if(updatingUser.getFirstName() != null && !updatingUser.getFirstName().isEmpty())
            existingUser.setFirstName(updatingUser.getFirstName());
        if(updatingUser.getLastName() != null && !updatingUser.getLastName().isEmpty())
            existingUser.setLastName(updatingUser.getLastName());
        if(updatingUser.getEmail() != null && !updatingUser.getEmail().isEmpty())
            existingUser.setEmail(updatingUser.getEmail());
        if(updatingUser.getPhone() != null && !updatingUser.getPhone().isEmpty())
            existingUser.setPhone(updatingUser.getPhone());
        if(updatingUser.getUsername() != null && !updatingUser.getUsername().isEmpty())
            existingUser.setUsername(updatingUser.getUsername());
        if(updatingUser.getPassword() != null && !updatingUser.getPassword().isEmpty())
            existingUser.setPassword(updatingUser.getPassword());
        if(!updatingUser.getRole().equals("USER"))
            existingUser.setRole(updatingUser.getRole());
        if(updatingUser.getProfession() != null && !updatingUser.getProfession().isEmpty())
            existingUser.setProfession(updatingUser.getProfession());
        existingUser.setLocked(updatingUser.isLocked());
        existingUser.setEnabled(updatingUser.isEnabled());
        existingUser.setCredentialsExpired(updatingUser.isCredentialsExpired());
        existingUser.setExpired(updatingUser.isExpired());
        userRepository.save(existingUser);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public String verify(User user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getEmail());
        return "Failed to authenticate";
    }
}
