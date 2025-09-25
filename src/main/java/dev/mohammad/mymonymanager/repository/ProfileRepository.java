package dev.mohammad.mymonymanager.repository;

import dev.mohammad.mymonymanager.entity.ProfileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {

    // select * from tbl_profiles whwre email = ?
    Optional<ProfileEntity> findByEmail(String email);
}
