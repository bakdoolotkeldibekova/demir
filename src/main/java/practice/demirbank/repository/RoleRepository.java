package practice.demirbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.demirbank.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
