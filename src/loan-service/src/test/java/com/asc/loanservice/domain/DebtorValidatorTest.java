package com.asc.loanservice.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.asc.loanservice.contracts.CustomerCheckResult;
import com.asc.loanservice.contracts.LoanRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
class DebtorValidatorTest {

  @MockBean private RestTemplate restTemplate;

  @Autowired private DebtorValidator debtorValidator;

  @BeforeEach
  void setup() {
    //        MockitoAnnotations.initMocks(DebtorValidatorTest.class);
  }

  @ParameterizedTest
  @MethodSource("debtorRegisterParams")
  //  void rejectDebtor(LoanRequestDto loanRequestDto) {
  //  @Test
  void rejectDebtor(LoanRequestDto loanRequestDto) {
    assertNotNull(debtorValidator);

    when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(CustomerCheckResult.class)))
        .thenReturn(
            ResponseEntity.ok(new CustomerCheckResult(loanRequestDto.getCustomerTaxId(), true)));

    assertFalse(debtorValidator.validate(loanRequestDto));
  }

  private static Stream<Arguments> debtorRegisterParams() {
    return Stream.of(
        Arguments.of(
            new LoanRequestDto(
                "Kowalski",
                LocalDate.of(1990, 11, 11),
                "01222815571",
                BigDecimal.valueOf(3000),
                BigDecimal.valueOf(1000),
                10,
                LocalDate.of(2020, 12, 12))),
        Arguments.of(
            new LoanRequestDto(
                "Kozłowski",
                LocalDate.of(1980, 12, 12),
                "51092513727",
                BigDecimal.valueOf(6000),
                BigDecimal.valueOf(10000),
                30,
                LocalDate.of(2020, 9, 9))),
        Arguments.of(
            new LoanRequestDto(
                "Nowak",
                LocalDate.of(1977, 5, 11),
                "40080200695",
                BigDecimal.valueOf(13000),
                BigDecimal.valueOf(111000),
                100,
                LocalDate.of(2021, 1, 1))));
  }
}
