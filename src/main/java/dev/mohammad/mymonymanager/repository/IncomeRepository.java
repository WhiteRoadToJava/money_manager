package dev.mohammad.mymonymanager.repository;

import dev.mohammad.mymonymanager.entity.ExpenseEntity;
import dev.mohammad.mymonymanager.entity.IncomeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity,Long> {


    // Retrieves all incomes for a profile, ordered by date descending
    List<IncomeEntity> findByProfileIdOrderByDateDesc(Long profileId);

    // Retrieves the top 5 most recent incomse for a profile
    List<IncomeEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);



    @Query("SELECT SUM(e.amount) FROM IncomeEntity e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

    List<IncomeEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

    List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);}

