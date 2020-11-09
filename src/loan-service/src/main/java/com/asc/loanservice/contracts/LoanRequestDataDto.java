package com.asc.loanservice.contracts;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoanRequestDataDto {
  @Id
  private String loanRequestNumber;
  private String customerName;
  private LocalDate customerBirthday;
  private String customerTaxId;
  private BigDecimal customerMonthlyIncome;
  private BigDecimal loanAmount;
  private Integer numberOfInstallments;
  private LocalDate firstInstallmentDate;
  private LoanRequestEvaluationResult evaluationResult;
  private LocalDateTime registrationDate;

  public LoanRequestDataDto mapFromLoanRequest(
      LoanRequestDto loanRequestDto, LoanRequestEvaluationResult evaluationResult) {
    return new LoanRequestDataDto(
        new Timestamp(System.currentTimeMillis()).toString(),
        loanRequestDto.getCustomerName(),
        loanRequestDto.getCustomerBirthday(),
        loanRequestDto.getCustomerTaxId(),
        loanRequestDto.getCustomerMonthlyIncome(),
        loanRequestDto.getLoanAmount(),
        loanRequestDto.getNumberOfInstallments(),
        loanRequestDto.getFirstInstallmentDate(),
        evaluationResult,
        LocalDateTime.now());
  }
}
