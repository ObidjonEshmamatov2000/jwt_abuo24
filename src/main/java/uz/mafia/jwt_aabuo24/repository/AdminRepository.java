package uz.mafia.jwt_aabuo24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mafia.jwt_aabuo24.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}