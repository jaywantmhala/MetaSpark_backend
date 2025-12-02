package com.switflow.swiftFlow.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Access Content";
    }

    @GetMapping("/design")
    @PreAuthorize("hasAnyRole('DESIGN', 'ADMIN')")
    public String designAccess() {
        return "Design Access Content";
    }

    @GetMapping("/production")
    @PreAuthorize("hasAnyRole('PRODUCTION', 'ADMIN')")
    public String productionAccess() {
        return "Production Access Content";
    }

    @GetMapping("/mechanic")
    @PreAuthorize("hasAnyRole('MECHANIC', 'ADMIN')")
    public String mechanicAccess() {
        return "Mechanic Access Content";
    }

    @GetMapping("/inspection")
    @PreAuthorize("hasAnyRole('INSPECTION', 'ADMIN')")
    public String inspectionAccess() {
        return "Inspection Access Content";
    }
}