package com.dnd.gooding.eventstore.infra;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.dnd.gooding.eventstore.api.EventEntry;
import com.dnd.gooding.eventstore.api.EventStore;
import com.dnd.gooding.eventstore.api.PayloadConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JdbcEventStore implements EventStore {

	private ObjectMapper objectMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcEventStore(ObjectMapper objectMapper, JdbcTemplate jdbcTemplate) {
		this.objectMapper = objectMapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Object event) {
		EventEntry entry = new EventEntry(event.getClass().getName(),
			"application/json", toJson(event));
		jdbcTemplate.update(
			"insert into evententry " +
				"(type, content_type, payload, timestamp) " +
				"values (?, ?, ?, ?)",
			ps -> {
				ps.setString(1, entry.getType());
				ps.setString(2, entry.getContentType());
				ps.setString(3, entry.getPayload());
				ps.setTimestamp(4, new Timestamp(entry.getTimestamp()));
			}
		);
	}

	@Override
	public List<EventEntry> get(long offset, long limit) {
		return jdbcTemplate.query(
			"select * from evententry order by id asc limit ?, ?",
			ps -> {
				ps.setLong(1, offset);
				ps.setLong(2, limit);
			},
			(rs, rowNum) -> {
				return new EventEntry(
					rs.getLong("id"),
					rs.getString("type"),
					rs.getString("content_type"),
					rs.getString("payload"),
					rs.getTimestamp("timestamp").getTime());
			});
	}

	private String toJson(Object event) {
		try {
			return objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new PayloadConvertException(e);
		}
	}
}
