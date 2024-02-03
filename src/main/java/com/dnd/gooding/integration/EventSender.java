package com.dnd.gooding.integration;

import com.dnd.gooding.eventstore.api.EventEntry;

public interface EventSender {

	void send(EventEntry event);
}
