package com.dnd.gooding.record.query.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.dnd.gooding.record.query.dto.ImageData;

public interface ImageDataDao extends Repository<ImageData, Long> {

	@Query(
		"""
   			select new com.dnd.gooding.record.query.dto.ImageData(
   				i.id, i.path, i.uploadTime, i.recordNumber
   			)
   			from ImageData i
   			where i.recordNumber in (:recordIds)
			"""
	)
	List<ImageData> findByRecordNumberIn(List<String> recordIds);
}
