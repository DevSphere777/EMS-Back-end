package project.emsbackend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.emsbackend.Model.Assignment;
import project.emsbackend.Service.AssignmentService;
import project.emsbackend.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {


    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    private ResponseEntity<List<Assignment>> userAssignment(){
        if(assignmentService.getAllAssignment() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(assignmentService.getAllAssignment(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    private ResponseEntity<Assignment> getAssignment(@PathVariable long id){
        if(assignmentService.findById(id) != null){
            return new ResponseEntity<>(assignmentService.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping
    private ResponseEntity<String> createAssignment(@RequestBody Assignment assignment){
        if(assignmentService.addAssignment(assignment))
            return new ResponseEntity<>("Assignment created successfully", HttpStatus.OK);

        else
            return new ResponseEntity<>("Assignment creation failed", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("{id}")
    private ResponseEntity<String> updateAssignment(@PathVariable long id,
                                                    @RequestBody Assignment assignment){
        Assignment existingAssignment = assignmentService.findById(id);
        if(existingAssignment == null)
            return new ResponseEntity<>("Assignment does not exist", HttpStatus.NOT_FOUND);
        else{
            assignmentService.updateAssignment(existingAssignment, assignment);
            return new ResponseEntity<>("Assignment updated successfully", HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    private ResponseEntity<String> deleteAssignment(@PathVariable long id){
        Assignment existingAssignment = assignmentService.findById(id);
        if(existingAssignment == null)
            return new ResponseEntity<>("Assignment does not exist", HttpStatus.NOT_FOUND);
        else {
            assignmentService.deleteById(id);
            return new ResponseEntity<>("Assignment deleted successfully", HttpStatus.OK);
        }
    }
}
