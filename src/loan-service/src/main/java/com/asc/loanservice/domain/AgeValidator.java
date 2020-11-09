package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestDto;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
class AgeValidator implements RulesValidator {

  private static final int CUSTOMER_MAX_AGE = 65;

  @Override
  public boolean validate(LoanRequestDto loanRequest) {
    return LocalDate.now()
            .minusYears(
                loanRequest
                    .getCustomerBirthday()
                    .minusMonths(loanRequest.getNumberOfInstallments())
                    .getYear())
            .getYear()
        < CUSTOMER_MAX_AGE;
  }
}
