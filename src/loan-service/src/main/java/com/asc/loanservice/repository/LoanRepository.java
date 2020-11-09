package com.asc.loanservice.repository;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import java.util.Optional;

public interface LoanRepository {

  void save(LoanRequestDataDto loanRequestDataDto);
  LoanRequestDataDto findByLoanRequestNumber(String loanNumber);

}
