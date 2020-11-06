package com.asc.loanservice.contracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestRegistrationResultDto {
    private String loanRequestNumber;
    private LoanRequestEvaluationResult evaluationResult;

    public LoanRequestRegistrationResultDto mapFromLoanRequestDataDto (LoanRequestDataDto loanRequestDataDto){
        return new LoanRequestRegistrationResultDto(loanRequestDataDto.getLoanRequestNumber(), loanRequestDataDto.getEvaluationResult());
    }
}
