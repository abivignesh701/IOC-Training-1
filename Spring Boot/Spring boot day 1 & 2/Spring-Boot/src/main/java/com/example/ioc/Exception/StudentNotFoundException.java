package com.example.ioc.Exception;

/**
 * Thrown when a requested student record cannot be found by roll number.
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(int rollNo) {
        super("Student not found with roll number: " + rollNo);
    }
}
