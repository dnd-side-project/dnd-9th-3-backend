package com.dnd.gooding.domain.file.infrastructure;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.dnd.gooding.domain.file.domain.File;
import com.dnd.gooding.domain.file.service.port.FileRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class FileRepositoryImpl implements FileRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	private final FileJpaRepository fileJpaRepository;

	public FileRepositoryImpl(EntityManager em, FileJpaRepository fileJpaRepository) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
		this.fileJpaRepository = fileJpaRepository;
	}

	@Override
	public File save(File file) {
		return fileJpaRepository.save(FileEntity.from(file)).toModel();
	}
}
