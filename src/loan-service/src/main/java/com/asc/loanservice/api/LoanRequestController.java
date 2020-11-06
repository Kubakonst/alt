package com.asc.loanservice.api;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.LoanRequestService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanRequestController {
  private final LoanRequestService loanRequestService;

  @PostMapping
  public LoanRequestRegistrationResultDto register(@RequestBody @Valid LoanRequestDto loanRequest) {
    return loanRequestService.register(loanRequest);
  }

  @GetMapping("/{loanNumber}")
  public LoanRequestDataDto getByNumber(@PathVariable("loanNumber") String loanNumber) {
    return loanRequestService.getByNumber(loanNumber);
  }
}
