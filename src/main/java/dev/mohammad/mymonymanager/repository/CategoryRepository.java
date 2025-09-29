package dev.mohammad.mymonymanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.mohammad.mymonymanager.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
        List<CategoryEntity> findByProfileId(Long profileId);

        // select * from category where id = ? and profile_id = ?
        Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);
 
        // select * from category where type = ? and profile_id = ?
        // if the user will get all income categories or expense categories 
        List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);

        // check if a category with the same name already exists for the given profile
        Boolean existsByNameAndProfileId(String name, Long profileId);
}