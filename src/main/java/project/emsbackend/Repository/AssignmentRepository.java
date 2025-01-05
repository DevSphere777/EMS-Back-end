package project.emsbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.emsbackend.Model.Assignment;
import project.emsbackend.Model.User;

import java.util.List;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    boolean existsByTitle(String title);
}
