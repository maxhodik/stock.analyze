package com.example.maxhodik.stock.analyze.restClient;

import com.example.maxhodik.stock.analyze.dto.StockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@Log4j2
@RequiredArgsConstructor
public class StockClientImpl implements StockClient {
    @Value("${api.iex.cloud.stock.url}")
    private String url;
    private final RestTemplate restTemplate;


    @Override
    public StockDto getStock(String task) {
        ResponseEntity<StockDto> response = restTemplate.exchange(getUrl(task), HttpMethod.GET, null, StockDto.class);
        try {
            if (response.getStatusCode().is2xxSuccessful() & Objects.nonNull(response.getBody())) {
                log.debug("Stock is downloaded. Status {}", response.getStatusCode());
                return response.getBody();
            }
        } catch (Exception exception) {
            log.debug("Status code {}", response.getStatusCode());
            return null;
        }
        return null;
    }


    private String getUrl(String task) {
        return String.format(url, task);
    }
}
