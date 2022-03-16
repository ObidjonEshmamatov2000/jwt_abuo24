package uz.mafia.jwt_aabuo24.loader;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mafia.jwt_aabuo24.entity.Admin;
import uz.mafia.jwt_aabuo24.entity.Role;
import uz.mafia.jwt_aabuo24.entity.User;
import uz.mafia.jwt_aabuo24.repository.AdminRepository;
import uz.mafia.jwt_aabuo24.repository.RoleRepository;
import uz.mafia.jwt_aabuo24.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String init;


    @Override
    public void run(String... args) throws Exception {
        try{
            if (init.equalsIgnoreCase("create")) {
                Role roleUser = new Role(1l, "ROLE_USER");
                Role roleAdmin = new Role(2l, "ROLE_ADMIN");
                List<Role> roleList = new ArrayList<>(Arrays.asList(roleUser, roleAdmin));
                roleList = roleRepository.saveAll(roleList);

                User user = new User();
                Admin admin = new Admin();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setFullName("user");
                user.setRoles(new ArrayList<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("root"));
                admin.setFullName("admin");
                admin.setSocial("t.me/test");
                admin.setRoles(roleList);
                userRepository.save(user);
                adminRepository.save(admin);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
}
