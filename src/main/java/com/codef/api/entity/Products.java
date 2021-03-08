package com.codef.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Products extends JpaBase {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long productId;
	
	private String countryCode;
	private String jobCode;
	private String customerCode;
	private String versionCode;
	
	private String apiAddress;
	
	private String pricingTypeCode;
	private int price;
	
	private boolean authNeeded;

	@ToString.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<RequestHistory> requestHistoryList = new ArrayList<RequestHistory>();
	
	@ToString.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<UserProductRegister> userProductRegisterList = new ArrayList<UserProductRegister>();
	
}
