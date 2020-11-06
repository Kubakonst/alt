package com.asc.loanservice.exceptions;

public class LoanNotFoundException extends RuntimeException {
  public LoanNotFoundException(String number) {
    super("No loan of number " + number + " found");
  }
}
