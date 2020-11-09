package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.CustomerCheckResult;
import com.asc.loanservice.contracts.LoanRequestDto;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DebtorValidator implements RulesValidator {

  private static final String BASE_DEBTOR_CHECK_URL = "http://localhost:8090/api/customercheck/";

  private final RestTemplate restTemplate;

  @Autowired
  public DebtorValidator(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public boolean validate(LoanRequestDto loanRequest) {
    return !Objects.requireNonNull(
        restTemplate
            .getForEntity(
                BASE_DEBTOR_CHECK_URL + loanRequest.getCustomerTaxId(),
                CustomerCheckResult.class)
            .getBody())
        .getIsRegisteredDebtor();
  }
}
