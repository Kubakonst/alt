package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestDto;
import org.springframework.stereotype.Service;

class MockRulesValidationService implements RulesValidationService {

  @Override
  public boolean validate(LoanRequestDto loanRequest) {
    return true;
  }
}
