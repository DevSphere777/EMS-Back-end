package project.emsbackend.Service;

import org.springframework.stereotype.Service;
import project.emsbackend.Model.User;
import project.emsbackend.Repository.UserRepository;

@Service
public class VerificationTokenService {
    private final UserRepository userRepository;

    public VerificationTokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void createVerificationToken(User user, String token) {
        user.setMailVerificationToken(token);
        userRepository.save(user);
    }

    public String validateVerificationToken(String token) {
        User user = userRepository.findByMailVerificationToken(token);
        if (user != null) {
            return "Invalid verification token";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "Valid verification token";
    }
}
