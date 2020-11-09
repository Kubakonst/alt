package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoanRequestService {

  private RulesValidationService rulesValidationService;

  private LoanRepository loanRepository;

  public LoanRequestService(
      RulesValidationService rulesValidationService, LoanRepository loanRepository) {
    this.rulesValidationService = rulesValidationService;
    this.loanRepository = loanRepository;
  }

  public LoanRequestRegistrationResultDto register(LoanRequestDto loanRequest) {
    if (!rulesValidationService.validate(loanRequest)) {
      LoanRequestDataDto rejectedRequest =
          new LoanRequestDataDto()
              .mapFromLoanRequest(loanRequest, LoanRequestEvaluationResult.REJECTED);
      loanRepository.save(rejectedRequest);
      return new LoanRequestRegistrationResultDto().mapFromLoanRequestDataDto(rejectedRequest);
    }
    LoanRequestDataDto approvedRequest =
        new LoanRequestDataDto()
            .mapFromLoanRequest(loanRequest, LoanRequestEvaluationResult.APPROVED);
    loanRepository.save(approvedRequest);
    return new LoanRequestRegistrationResultDto().mapFromLoanRequestDataDto(approvedRequest);
  }

  public LoanRequestDataDto getByNumber(String loanNumber) {
    return loanRepository.findByLoanRequestNumber(loanNumber);
  }
}
