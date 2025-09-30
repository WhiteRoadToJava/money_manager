package dev.mohammad.mymonymanager.service;


import dev.mohammad.mymonymanager.dbo.ExpenseDTO;
import dev.mohammad.mymonymanager.entity.CategoryEntity;
import dev.mohammad.mymonymanager.entity.ExpenseEntity;
import dev.mohammad.mymonymanager.entity.ProfileEntity;
import dev.mohammad.mymonymanager.repository.CategoryRepository;
import dev.mohammad.mymonymanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;


    public ExpenseDTO addExpense(ExpenseDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category  =  categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
        ExpenseEntity newExpense = toEntity(dto, profile, category);
        newExpense = expenseRepository.save(newExpense);
        return toDTO(newExpense);
    }

    // Retrieves all expenses for current month/based
    public List<ExpenseDTO> getCurrentMonthExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.plusDays(now.lengthOfMonth());
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void deleteExpense(Long ExpenseId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        ExpenseEntity entity =  expenseRepository.findById(ExpenseId)
                .orElseThrow(() -> new RuntimeException("Expense Not Found"));
        if (!entity.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Unauthorized to delete this Expense");
        }
        expenseRepository.delete(entity);
    }

    // get latest 5 expenses for current user
    public List<ExpenseDTO> getLatest5ExpenseForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BigDecimal getTotalExpenseForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal total = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return total != null ? total : BigDecimal.ZERO;
    }

    private ExpenseEntity toEntity(ExpenseDTO expenseDTO, ProfileEntity profile, CategoryEntity category) {
        return ExpenseEntity.builder()
                .id(expenseDTO.getId())
                .amount(expenseDTO.getAmount())
                .name(expenseDTO.getName())
                .icon(expenseDTO.getIcon())
                .category(category)
                .date(expenseDTO.getDate())
                .profile(profile)
                .build();
    }

    private ExpenseDTO toDTO(ExpenseEntity entity){
        return ExpenseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName():"N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreationDate())
                .updatedAt(entity.getUpdateDate())
                .build();
    }

}
