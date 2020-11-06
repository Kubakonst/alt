package com.asc.loanservice.repository;

import static java.util.Objects.requireNonNull;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.exceptions.LoanNotFoundException;
import java.util.HashMap;
import org.springframework.stereotype.Component;

public class LoanRequestRepository {

  private HashMap<String, LoanRequestDataDto> map = new HashMap<>();

  public void save(LoanRequestDataDto loanRequestDataDto) {
    requireNonNull(loanRequestDataDto);
    map.put(loanRequestDataDto.getLoanRequestNumber(), loanRequestDataDto);
  }

  public LoanRequestDataDto findByLoanRequestNumber(String loanNumber) {
    LoanRequestDataDto loanRequestDataDto = map.get(loanNumber);
    if (loanRequestDataDto == null) {
      throw new LoanNotFoundException(loanNumber);
    }
    return loanRequestDataDto;
  }
}