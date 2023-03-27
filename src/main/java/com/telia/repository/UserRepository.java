package com.telia.repository;
import com.telia.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPersonalNumber(Long personalNumber);
    List<User> findByFullNameAndPersonalNumber(String name, Long personalNumber, Sort by);
    List<User> findByFullName(String name, Sort by);
}
