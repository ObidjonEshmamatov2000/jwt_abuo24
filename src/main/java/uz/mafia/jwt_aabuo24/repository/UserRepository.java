package uz.mafia.jwt_aabuo24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mafia.jwt_aabuo24.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from users u where users.username=:username", nativeQuery = true)
    User findByLogin(@Param("username") String username);

    Optional<User> findByUsername(String username);
}