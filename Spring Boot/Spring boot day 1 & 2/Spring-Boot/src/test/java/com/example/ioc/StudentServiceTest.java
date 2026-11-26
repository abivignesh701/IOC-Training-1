package com.example.ioc;

import com.example.ioc.Exception.DuplicateStudentException;
import com.example.ioc.Exception.StudentNotFoundException;
import com.example.ioc.Model.Student;
import com.example.ioc.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    @Test
    void getAllStudentsReturnsInitialSeedData() {
        List<Student> students = studentService.getAllStudents();
        assertEquals(2, students.size());
        assertEquals("ram", students.get(0).getName());
        assertEquals("ravi", students.get(1).getName());
    }

    @Test
    void getStudentByRollNoThrowsNotFoundForMissingRollNumber() {
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentByRollNo(999));
    }

    @Test
    void getStudentByRollNoThrowsBadRequestForNonPositiveRollNumber() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentByRollNo(0));
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentByRollNo(-5));
    }

    @Test
    void addStudentRejectsDuplicateAndInvalidPayloads() {
        studentService.addStudent(new Student(3, "  abi  ", "  java  "));
        Student added = studentService.getStudentByRollNo(3);
        assertEquals("abi", added.getName());
        assertEquals("java", added.getTech());

        assertThrows(DuplicateStudentException.class,
                () -> studentService.addStudent(new Student(3, "other", "python")));
        assertThrows(IllegalArgumentException.class,
                () -> studentService.addStudent(new Student(4, " ", "java")));
        assertThrows(IllegalArgumentException.class,
                () -> studentService.addStudent(new Student(4, "name", "")));
    }

    @Test
    void updateAndDeleteDoNotCorruptExistingStudentsWhenMissing() {
        assertThrows(StudentNotFoundException.class,
                () -> studentService.updateStudent(new Student(99, "ghost", "none")));
        assertThrows(StudentNotFoundException.class,
                () -> studentService.deleteStudentByRollNo(99));

        assertEquals(2, studentService.getAllStudents().size());
        assertEquals("ram", studentService.getStudentByRollNo(1).getName());

        assertEquals("updated", studentService.updateStudent(new Student(1, "ram kumar", "spring boot")));
        assertEquals("ram kumar", studentService.getStudentByRollNo(1).getName());

        assertEquals("deleted", studentService.deleteStudentByRollNo(1));
        assertEquals(1, studentService.getAllStudents().size());
    }
}
