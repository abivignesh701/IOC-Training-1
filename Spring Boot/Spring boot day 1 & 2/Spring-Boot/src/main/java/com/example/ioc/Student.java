package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Demonstrates Spring IoC constructor injection with {@link Writer} bean qualification.
 */
@Component
public class Student {
    private int age;
    private final Writer writer;

    public Student(@Qualifier("pen") Writer writer) {
        this.writer = writer;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Writer getWriter() {
        return writer;
    }

    public void study() {
        System.out.println("i am studying");
    }

    public void exam() {
        writer.write();
    }
}
