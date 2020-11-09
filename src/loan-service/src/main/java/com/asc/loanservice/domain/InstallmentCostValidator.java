package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestDto;
import org.springframework.stereotype.Component;

@Component
class InstallmentCostValidator implements RulesValidator {

  private static final double INTEREST = 0.04;
  private static final int MONTHS_IN_THE_BILLING_PERIOD = 12;

  @Override
  public boolean validate(LoanRequestDto loanRequest) {
    double q = 1 + (INTEREST / MONTHS_IN_THE_BILLING_PERIOD);
    double installment =
        loanRequest.getLoanAmount().doubleValue()
            * (Math.pow(q, loanRequest.getNumberOfInstallments()))
            * (q - 1)
            / ((Math.pow(q, loanRequest.getNumberOfInstallments())) - 1);
    return loanRequest
            .getCustomerMonthlyIncome()
            .multiply(java.math.BigDecimal.valueOf(0.15))
            .compareTo(java.math.BigDecimal.valueOf(installment))
        == -1;
  }
}
