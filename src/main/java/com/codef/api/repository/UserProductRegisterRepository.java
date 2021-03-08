package com.codef.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codef.api.entity.UserProductRegister;

public interface UserProductRegisterRepository extends JpaRepository<UserProductRegister, Long> {

	@Query(nativeQuery=true, value =
			"select * from user_product_register "
			+ " where user_id = :userId "
			+ " and product_id = :productId "
			+ " and deleted = false "
			)
	Optional<UserProductRegister> findUserProductRegister(@Param("userId") Long userId, @Param("productId") Long productId);

}
