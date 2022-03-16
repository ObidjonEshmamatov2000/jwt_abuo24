package uz.mafia.jwt_aabuo24.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import uz.mafia.jwt_aabuo24.entity.User;
import uz.mafia.jwt_aabuo24.loader.Vm;
import uz.mafia.jwt_aabuo24.repository.UserRepository;
import uz.mafia.jwt_aabuo24.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Vm vm){
        String username = vm.getUsername();
        String password = vm.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("dsa"));
        System.out.println(user.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        Map map = new HashMap();
        map.put("token", token);
        map.put("username",username);
        map.put("success", true);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
