package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestDto;

interface RulesValidator {
  boolean validate(LoanRequestDto loanRequest);
}
