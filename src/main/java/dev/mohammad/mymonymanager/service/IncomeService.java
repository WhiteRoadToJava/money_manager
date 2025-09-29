package dev.mohammad.mymonymanager.service;


import dev.mohammad.mymonymanager.dbo.ExpenseDTO;
import dev.mohammad.mymonymanager.dbo.IncomeDTO;
import dev.mohammad.mymonymanager.entity.CategoryEntity;
import dev.mohammad.mymonymanager.entity.ExpenseEntity;
import dev.mohammad.mymonymanager.entity.IncomeEntity;
import dev.mohammad.mymonymanager.entity.ProfileEntity;
import dev.mohammad.mymonymanager.repository.CategoryRepository;
import dev.mohammad.mymonymanager.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {


    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;


    public IncomeDTO addIncome(IncomeDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category  =  categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
        IncomeEntity newIncome = toEntity(dto, profile, category);
        newIncome = incomeRepository.save(newIncome);
        return toDTO(newIncome);
    }

    private IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profile, CategoryEntity category) {
        return IncomeEntity.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .name(dto.getName())
                .icon(dto.getIcon())
                .category(category)
                .date(dto.getDate())
                .profile(profile)
                .build();
    }

    private IncomeDTO toDTO(IncomeEntity entity){
        return IncomeDTO.builder()
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
