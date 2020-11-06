package com.asc.loanservice.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LoanRequestServiceTest {

  private LoanRequestService loanRequestService = new LoanRequestService();

  @ParameterizedTest
  @MethodSource("correctRegisterParams")
  void register(LoanRequestDto loanRequestDto) {
    assertDoesNotThrow(
        () -> {
          loanRequestService.register(loanRequestDto);
        });
  }

  @ParameterizedTest
  @MethodSource("correctRegisterParams")
  void getByNumber(LoanRequestDto loanRequestDto) {
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

  @ParameterizedTest
  @MethodSource("debtorRegisterParams")
  void rejectDebtor(LoanRequestDto loanRequestDto) {
    LoanRequestRegistrationResultDto result = loanRequestService.register(loanRequestDto);
    assertEquals(LoanRequestEvaluationResult.REJECTED, result.getEvaluationResult());
  }

  @ParameterizedTest
  @MethodSource("rejectElderRegisterParams")
  void rejectElder(LoanRequestDto loanRequestDto) {
    LoanRequestRegistrationResultDto result = loanRequestService.register(loanRequestDto);
    assertEquals(LoanRequestEvaluationResult.REJECTED, result.getEvaluationResult());
  }

  @ParameterizedTest
  @MethodSource("customerCanAffordLoanRegisterParams")
  void rejectBigInstallment(LoanRequestDto loanRequestDto) {
    LoanRequestRegistrationResultDto result = loanRequestService.register(loanRequestDto);
    assertEquals(LoanRequestEvaluationResult.REJECTED, result.getEvaluationResult());
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

  private static Stream<Arguments> customerCanAffordLoanRegisterParams() {
    return Stream.of(
        Arguments.of(
            new LoanRequestDto(
                "Kowalski",
                LocalDate.of(1990, 11, 11),
                "342fsdb4",
                BigDecimal.valueOf(30),
                BigDecimal.valueOf(1000),
                10,
                LocalDate.of(2020, 12, 12))),
        Arguments.of(
            new LoanRequestDto(
                "Kozłowski",
                LocalDate.of(1980, 12, 12),
                "dhsai3",
                BigDecimal.valueOf(600),
                BigDecimal.valueOf(10000),
                30,
                LocalDate.of(2020, 9, 9))),
        Arguments.of(
            new LoanRequestDto(
                "Nowak",
                LocalDate.of(1977, 5, 11),
                "342fsdb4",
                BigDecimal.valueOf(1300),
                BigDecimal.valueOf(111000),
                100,
                LocalDate.of(2021, 1, 1))));
  }

  private static Stream<Arguments> rejectElderRegisterParams() {
    return Stream.of(
        Arguments.of(
            new LoanRequestDto(
                "Kowalski",
                LocalDate.of(1890, 11, 11),
                "342fsdb4",
                BigDecimal.valueOf(3000),
                BigDecimal.valueOf(1000),
                10,
                LocalDate.of(2020, 12, 12))),
        Arguments.of(
            new LoanRequestDto(
                "Kozłowski",
                LocalDate.of(1780, 12, 12),
                "dhsai3",
                BigDecimal.valueOf(6000),
                BigDecimal.valueOf(10000),
                30,
                LocalDate.of(2020, 9, 9))),
        Arguments.of(
            new LoanRequestDto(
                "Nowak",
                LocalDate.of(1960, 5, 11),
                "342fsdb4",
                BigDecimal.valueOf(13000),
                BigDecimal.valueOf(11100),
                100,
                LocalDate.of(2021, 1, 1))));
  }
}
