package edu.icet.controller;

import edu.icet.dto.Admin;
import edu.icet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    final AdminService service;

    @PostMapping ("/add-admin")
    public void addAdmin(@RequestBody Admin admin) {
        service.addAdmin(admin);
    }

    @GetMapping ("/get-all")
    public Admin getAllAdmins() {
        return null;
    }
}
