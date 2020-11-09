package com.asc.loanservice.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.asc.loanservice.contracts.LoanRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class InstallmentCostValidatorTest {

  private InstallmentCostValidator installmentCostValidator = new InstallmentCostValidator();

  @ParameterizedTest
  @MethodSource("customerCantAffordLoanRegisterParams")
  void rejectBigInstallment(LoanRequestDto loanRequestDto) {
    assertTrue(installmentCostValidator.validate(loanRequestDto));
  }

  private static Stream<Arguments> customerCantAffordLoanRegisterParams() {
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
}
