package com.tsoyolv.gerritstat.port.output.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tsoyolv.gerritstat.port.output.rest.deserializer.LocalDateTimeDeserializer;
import com.tsoyolv.gerritstat.port.output.rest.dto.ChangesetResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GerritRestClient {

    @Value("${gerrit.host.url}")
    private String host;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .registerModule(new JavaTimeModule())
            .registerModule(new SimpleModule().addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer()));

    private static final int COUNT = 25;


    public List<ChangesetResponse> getChangeSets(String userName, String cookie, LocalDate from) {
        Set<ChangesetResponse> allItems = new HashSet<>();
        int offset = 0;
        while (true) {
            List<ChangesetResponse> items = requestChangeSets(userName, cookie, offset, COUNT);
            ChangesetResponse last = items.get(items.size() - 1);
            allItems.addAll(items);
            if (last.getSubmitted().toLocalDate().isBefore(from)) {
                break;
            }
            offset += COUNT;
        }
        return allItems.stream().sorted(Comparator.comparing(ChangesetResponse::getSubmitted).reversed()).toList();
    }

    private List<ChangesetResponse> requestChangeSets(String userName, String cookie, int offset, int count) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = "%s&S=%d&n=%d&q= status:Merged (o:%s )".formatted(host, offset, count, userName);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        String response = responseEntity.getBody();

        if (response != null && response.startsWith(")]}'")) {
            response = response.substring(4);
        }
        try {
            return mapper.readValue(response, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
