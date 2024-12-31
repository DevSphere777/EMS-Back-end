package project.emsbackend.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.emsbackend.Model.User;
import project.emsbackend.Model.UserPrincipal;
import project.emsbackend.Repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(username);
            if(user == null){
                System.out.println("User not found");
                throw new UsernameNotFoundException("User not found");
            }
            return new UserPrincipal(user);
    }
}
