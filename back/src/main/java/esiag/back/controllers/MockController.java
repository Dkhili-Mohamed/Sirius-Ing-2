package esiag.back.controllers;

import esiag.back.services.MockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {

    private final MockService mockService;

    public MockController(MockService mockService) {
        this.mockService = mockService;
    }

    @PostMapping("/mock/init")
    public String initMockData() {
        mockService.MockData();
        return "Mock data généré ";
    }
}
