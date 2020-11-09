package com.asc.loanservice.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.asc.loanservice.contracts.LoanRequestDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AgeValidatorTest {

  private final AgeValidator ageValidator = new AgeValidator();

  @ParameterizedTest
  @MethodSource("rejectElderRegisterParams")
  void rejectElder(LoanRequestDto loanRequestDto) {
    assertFalse(ageValidator.validate(loanRequestDto));
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
