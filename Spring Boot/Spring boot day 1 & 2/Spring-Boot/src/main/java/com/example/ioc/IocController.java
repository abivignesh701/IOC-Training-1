package com.example.ioc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes a basic greeting endpoint backed by {@link IocService}.
 */
@RestController
public class IocController {
    private final IocService iocService;

    public IocController(IocService iocService) {
        this.iocService = iocService;
    }

    @GetMapping("/hello")
    public String hello() {
        return iocService.getGreeting();
    }
}
