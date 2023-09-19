package com.dnd.gooding.domain.user.infrastructure;

import static com.dnd.gooding.domain.onboard.infrastructure.QOnboardEntity.*;
import static com.dnd.gooding.domain.user.infrastructure.QUserEntity.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.dnd.gooding.domain.user.domain.User;
import com.dnd.gooding.domain.user.service.port.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	private final UserJpaRepository userJpaRepository;
	public UserRepositoryImpl(EntityManager em, UserJpaRepository userJpaRepository) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	public Optional<User> findByProviderAndOauthId(String provider, String oauthId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(userEntity)
				.where(providerEquals(provider), oauthIdEquals(oauthId))
				.fetchOne())
			.map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findByUserId(Long userId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(userEntity)
				.where(userIdEquals(userId))
				.fetchOne())
			.map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findByOauthId(String oauthId) {
		return Optional.ofNullable(
			queryFactory
				.select(userEntity).distinct()
				.from(userEntity)
				.leftJoin(userEntity.onboards, onboardEntity).fetchJoin()
				.where(oauthIdEquals(oauthId))
				.fetchOne())
			.map(UserEntity::toModel);
	}

	@Override
	public void delete(User user) {
		userJpaRepository.delete(UserEntity.from(user));
	}

	@Override
	public User save(User user) {
		return userJpaRepository.save(UserEntity.from(user)).toModel();
	}

	private BooleanExpression userIdEquals(Long userId) {
		return userEntity.id.eq(userId);
	}

	private BooleanExpression providerEquals(String provider) {
		return userEntity.provider.eq(provider);
	}

	private BooleanExpression oauthIdEquals(String oauthId) {
		return userEntity.oauthId.eq(oauthId);
	}
}
