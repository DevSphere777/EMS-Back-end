package project.emsbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.emsbackend.Model.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    boolean existsByTitle(String title);
}
