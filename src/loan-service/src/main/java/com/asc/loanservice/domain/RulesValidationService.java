package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestDto;

interface RulesValidationService {
  boolean validate(LoanRequestDto loanRequest);
}
