package com.example.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class IocApplicationTests {

    @Autowired
    private Student student;

    @Autowired
    private IocController iocController;

    @Test
    void contextLoadsAndWiresExpectedBeans() {
        assertNotNull(student);
        assertInstanceOf(Pen.class, student.getWriter());
        assertNotNull(iocController);
    }
}
