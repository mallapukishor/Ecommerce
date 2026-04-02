package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);

    boolean existsByUserName(@NotBlank @Size(min = 3,max = 20) String username);

    boolean existsByEmail(@NotBlank @Size(max = 50) @Email String email);
}
