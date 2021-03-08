package com.codef.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class CommonCode extends JpaBase{

	@Id
    @Column(name = "id")
	private String commonCodeId;
	
	@ManyToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="parent_id")
    @JsonBackReference
	private CommonCode parent;
	
	private String codeName;
	private boolean isValid;
	
	
	@ToString.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<CommonCode> childList = new ArrayList<CommonCode>();
}
