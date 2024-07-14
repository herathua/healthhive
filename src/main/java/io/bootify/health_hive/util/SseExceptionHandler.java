package io.bootify.health_hive.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@ControllerAdvice
public class SseExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public SseEmitter handleException(HttpServletRequest request, Throwable ex) {
        SseEmitter sseEmitter = new SseEmitter();
        try {
            sseEmitter.send(SseEmitter.event()
                    .name("error")
                    .data("An error occurred: " + ex.getMessage())
                    .id("error"));
            sseEmitter.complete();
        } catch (Exception e) {
            sseEmitter.completeWithError(e);
        }
        return sseEmitter;
    }
}
