package com.codef.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class ProductParameters extends JpaBase {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long parameterId;
	

	@ManyToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="product_id")
    @JsonBackReference
	private Products product;
	
	private long parameterSeq;
	
	private String parameterName;
	
	private String parameterTypeCode;
	
}
