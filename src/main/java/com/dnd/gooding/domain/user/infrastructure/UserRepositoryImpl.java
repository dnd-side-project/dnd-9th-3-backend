package com.dnd.gooding.domain.user.infrastructure;

import static com.dnd.gooding.domain.user.infrastructure.QUserEntity.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.dnd.gooding.domain.user.service.port.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	public UserRepositoryImpl(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<UserEntity> findByProviderAndOauthId(String provider, String oauthId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(userEntity)
				.where(providerEquals(provider), oauthIdEquals(oauthId))
				.fetchOne());
	}

	@Override
	public Optional<UserEntity> findByUserId(Long userId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(userEntity)
				.where(userIdEquals(userId))
				.fetchOne());
	}

	@Override
	public Optional<UserEntity> findByOauthId(String oauthId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(userEntity)
				.where(oauthIdEquals(oauthId))
				.fetchOne());
	}

	@Override
	public Optional<UserEntity> findByUserIdAndOnboarding(Long userId) {
		return Optional.ofNullable(
			queryFactory
				.select(userEntity).distinct()
				.from(userEntity)
				// .join(user.onboardings, onboarding).fetchJoin()
				.where(userIdEquals(userId))
				.fetchOne());
	}

	@Override
	public void delete(UserEntity userEntity) {
		em.remove(userEntity);
	}

	@Override
	public UserEntity save(UserEntity userEntity) {
		em.persist(userEntity);
		return userEntity;
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
