package com.example.maxhodik.stock.analyze.restClient;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
@Log4j2
@RequiredArgsConstructor
public class CompanyClientImpl implements CompanyClient {
    //  private final WebClient webClient;
    private final RestTemplate restTemplate;
    @Value("${api.iex.cloud.company.url}")
    private String url;

    @Override
    public List<CompanyDto> getCompanies() {
        List<CompanyDto> companies;
        ParameterizedTypeReference<List<CompanyDto>> typeReference = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<CompanyDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, typeReference);
        if (response.getStatusCode().is2xxSuccessful() & Objects.nonNull(response.getBody())) {
            companies = response.getBody();
            log.info("List of companies downloaded successful. Response status {}", response.getStatusCode());
            return companies;
        }
        log.info("List of companies isn't downloaded. Response status {}", response.getStatusCode());
        return List.of();
    }
}
