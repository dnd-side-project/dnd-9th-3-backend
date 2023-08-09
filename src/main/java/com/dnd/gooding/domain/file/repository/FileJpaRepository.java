package com.dnd.gooding.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.gooding.domain.file.model.File;

public interface FileJpaRepository extends JpaRepository<File, Long> {
}
