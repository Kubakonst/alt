package com.asc.loanservice.repository;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class LoanRepositoryImpl implements LoanRepository {

  private static final String SAVE_QUERY =
      "INSERT INTO loanRequest (loanRequestNumber, customerName, customerBirthday, customerTaxId, customerMonthlyIncome, loanAmount, numberOfInstallments, "
          + "firstInstallmentDate, evaluationResult, registrationDate) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

  private static final String FIND_LOAN_QUERY =
      "SELECT loanRequestNumber, customerName, customerBirthday, customerTaxId, customerMonthlyIncome, loanAmount, numberOfInstallments, \"\n"
          + "      + \"firstInstallmentDate, evaluationResult, registrationDate FROM loanrequest";

  public void save(LoanRequestDataDto loanRequestDataDto) {
    try {
      Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
      PreparedStatement preparedStatement = conn.prepareStatement(SAVE_QUERY);
      preparedStatement.setString(1, loanRequestDataDto.getLoanRequestNumber());
      preparedStatement.setString(2, loanRequestDataDto.getCustomerName());
      preparedStatement.setDate(3, Date.valueOf(loanRequestDataDto.getCustomerBirthday()));
      preparedStatement.setString(4, loanRequestDataDto.getCustomerTaxId());
      preparedStatement.setBigDecimal(5, loanRequestDataDto.getCustomerMonthlyIncome());
      preparedStatement.setBigDecimal(6, loanRequestDataDto.getLoanAmount());
      preparedStatement.setInt(7, loanRequestDataDto.getNumberOfInstallments());
      preparedStatement.setDate(8, Date.valueOf(loanRequestDataDto.getFirstInstallmentDate()));
      preparedStatement.setString(9, loanRequestDataDto.getEvaluationResult().name());
      preparedStatement.setTimestamp(
          10, Timestamp.valueOf(loanRequestDataDto.getRegistrationDate()));

      preparedStatement.close();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public LoanRequestDataDto findByLoanRequestNumber(String loanNumber) {
    try {
      Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
      PreparedStatement ps = conn.prepareStatement(FIND_LOAN_QUERY);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String loanRequestNumber = rs.getString(1);
        String customerName = rs.getString(2);
        LocalDate customerBirthday = rs.getDate(3).toLocalDate();
        String customerTaxId = rs.getString(4);
        BigDecimal customerMonthlyIncome = rs.getBigDecimal(5);
        BigDecimal loanAmount = rs.getBigDecimal(6);
        Integer numberOfInstallments = rs.getInt(7);
        LocalDate firstInstallmentDate = rs.getDate(8).toLocalDate();
        LoanRequestEvaluationResult evaluationResult =
            LoanRequestEvaluationResult.valueOf(rs.getString(9));
        LocalDateTime registrationDate = rs.getTimestamp(10).toLocalDateTime();
        ps.close();
        return new LoanRequestDataDto(
            loanRequestNumber,
            customerName,
            customerBirthday,
            customerTaxId,
            customerMonthlyIncome,
            loanAmount,
            numberOfInstallments,
            firstInstallmentDate,
            evaluationResult,
            registrationDate);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }
}
