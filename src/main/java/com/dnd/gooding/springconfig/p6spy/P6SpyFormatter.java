package com.dnd.gooding.springconfig.p6spy;

import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import static com.p6spy.engine.logging.Category.STATEMENT;
import static java.util.Locale.ROOT;
import static org.hibernate.engine.jdbc.internal.FormatStyle.BASIC;
import static org.hibernate.engine.jdbc.internal.FormatStyle.DDL;

public class P6SpyFormatter extends JdbcEventListener implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
		String sql, String url) {

		StringBuilder sb = new StringBuilder();

		sql = format(category, sql);
		sb.append(category)
			.append(" -> ")
			.append("[Query executeTime = ")
			.append(elapsed).append("ms")
			.append(" | Connection ID = ")
			.append(connectionId)
			.append("]")
			.append(sql);

		return sb.toString();
	}

	private String format(final String category, String sql) {
		if (sql == null || sql.trim().isEmpty()) {
			return sql;
		}

		if (STATEMENT.getName().equals(category)) {
			String tmpsql = sql.trim().toLowerCase(ROOT);
			if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
				sql = DDL.getFormatter().format(sql);
			} else {
				sql = BASIC.getFormatter().format(sql);
			}
		}

		return sql;
	}
}
