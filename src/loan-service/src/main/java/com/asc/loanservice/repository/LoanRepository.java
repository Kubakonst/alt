package com.asc.loanservice.repository;

import com.asc.loanservice.contracts.LoanRequestDataDto;

public interface LoanRepository {

  void save(LoanRequestDataDto loanRequestDataDto);
  LoanRequestDataDto findByLoanRequestNumber(String loanNumber);

}
