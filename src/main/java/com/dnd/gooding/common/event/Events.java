package com.dnd.gooding.common.event;

import java.util.ArrayList;
import java.util.List;

public class Events {

  private static ThreadLocal<List<EventHandler<?>>> handlers = new ThreadLocal<>();
  private static ThreadLocal<Boolean> publishing =
      new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
          return Boolean.FALSE;
        }
      };

  public static void raise(Object event) {
    if (publishing.get()) {
      return;
    }

    try {
      publishing.set(Boolean.TRUE);

      List<EventHandler<?>> eventHandlers = handlers.get();
      if (eventHandlers == null) {
        return;
      }
      for (EventHandler handler : eventHandlers) {
        if (handler.canHandle(event)) {
          handler.handle(event);
        }
      }
    } finally {
      publishing.set(Boolean.FALSE);
    }
  }

  public static void handle(EventHandler<?> handler) {
    if (publishing.get()) {
      return;
    }

    List<EventHandler<?>> eventHandlers = handlers.get();
    if (eventHandlers == null) {
      eventHandlers = new ArrayList<>();
      handlers.set(eventHandlers);
    }
    eventHandlers.add(handler);
  }

  public static void reset() {
    if (!publishing.get()) {
      handlers.remove();
    }
  }
}
