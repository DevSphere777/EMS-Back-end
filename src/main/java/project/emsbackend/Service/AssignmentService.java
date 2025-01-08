package project.emsbackend.Service;

import org.springframework.stereotype.Service;
import project.emsbackend.Model.Assignment;
import project.emsbackend.Model.User;
import project.emsbackend.Repository.AssignmentRepository;
import project.emsbackend.Repository.UserRepository;

import java.util.List;
@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    public List<Assignment> getAllAssignment() {

        return assignmentRepository.findAll();
    }

    public boolean addAssignment(Assignment assignment) {

        if(assignmentRepository.existsByTitle(assignment.getTitle()))
            return false;
        assignmentRepository.save(assignment);
        for (User user : assignment.getUsers()){
            if(!user.getAssignments().contains(assignment)){
                user.getAssignments().add(assignment);
                userRepository.save(user);
            }
        }

        return true;
    }

    public Assignment findById(long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    public void updateAssignment(Assignment existingAssignment, Assignment assignment) {



        if(assignment.getTitle() != null && !assignment.getTitle().isEmpty())
            existingAssignment.setTitle(assignment.getTitle());
        if(assignment.getDescription() != null && !assignment.getDescription().isEmpty())
            existingAssignment.setDescription(assignment.getDescription());
        assignmentRepository.save(existingAssignment);
        if(assignment.getUsers() != null){
            for(User user : existingAssignment.getUsers()) {
                if (!assignment.getUsers().contains(user)) {
                    user.getAssignments().remove(existingAssignment);
                    userRepository.save(user);
                }
            }
            for(User user : assignment.getUsers()){
                if(!existingAssignment.getUsers().contains(user)) {
                    user.getAssignments().add(existingAssignment);
                    userRepository.save(user);
                }
            }
        }
        assignmentRepository.save(existingAssignment);
    }

    public void deleteById(long id) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if(assignment != null)
            for(User user : assignment.getUsers()){
                user.getAssignments().remove(assignment);
                userRepository.save(user);
            }
        assignmentRepository.deleteById(id);
    }

}
