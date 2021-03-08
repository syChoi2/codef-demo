package com.codef.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codef.api.entity.ProductParameters;

public interface ProductParameterRepository extends JpaRepository<ProductParameters, Long> {

	@Query(nativeQuery=true, value =
			"select * from product_parameters "
			+ " where product_id = :productId "
			+ " and deleted = false "
			)
	Iterable<ProductParameters> findProductParameters(@Param("productId") Long productId);

}
