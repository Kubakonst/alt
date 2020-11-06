package com.asc.loanservice.domain;

import com.asc.loanservice.contracts.CustomerCheckResult;
import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.repository.LoanRequestRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoanRequestService {

  private static final double INTEREST = 0.04;
  private static final int MONTHS_IN_THE_BILLINGG_PERIOD = 12;
  private static final String BASE_DEBTOR_CHECK_URL = "http://localhost:8090/api/customercheck/";

  private RestTemplate restTemplate = new RestTemplate();
  private LoanRequestRepository loanRequestRepository = new LoanRequestRepository();

  public LoanRequestRegistrationResultDto register(LoanRequestDto loanRequest) {
    if (!validRules(loanRequest)) {
      LoanRequestDataDto rejectedRequest =
          new LoanRequestDataDto()
              .mapFromLoanRequest(loanRequest, LoanRequestEvaluationResult.REJECTED);
      loanRequestRepository.save(rejectedRequest);
      return new LoanRequestRegistrationResultDto().mapFromLoanRequestDataDto(rejectedRequest);
    }
    LoanRequestDataDto approvedRequest =
        new LoanRequestDataDto()
            .mapFromLoanRequest(loanRequest, LoanRequestEvaluationResult.APPROVED);
    loanRequestRepository.save(approvedRequest);
    return new LoanRequestRegistrationResultDto().mapFromLoanRequestDataDto(approvedRequest);
  }

  public LoanRequestDataDto getByNumber(String loanNumber) {
    return loanRequestRepository.findByLoanRequestNumber(loanNumber);
  }

  private boolean validRules(LoanRequestDto loanRequest) {
    if (isDebtorCustomer(loanRequest.getCustomerTaxId())){
      return false;
    }
    if (isCustomerBefore65AtTheEndOfLoan(
        loanRequest.getCustomerBirthday(), loanRequest.getNumberOfInstallments())){
      return false;
    }
    if (isMonthlyInstallmentTooMuchForCustomer(
        loanRequest.getCustomerMonthlyIncome(),
        loanRequest.getLoanAmount(),
        loanRequest.getNumberOfInstallments())){
      return false;
    }
    return true;
  }

  private boolean isCustomerBefore65AtTheEndOfLoan(
      LocalDate customerBirthday, Integer numberOfInstallments) {
    System.out.println(LocalDate.now()
        .minusYears(customerBirthday.minusMonths(numberOfInstallments).getYear())
        .getYear());
    return LocalDate.now()
            .minusYears(customerBirthday.minusMonths(numberOfInstallments).getYear())
            .getYear()
        >= 65;
  }

  private boolean isMonthlyInstallmentTooMuchForCustomer(
      BigDecimal customerMonthlyIncome, BigDecimal loanAmount, Integer numberOfInstallments) {
    double q = 1 + (INTEREST / MONTHS_IN_THE_BILLINGG_PERIOD);
    double installment =
        loanAmount.doubleValue()
            * (Math.pow(q, numberOfInstallments))
            * (q - 1)
            / ((Math.pow(q, numberOfInstallments)) - 1);
    return customerMonthlyIncome
            .multiply(BigDecimal.valueOf(0.15))
            .compareTo(BigDecimal.valueOf(installment))
        == -1;
  }

  private boolean isDebtorCustomer(String customerTaxId) {
    return Objects.requireNonNull(restTemplate
        .getForEntity(BASE_DEBTOR_CHECK_URL + customerTaxId, CustomerCheckResult.class)
        .getBody())
        .getIsRegisteredDebtor();
  }
}
