package com.procs.pcs_.controller;

import com.procs.pcs_.model.*;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import com.procs.pcs_.request_response.*;
import com.procs.pcs_.security.JwtUtils;
import com.procs.pcs_.service.UserDetailsImpl;
import com.procs.pcs_.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(value = "http://localhost:8081")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDataRepository userDataR;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
//сюда надр еще добавить список проектов id+название
        return ResponseEntity
                .ok(new JwtResponse(jwt, "Bearer",
//                        userDetails.getId(), userDetails.getUsername(),
//                        userDetails.getEmail(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }
        UsersEntity user = new UsersEntity(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        if (strRoles == null) {
            strRoles.add("prog");
        }
        Set<RoleEntity> roles = userService.getUserRoleSetFromUserStringSet(strRoles);
        if (roles == null) return ResponseEntity.ok("Error with adding roles");

        user.setRoles(roles);
        userRepository.save(user);

        UserData userData = new UserData(
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                userRepository.getByLogin(user.getLogin()));
        userDataR.save(userData);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
