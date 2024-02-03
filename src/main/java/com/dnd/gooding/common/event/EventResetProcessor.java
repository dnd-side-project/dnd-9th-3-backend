package com.dnd.gooding.common.event;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
public class EventResetProcessor {

  private ThreadLocal<Integer> nestedCount =
      new ThreadLocal<Integer>() {

        @Override
        protected Integer initialValue() {
          return 0;
        }
      };

  @Around("@within(org.springframework.stereotype.Service)")
  public Object doReset(ProceedingJoinPoint joinPoint) throws Throwable {
    nestedCount.set(nestedCount.get() + 1);
    try {
      return joinPoint.proceed();
    } finally {
      nestedCount.set(nestedCount.get() - 1);
      if (nestedCount.get() == 0) {
        Events.reset();
      }
    }
  }
}
