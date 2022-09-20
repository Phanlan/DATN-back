package myproject.project.service;

import lombok.AllArgsConstructor;
import myproject.project.entity.*;
import myproject.project.model.request.LoginRequest;
import myproject.project.model.request.RegisterRequest;
import myproject.project.repository.RoleRepository;
import myproject.project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final PasswordEncoder encoder;
//    private final AuthenticationManager authenticationManager;
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        boolean emailExits = userRepository.existsByEmail(registerRequest.getEmail());
        if (emailExits) {
            return new ResponseEntity<>("Email have existed!", HttpStatus.BAD_REQUEST);
        }

        Role userRole =roleRepository.findByName(RoleType.USER)
                .orElseThrow(
                        () -> new RuntimeException("Error: Role is not found.")
                );

        User user = User.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullname())
                .phoneNumber(registerRequest.getPhone())
                .password(registerRequest.getPassword())
                .birthday(registerRequest.getBirthday())
                .address(registerRequest.getAddress())
                .role(userRole)
                .status(Status.ACTIVE)
                .build();
        userRepository.save(user);
        return new ResponseEntity<>("Register your account success!", HttpStatus.OK);
    }

    public User getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new Exception("User not exist");
        return user;
    }
    public ResponseEntity<?> login(LoginRequest loginRequest) throws Exception {
        User user = getUserByEmail(loginRequest.getEmail());
//        boolean checkAccount = encoder.matches(loginRequest.getPassword(), user.getPassword());
//        if (!checkAccount) {
//            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
//        }


        if (user.getStatus().equals(Status.NON_ACTIVE)) {
            return new ResponseEntity<>("your account is not active", HttpStatus.BAD_REQUEST);
        } else {
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    );

//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return (ResponseEntity<?>) ResponseEntity.ok();
        }
    }

    public UserDetails loadUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return new CustomUserDetails(user);
    }

}
