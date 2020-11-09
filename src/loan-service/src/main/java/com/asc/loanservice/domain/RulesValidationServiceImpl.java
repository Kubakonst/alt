package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
class RulesValidationServiceImpl implements RulesValidationService {

  private List<RulesValidator> validators;

  public RulesValidationServiceImpl(List<RulesValidator> validators) {
    this.validators = validators;
  }

  @Override
  public boolean validate(LoanRequestDto loanRequest) {
    return validators.stream().allMatch(validator -> validator.validate(loanRequest));
  }
}
