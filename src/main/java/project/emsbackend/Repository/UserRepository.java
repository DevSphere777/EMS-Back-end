package project.emsbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.emsbackend.Model.Assignment;
import project.emsbackend.Model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String username);

    boolean existsByPhone(String phone);

    User findByUsername(String username);

    User findByEmail(String email);

    List<Assignment> getAssignmentsById(long userId);
}
