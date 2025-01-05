package project.emsbackend.Service;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.emsbackend.Model.Assignment;
import project.emsbackend.Model.User;
import project.emsbackend.Repository.AssignmentRepository;

import java.util.List;
@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Transactional
    public List<Assignment> getAllAssignment() {
        return assignmentRepository.findAll();
    }

    public boolean addAssignment(Assignment assignment) {
        if(assignmentRepository.existsByTitle(assignment.getTitle()))
            return false;
        for (User user : assignment.getUsers())
                user.getAssignments().add(assignment);
            assignmentRepository.save(assignment);
            return true;
    }

    public Assignment findById(long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    public void updateAssignment(Assignment existingAssignment, Assignment assignment) {
        existingAssignment.setTitle(assignment.getTitle());
        existingAssignment.setDescription(assignment.getDescription());
        existingAssignment.setUsers(assignment.getUsers());
        assignmentRepository.save(existingAssignment);
    }

    public void deleteById(long id) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if(assignment != null)
            for(User user : assignment.getUsers())
                user.getAssignments().remove(assignment);
        assignmentRepository.deleteById(id);
    }
}
