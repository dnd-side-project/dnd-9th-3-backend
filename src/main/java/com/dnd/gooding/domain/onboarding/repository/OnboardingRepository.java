package com.dnd.gooding.domain.onboarding.repository;

import com.dnd.gooding.domain.onboarding.model.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long>, OnboardingRepositoryCustom  {
}
