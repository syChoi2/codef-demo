package com.codef.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codef.api.dto.PathDto;
import com.codef.api.entity.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {

	@Query(nativeQuery=true, value =
			"select * from products p " + 
			"where 1=1  " + 
			"and p.version_code = (select id from common_code cc where parent_id = 'VS0000' and code_name = :#{#pathDto.versionName} ) " + 
			"and p.country_code = (select id from common_code cc where parent_id = 'CN0000' and code_name = :#{#pathDto.countryName} ) " + 
			"and p.job_code = (select id from common_code cc where parent_id = 'JB0000' and code_name = :#{#pathDto.jobName} ) " + 
			"and p.customer_code = (select id from common_code cc where parent_id = 'CS0000' and code_name = :#{#pathDto.customerName} ) " + 
			"and p.product_code = (select id from common_code cc where parent_id = 'PD0000' and code_name = :#{#pathDto.productName}  ) " + 
			"and deleted = false"
			)
	Optional<Products> findProduct(@Param("pathDto") PathDto pathDto);

}
