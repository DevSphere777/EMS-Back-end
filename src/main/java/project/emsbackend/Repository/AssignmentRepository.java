package project.emsbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.emsbackend.Model.Assignment;
import project.emsbackend.Model.User;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {


    List<User> getUsers(Assignment assignment);
}
