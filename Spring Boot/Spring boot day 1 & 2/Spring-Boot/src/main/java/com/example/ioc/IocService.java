package com.example.ioc;

import org.springframework.stereotype.Service;

/**
 * Provides greeting messages for {@link IocController}.
 */
@Service
public class IocService {
    public String getGreeting() {
        return "Hello Service";
    }
}
