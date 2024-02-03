package com.dnd.gooding.integration.infra;

import com.dnd.gooding.eventstore.api.EventEntry;
import com.dnd.gooding.integration.EventSender;

public class SysoutEventSender implements EventSender {

	@Override
	public void send(EventEntry event) {
		System.out.println("EventSender send event : " + event);
	}
}
