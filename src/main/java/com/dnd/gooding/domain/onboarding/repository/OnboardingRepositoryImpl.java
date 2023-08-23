package com.dnd.gooding.domain.onboarding.repository;

import javax.persistence.EntityManager;

import com.dnd.gooding.domain.onboarding.model.Onboarding;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class OnboardingRepositoryImpl implements OnboardingRepositoryCustom {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public OnboardingRepositoryImpl(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public void insert(Onboarding onboarding) {
		em.persist(onboarding);
	}
}
