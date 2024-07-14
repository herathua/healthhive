package io.bootify.health_hive.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bootify.health_hive.model.LabRequestDTO;
import io.bootify.health_hive.service.LabRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(value = "/api/labRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class SSEController {

    @Autowired
    private LabRequestService labRequestService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper to convert objects to JSON

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private final Map<Long, AtomicReference<List<LabRequestDTO>>> latestLabRequestsMap = new ConcurrentHashMap<>();

    @GetMapping(value = "/stream-sse/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSse(@PathVariable(name = "id") final Long labId) {
        SseEmitter emitter = new SseEmitter();
        latestLabRequestsMap.putIfAbsent(labId, new AtomicReference<>());

        emitter.onCompletion(() -> {
            System.out.println("SSE connection closed for labId: " + labId);
            latestLabRequestsMap.remove(labId);
        });
        emitter.onTimeout(() -> {
            System.out.println("SSE connection timed out for labId: " + labId);
            latestLabRequestsMap.remove(labId);
        });
        emitter.onError((ex) -> {
            System.out.println("SSE connection error for labId: " + labId);
            latestLabRequestsMap.remove(labId);
        });

        System.out.println("SSE connection opened for labId: " + labId);

        executor.scheduleAtFixedRate(() -> {
            try {
                List<LabRequestDTO> newLabRequests = labRequestService.getLabRequestsByLabId(labId);
                AtomicReference<List<LabRequestDTO>> latestLabRequestsRef = latestLabRequestsMap.get(labId);
                List<LabRequestDTO> latestLabRequests = latestLabRequestsRef.get();

                if (!newLabRequests.equals(latestLabRequests)) {
                    latestLabRequestsRef.set(newLabRequests);
                    String json = objectMapper.writeValueAsString(newLabRequests);
                    emitter.send(SseEmitter.event()
                            .name("labRequestUpdate")
                            .data(json, MediaType.APPLICATION_JSON));
                }
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }, 0, 5, TimeUnit.SECONDS);

        return emitter;
    }
}
