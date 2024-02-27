package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveSelectOperation;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@Log4j2
@ExtendWith(MockitoExtension.class)
class CustomCompanyRepositoryImplTest {
    private final Company EXPECTED_COMPANY = new Company(1L, "symbol", "companyName", true);

    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    @InjectMocks
    private CustomCompanyRepositoryImpl companyRepository;


    @Test
    public void testSaveCompany_NewCompany() {
        // GIVEN
        ReactiveSelectOperation.ReactiveSelect<Company> reactiveSelectMock = mock(ReactiveSelectOperation.ReactiveSelect.class);
        ReactiveSelectOperation.TerminatingSelect<Company> reactiveTerminatingMock = mock(ReactiveSelectOperation.TerminatingSelect.class);

        when(r2dbcEntityTemplate.select(Company.class))
                .thenReturn(reactiveSelectMock);
        when(reactiveSelectMock.matching(any())).thenReturn(reactiveTerminatingMock);
        when(reactiveTerminatingMock.one())
                .thenReturn(Mono.empty());
        when(r2dbcEntityTemplate.insert(EXPECTED_COMPANY))
                .thenReturn(Mono.just(EXPECTED_COMPANY));
        // WHEN
        companyRepository.saveCompany(EXPECTED_COMPANY)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        // THEN
        verify(r2dbcEntityTemplate).select(Company.class);
        verify(r2dbcEntityTemplate).insert(EXPECTED_COMPANY);
    }

    @Test
    public void testSaveCompany_NOT_NewCompany() {
        // GIVEN
        ReactiveSelectOperation.ReactiveSelect<Company> reactiveSelectMock = mock(ReactiveSelectOperation.ReactiveSelect.class);
        ReactiveSelectOperation.TerminatingSelect<Company> reactiveTerminatingMock = mock(ReactiveSelectOperation.TerminatingSelect.class);

        when(r2dbcEntityTemplate.select(Company.class))
                .thenReturn(reactiveSelectMock);
        when(reactiveSelectMock.matching(any())).thenReturn(reactiveTerminatingMock);
        when(reactiveTerminatingMock.one())
                .thenReturn(Mono.just(EXPECTED_COMPANY));
        // WHEN
        companyRepository.saveCompany(EXPECTED_COMPANY)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        // THEN
        verify(r2dbcEntityTemplate).select(Company.class);
        verify(r2dbcEntityTemplate, never()).insert(EXPECTED_COMPANY);
    }


}
