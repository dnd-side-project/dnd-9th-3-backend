package com.dnd.gooding.domain.onboard.infrastructure;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.dnd.gooding.domain.onboard.domain.Onboard;
import com.dnd.gooding.domain.onboard.service.port.OnboardRepository;

@Repository
public class OnboardRepositoryImpl implements OnboardRepository {

	private final OnboardJpaRepository onboardJpaRepository;

	public OnboardRepositoryImpl(OnboardJpaRepository onboardJpaRepository) {
		this.onboardJpaRepository = onboardJpaRepository;
	}

	@Override
	public Onboard save(Onboard onboard) {
		return onboardJpaRepository.save(OnboardEntity.from(onboard)).toModel();
	}
}
