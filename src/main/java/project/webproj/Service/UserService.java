package project.webproj.Service;

import org.springframework.stereotype.Service;
import project.webproj.Model.User;
import project.webproj.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
