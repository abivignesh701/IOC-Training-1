package com.example.ioc.Controller;

import com.example.ioc.Model.Student;
import com.example.ioc.Service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for {@link Student} resources.
 */
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{rno}")
    public Student getStudentByRollNo(@PathVariable("rno") int rollNo) {
        return studentService.getStudentByRollNo(rollNo);
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("added");
    }

    @PutMapping("/students")
    public String updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/students/{rno}")
    public String deleteById(@PathVariable("rno") int rollNo) {
        return studentService.deleteStudentByRollNo(rollNo);
    }
}
