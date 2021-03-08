package com.codef.api.repository;

import com.codef.api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByClientIdAndClientSecret(String clientId, String clientSecret);

}
