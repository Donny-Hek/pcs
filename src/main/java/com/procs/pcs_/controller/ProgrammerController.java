package com.procs.pcs_.controller;

import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.security.JwtUtils;
import com.procs.pcs_.service.ProjectService;
import com.procs.pcs_.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(value = "http://localhost:8081")
@RestController
@RequestMapping("/programer")
@RequiredArgsConstructor
public class ProgrammerController {
    private final JwtUtils jwtUtils;
    private final ProjectService projectService;
    private final UserDataRepository userDataRepository;
    private final UserService userService;
//    @PostMapping("/")
//    public ResponseEntity<?>
}
