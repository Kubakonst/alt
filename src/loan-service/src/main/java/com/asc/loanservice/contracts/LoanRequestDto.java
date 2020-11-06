package com.asc.loanservice.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {

  @NotBlank private String customerName;
  @NotBlank @Past private LocalDate customerBirthday;
  @NotBlank private String customerTaxId;
  @NotBlank @Positive private BigDecimal customerMonthlyIncome;
  @NotBlank @Positive private BigDecimal loanAmount;
  @NotBlank @Positive private Integer numberOfInstallments;
  @NotBlank @Past private LocalDate firstInstallmentDate;
}
