package com.dnd.gooding.record.query.dao;

import com.dnd.gooding.record.query.dto.FeedData;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface FeedDataDao extends Repository<FeedData, String> {

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
                            + "LEFT JOIN member m "
                            + "ON r.recorder_id = m.member_id "
                            + "LEFT JOIN oauth o "
                            + "ON m.member_id = o.email ",
            nativeQuery = true)
    List<FeedData> findFeeds();
}
