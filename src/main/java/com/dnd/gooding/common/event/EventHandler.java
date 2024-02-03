package com.dnd.gooding.common.event;

import net.jodah.typetools.TypeResolver;

public interface EventHandler<T> {

	void handle(T event);

	/**
	 * 핸들러가 이벤트를 처리할 수 있는지 여부를 검사
	 * @param event
	 * @return
	 */
	default boolean canHandle(Object event) {
		Class<?>[] typeArgs = TypeResolver.resolveRawArguments(
			EventHandler.class, this.getClass());
		return typeArgs[0].isAssignableFrom(event.getClass());
	}
}
