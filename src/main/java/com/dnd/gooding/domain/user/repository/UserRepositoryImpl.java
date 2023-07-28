package com.dnd.gooding.domain.user.repository;

import static com.dnd.gooding.domain.user.model.QUser.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.dnd.gooding.domain.user.model.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class UserRepositoryImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public UserRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<User> findByUserIdByProviderAndOauthId(String provider, String oauthId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(user)
				.where(providerEquals(provider), oauthIdEquals(oauthId))
				.fetchOne());
	}

	private BooleanExpression providerEquals(String provider) {
		return user.provider.eq(provider);
	}

	private BooleanExpression oauthIdEquals(String oauthId) {
		return user.oauthId.eq(oauthId);
	}
}
