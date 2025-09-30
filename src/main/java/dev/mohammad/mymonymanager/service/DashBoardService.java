package dev.mohammad.mymonymanager.service;


import dev.mohammad.mymonymanager.dbo.ExpenseDTO;
import dev.mohammad.mymonymanager.dbo.IncomeDTO;
import dev.mohammad.mymonymanager.dbo.RecentTransactionDTO;
import dev.mohammad.mymonymanager.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Stream.concat;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final ProfileService profileService;

    public Map<String, Object> getDashboardData(){
        ProfileEntity profile = profileService.getCurrentProfile();
        Map<String, Object> returnedValue  = new LinkedHashMap<>();
        List<IncomeDTO> latestIncomes = incomeService.getLatest5IncomeForCurrentUser();
        List<ExpenseDTO> latestExpenses = expenseService.getCurrentExpensesForCurrentUser();
        List<RecentTransactionDTO> recentTramsaction = concat(latestIncomes.stream().map(incom ->
                     RecentTransactionDTO.builder()
                             .id(incom.getId())
                             .profileId(profile.getId())
                             .icon(incom.getIcon())
                             .name(incom.getName())
                             .amount(incom.getAmount())
                             .date(incom.getDate())
                             .createdAt(incom.getCreatedAt())
                             .updatedAt(incom.getUpdatedAt())
                             .type("income")
                             .build()),
                     latestExpenses.stream().map(expense ->
                             RecentTransactionDTO.builder()
                                     .id(expense.getId())
                                     .profileId(profile.getId())
                                     .icon(expense.getIcon())
                                     .name(expense.getName())
                                     .amount(expense.getAmount())
                                     .date(expense.getDate())
                                     .createdAt(expense.getCreatedAt())
                                     .updatedAt(expense.getUpdatedAt())
                                     .type("expense")
                                     .build()))
                     .sorted((a, b)->{
                         int cmp = b.getDate().compareTo(a.getDate());
                         if(cmp == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null){
                             return b.getCreatedAt().compareTo(a.getCreatedAt());
                         }
                         return cmp;
                     }).collect(Collectors.toList());
             returnedValue.put("totalBalence", incomeService.getTotalIncomeForCurrentUser().subtract(
                     expenseService.getTotalExpenseForCurrentUser()));
             returnedValue.put("latestIncomes", incomeService.getTotalIncomeForCurrentUser());
             returnedValue.put("latestExpenses", expenseService.getTotalExpenseForCurrentUser());
             returnedValue.put("recentExpenses", latestExpenses);
             returnedValue.put("recentIncomes", latestIncomes);
             returnedValue.put("recentTransactions", recentTramsaction);
             return returnedValue;
    }
}
