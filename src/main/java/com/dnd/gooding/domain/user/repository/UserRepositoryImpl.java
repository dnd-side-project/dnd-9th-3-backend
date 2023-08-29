package com.dnd.gooding.domain.user.repository;

import static com.dnd.gooding.domain.onboarding.model.QOnboarding.onboarding;
import static com.dnd.gooding.domain.user.model.QUser.user;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.service.port.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class UserRepositoryImpl implements UserRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public UserRepositoryImpl(EntityManager em) {
		this.em = em;
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

	@Override
	public Optional<User> findByOauthId(String oauthId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(user)
				.where(oauthIdEquals(oauthId))
				.fetchOne());
	}

	@Override
	public Optional<User> findByUserIdAndOnboarding(Long userId) {
		return Optional.ofNullable(
				queryFactory
					.select(user).distinct()
					.from(user)
					.join(user.onboardings, onboarding).fetchJoin()
					.where(userIdEquals(userId))
					.fetchOne());
	}

	@Override
	public void profileImageUpdate(User updateUser, String profileImageUrl) {
		Long count = queryFactory
				.update(user)
				.set(user.profileImgUrl, profileImageUrl)
				.where(userIdEquals(updateUser.getId()))
				.execute();
		em.flush();
		em.clear();
	}

	@Override
	public void nickNameUpdate(User updateUser, String nickName) {
		Long count = queryFactory
				.update(user)
				.set(user.nickname, nickName)
				.where(userIdEquals(updateUser.getId()))
				.execute();
		em.flush();
		em.clear();
	}

	@Override
	public Optional<User> findById(Long userId) {
		return Optional.empty();
	}

	@Transactional
	@Override
	public void delete(User user) {
		em.remove(user);
	}

	@Transactional
	@Override
	public User save(User user) {
		em.persist(user);
		return user;
	}

	private BooleanExpression userIdEquals(Long userId) {
		return user.id.eq(userId);
	}

	private BooleanExpression providerEquals(String provider) {
		return user.provider.eq(provider);
	}

	private BooleanExpression oauthIdEquals(String oauthId) {
		return user.oauthId.eq(oauthId);
	}
}
