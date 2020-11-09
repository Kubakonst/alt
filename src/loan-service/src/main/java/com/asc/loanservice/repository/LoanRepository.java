package com.asc.loanservice.repository;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import org.springframework.data.repository.Repository;

public interface LoanRepository extends Repository<LoanRequestDataDto, String> {

  LoanRequestDataDto save(LoanRequestDataDto loanRequestDataDto);
  LoanRequestDataDto findByLoanRequestNumber(String loanNumber);

}