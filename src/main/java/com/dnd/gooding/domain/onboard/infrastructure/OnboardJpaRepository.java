package com.dnd.gooding.domain.onboard.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardJpaRepository extends JpaRepository<OnboardEntity, Long> {
}
