package org.aqua.trading.repository;

import java.util.Optional;
import java.util.UUID;
import org.aqua.trading.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByIdAndStatus(UUID id, String status);
}
