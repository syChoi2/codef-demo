package com.codef.api.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class JpaBase {

	@Column(nullable = false)
	@Builder.Default
	private boolean deleted = false;
	@Column(nullable = false, updatable=false)
	private OffsetDateTime createdAt;
	@Column(nullable = false)
	private OffsetDateTime modifiedAt;
	
	
	@PrePersist
	public void prePersist() {
		OffsetDateTime now = OffsetDateTime.now();
		createdAt = now;
		modifiedAt = now;
	}
	
	@PreUpdate
	public void preUpdate() {
		modifiedAt = OffsetDateTime.now();
	}
	
}
