package com.example.ioc.Service;

import com.example.ioc.Exception.DuplicateStudentException;
import com.example.ioc.Exception.StudentNotFoundException;
import com.example.ioc.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * In-memory service managing CRUD operations and validation for {@link Student} records.
 */
@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>(List.of(
            new Student(1, "ram", "spring"),
            new Student(2, "ravi", "javascript")
    ));

    public synchronized List<Student> getAllStudents() {
        return List.copyOf(students);
    }

    public synchronized Student getStudentByRollNo(int rollNo) {
        validateRollNo(rollNo);
        int index = findIndexByRollNo(rollNo)
                .orElseThrow(() -> new StudentNotFoundException(rollNo));
        return students.get(index);
    }

    public synchronized void addStudent(Student student) {
        validateStudent(student);
        if (findIndexByRollNo(student.getRno()).isPresent()) {
            throw new DuplicateStudentException(student.getRno());
        }
        students.add(normalizeStudent(student));
    }

    public synchronized String updateStudent(Student student) {
        validateStudent(student);
        int index = findIndexByRollNo(student.getRno())
                .orElseThrow(() -> new StudentNotFoundException(student.getRno()));
        students.set(index, normalizeStudent(student));
        return "updated";
    }

    public synchronized String deleteStudentByRollNo(int rollNo) {
        validateRollNo(rollNo);
        int index = findIndexByRollNo(rollNo)
                .orElseThrow(() -> new StudentNotFoundException(rollNo));
        students.remove(index);
        return "deleted";
    }

    private OptionalInt findIndexByRollNo(int rollNo) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRno() == rollNo) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    private void validateRollNo(int rollNo) {
        if (rollNo <= 0) {
            throw new IllegalArgumentException("Roll number must be a positive integer");
        }
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student payload is required");
        }
        validateRollNo(student.getRno());
        if (student.getName() == null || student.getName().isBlank()) {
            throw new IllegalArgumentException("Student name must not be blank");
        }
        if (student.getTech() == null || student.getTech().isBlank()) {
            throw new IllegalArgumentException("Student tech must not be blank");
        }
    }

    private Student normalizeStudent(Student student) {
        return new Student(
                student.getRno(),
                student.getName().trim(),
                student.getTech().trim()
        );
    }
}
