package dev.mohammad.mymonymanager.repository;

import dev.mohammad.mymonymanager.entity.ExpenseEntity;

import dev.mohammad.mymonymanager.entity.IncomeEntity;
import lombok.Lombok;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {

    // Retrieves all expenses for a profile, ordered by date descending
    List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);

    // Retrieves the top 5 most recent expenses for a profile
    List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);



@Query("SELECT SUM(e.amount) FROM ExpenseEntity e WHERE e.profile.id = :profileId")
BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

    List<ExpenseEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

    List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

List<ExpenseEntity> findByProfileIdAndDate(Long profileId, LocalDate date);
}
