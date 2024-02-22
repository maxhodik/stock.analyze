package com.example.maxhodik.stock.analyze.restClient;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyClientImplTest {
    private final CompanyDto EXPECTED_COMPANY_DTO = new CompanyDto("symbol", "companyName", true);
    private final CompanyDto EXPECTED_COMPANY_DTO_1 = new CompanyDto("symbol1", "companyName1", true);
    private final List<CompanyDto> COMPANIES_DTO_LIST = List.of(EXPECTED_COMPANY_DTO, EXPECTED_COMPANY_DTO_1);
    private final ResponseEntity<List<CompanyDto>> RESPONSE_ENTITY_OK = new ResponseEntity<>(COMPANIES_DTO_LIST, HttpStatus.OK);
    private final ResponseEntity<List<CompanyDto>> RESPONSE_ENTITY_NO_CONTENT = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private CompanyClientImpl companyClient;
    private final String URL = "http://example.com/companies";


    @Test
    public void testGetCompanies_SuccessfulResponse() {
        // GIVEN
        when(restTemplate.exchange(eq(URL), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
                .thenReturn(RESPONSE_ENTITY_OK);
        ReflectionTestUtils.setField(companyClient, "url", URL);
        // WHEN
        List<CompanyDto> companies = companyClient.getCompanies();
        // THEN
        assertEquals(2, companies.size());
        assertTrue(companies.containsAll(COMPANIES_DTO_LIST));
    }

    @Test
    public void testGetCompanies_EmptyResponse() {
        //GIVEN
        when(restTemplate.exchange(eq(URL), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
                .thenReturn(RESPONSE_ENTITY_NO_CONTENT);
        ReflectionTestUtils.setField(companyClient, "url", URL);
        // WHEN
        List<CompanyDto> companies = companyClient.getCompanies();

        // THEN
        assertNotNull(companies);
        assertEquals(0, companies.size());
    }
}
