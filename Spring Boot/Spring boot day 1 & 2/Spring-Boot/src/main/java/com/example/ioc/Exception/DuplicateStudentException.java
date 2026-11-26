package com.example.ioc.Exception;

/**
 * Thrown when attempting to create a student with a roll number that already exists.
 */
public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException(int rollNo) {
        super("Student already exists with roll number: " + rollNo);
    }
}
