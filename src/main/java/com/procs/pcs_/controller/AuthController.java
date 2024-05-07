package com.procs.pcs_.controller;

import com.procs.pcs_.model.ERole;
import com.procs.pcs_.model.RoleEntity;
import com.procs.pcs_.model.UserData;
import com.procs.pcs_.model.UsersEntity;
import com.procs.pcs_.repository.RoleRepository;
import com.procs.pcs_.repository.UserDataRepository;
import com.procs.pcs_.repository.UserRepository;
import com.procs.pcs_.request_response.JwtResponse;
import com.procs.pcs_.request_response.LoginRequest;
import com.procs.pcs_.request_response.MessageResponse;
import com.procs.pcs_.request_response.SignupRequest;
import com.procs.pcs_.security.JwtUtils;
import com.procs.pcs_.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDataRepository userDataR;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
//сюда надр еще добавить список проектов id+название
        return ResponseEntity
                .ok(new JwtResponse(jwt, "Bearer", userDetails.getId(), userDetails.getUsername(),
                        userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        UsersEntity user = new UsersEntity(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.PROG)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
//            strRoles.forEach(role -> {
//            RoleEntity newRole = switch (role) {
//                case "admin" -> roleRepository.findByName(ERole.ADMIN)
//                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                case "proger" -> roleRepository.findByName(ERole.PROG)
//                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                case "manager" -> roleRepository.findByName(ERole.MANAG)
//                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                default -> null;
//            };
//            if (newRole != null) {
//                roles.add(newRole);
//            }
//        });
        }
        user.setRoles(roles);
        userRepository.save(user);

        UserData userData = new UserData(
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                userRepository.getByLogin(user.getLogin()));
        userDataR.save(userData);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/registeradmin")
    public ResponseEntity<?> registrationToAdmin(@RequestBody SignupRequest signupRequest) {
        this.registerUser(signupRequest);

        return ResponseEntity.ok(new MessageResponse("Admin registered!"));
    }
}
