package uz.mafia.jwt_aabuo24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mafia.jwt_aabuo24.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findByName(String name);
}
