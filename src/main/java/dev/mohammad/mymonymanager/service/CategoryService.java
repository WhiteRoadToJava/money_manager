package dev.mohammad.mymonymanager.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.mohammad.mymonymanager.dbo.CategoryDTO;
import dev.mohammad.mymonymanager.entity.CategoryEntity;
import dev.mohammad.mymonymanager.entity.ProfileEntity;
import dev.mohammad.mymonymanager.repository.CategoryRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
public class CategoryService {
    
        private final CategoryRepository categoryRepository;
        private final ProfileService profileService;

        // save category
        public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
                ProfileEntity Profile = profileService.getCurrentProfile();
                if (categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), Profile.getId())) {
                        throw new RuntimeException("Category with the same name already exists");
                }
                CategoryEntity newCategory = toEntity(categoryDTO, Profile);
                newCategory = categoryRepository.save(newCategory);
                return toDTO(newCategory);
        }

        public List<CategoryDTO> getCategoriesForCurrentUser() {
                ProfileEntity Profile = profileService.getCurrentProfile();
                List<CategoryEntity> catgories = categoryRepository.findByProfileId(Profile.getId());
                return catgories.stream().map(this::toDTO).collect(Collectors.toList());
        }

        public List<CategoryDTO> categoriesByTypeForCurrentUser(String type) {
                ProfileEntity Profile = profileService.getCurrentProfile();
                List<CategoryEntity> categories = categoryRepository.findByTypeAndProfileId(type, Profile.getId());
                return categories.stream().map(this::toDTO).collect(Collectors.toList());
        }

        public CategoryDTO updateCategory(Long categoryId ,CategoryDTO categoryDTO) {
                ProfileEntity profile = profileService.getCurrentProfile();

                 CategoryEntity existedCategory = categoryRepository.findByIdAndProfileId(categoryId, profile.getId())
                         .orElseThrow(() -> new RuntimeException("Category with the same id does not exist"));
                 existedCategory.setName(categoryDTO.getName());
                 existedCategory.setIcon(categoryDTO.getIcon());
                 categoryRepository.save(existedCategory);
                 return toDTO(existedCategory);

        }


        private CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity Profile) {
                return CategoryEntity.builder()
                                .id(categoryDTO.getId())
                                .name(categoryDTO.getName())
                                .icon(categoryDTO.getIcon())
                                .profile(Profile)
                                .type(categoryDTO.getType()).
                                build();
        }

        private CategoryDTO toDTO(CategoryEntity entity) {
                return CategoryDTO.builder()
                                .id(entity.getId())
                                .profileId(entity.getProfile().getId() != null ? entity.getProfile().getId() : null)
                                .name(entity.getName())
                                .icon(entity.getIcon())
                                .createdAt(entity.getCreatedAt())
                                .updatedAt(entity.getUpdatedAt())
                                .type(entity.getType())
                                .build();
        }
}