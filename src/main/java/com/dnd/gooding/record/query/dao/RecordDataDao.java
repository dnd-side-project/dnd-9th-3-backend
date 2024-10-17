package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.query.dto.RecordData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface RecordDataDao extends Repository<RecordData, String> {

    @Query(
            value =
                    "SELECT "
                            + "r.record_number, "
                            + "r.place_title, "
                            + "r.place_latitude, "
                            + "r.place_longitude, "
                            + "r.recorder_id, "
                            + "r.recorder_name, "
                            + "r.title, "
                            + "r.record_date, "
                            + "r.description "
                            + "FROM record r "
                            + "WHERE r.record_number = :recordNumber",
            nativeQuery = true)
    Optional<RecordData> findById(@Param("recordNumber") String recordNumber);

    @Query(
            value =
                    "SELECT "
                            + "r.record_number, "
                            + "r.place_title, "
                            + "r.place_latitude, "
                            + "r.place_longitude, "
                            + "r.recorder_id, "
                            + "r.recorder_name, "
                            + "r.title, "
                            + "r.record_date, "
                            + "r.description "
                            + "FROM record r "
                            + "WHERE r.recorder_id = :recorderId",
            nativeQuery = true)
    List<RecordData> findByRecorderId(@Param("recorderId") String recorderId);

    @Query(
            value =
                    "SELECT "
                            + "r.record_number, "
                            + "r.place_latitude, "
                            + "r.place_longitude, "
                            + "r.place_title, "
                            + "r.description, "
                            + "r.record_date, "
                            + "r.record_score, "
                            + "r.recorder_id, "
                            + "r.recorder_name, "
                            + "r.title, "
                            + "m.name, "
                            + "o.image_url "
                            + "FROM record r "
                            + "INNER JOIN bookmark b "
                            + "ON r.record_number = b.record_number "
                            + "LEFT JOIN member m "
                            + "ON r.recorder_id = m.member_id "
                            + "LEFT JOIN oauth o "
                            + "ON m.member_id = o.email "
                            + "WHERE b.member_id = :memberId "
                            + "AND b.bookmark_yn = 'Y'",
            nativeQuery = true)
    List<RecordData> findByBookMarkMemberId(@Param("memberId") String memberId);
}
