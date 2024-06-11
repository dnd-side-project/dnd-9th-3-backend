package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.query.dto.ImageData;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ImageDataDao extends Repository<ImageData, Long> {

    @Query(
            "select new com.dnd.gooding.record.query.dto.ImageData("
                    + "i.id, i.path, i.uploadTime, i.recordNumber) "
                    + "from ImageData i "
                    + "where i.recordNumber in :recordIds")
    List<ImageData> findByRecordNumberIn(@Param("recordIds") List<String> recordIds);
}
