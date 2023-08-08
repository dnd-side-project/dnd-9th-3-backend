package com.dnd.gooding.domain.file.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "file")
@Getter
public class File {

	private String extension;
	private String orginalFile;
}
