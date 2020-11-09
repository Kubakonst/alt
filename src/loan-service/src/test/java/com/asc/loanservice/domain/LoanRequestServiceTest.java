package com.asc.loanservice.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.repository.InMemoryLoanRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanRequestServiceTest {

  private LoanRequestService loanRequestService;

  @Mock private RulesValidationService mockRulesValidationService;

  @ParameterizedTest
  @MethodSource("correctRegisterParams")
  void register(LoanRequestDto loanRequestDto) {

    loanRequestService =
        new LoanRequestService(mockRulesValidationService, new InMemoryLoanRepository());
    when(mockRulesValidationService.validate(loanRequestDto)).thenReturn(true);

    assertDoesNotThrow(
        () -> {
          loanRequestService.register(loanRequestDto);
        });
  }

  @ParameterizedTest
  @MethodSource("correctRegisterParams")
  void getByNumber(LoanRequestDto loanRequestDto) {

    loanRequestService =
        new LoanRequestService(mockRulesValidationService, new InMemoryLoanRepository());
    when(mockRulesValidationService.validate(loanRequestDto)).thenReturn(true);

    LoanRequestRegistrationResultDto result = loanRequestService.register(loanRequestDto);
    assertEquals(
        result.getLoanRequestNumber(),
        loanRequestService.getByNumber(result.getLoanRequestNumber()).getLoanRequestNumber());
    assertEquals(
        loanRequestDto.getCustomerName(),
        loanRequestService.getByNumber(result.getLoanRequestNumber()).getCustomerName());
    assertEquals(
        loanRequestDto.getCustomerBirthday(),
        loanRequestService.getByNumber(result.getLoanRequestNumber()).getCustomerBirthday());
  }

  private static Stream<Arguments> correctRegisterParams() {
    return Stream.of(
        Arguments.of(
            new LoanRequestDto(
                "Kowalski",
                LocalDate.of(1990, 11, 11),
                "342fsdb4",
                BigDecimal.valueOf(3000),
                BigDecimal.valueOf(1000),
                10,
                LocalDate.of(2020, 12, 12))),
        Arguments.of(
            new LoanRequestDto(
                "Kozłowski",
                LocalDate.of(1980, 12, 12),
                "dhsai3",
                BigDecimal.valueOf(6000),
                BigDecimal.valueOf(10000),
                30,
                LocalDate.of(2020, 9, 9))),
        Arguments.of(
            new LoanRequestDto(
                "Nowak",
                LocalDate.of(1977, 5, 11),
                "342fsdb4",
                BigDecimal.valueOf(13000),
                BigDecimal.valueOf(111000),
                100,
                LocalDate.of(2021, 1, 1))));
  }
}
