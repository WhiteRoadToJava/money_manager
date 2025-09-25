package dev.mohammad.mymonymanager.service;


import dev.mohammad.mymonymanager.dbo.ProfileDTO;
import dev.mohammad.mymonymanager.entity.ProfileEntity;
import dev.mohammad.mymonymanager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;


public ProfileDTO registerProfile(ProfileDTO profileDTO){
    ProfileEntity newProfileEntity = toEntity(profileDTO);
    newProfileEntity = profileRepository.save(newProfileEntity);



    return toDTO(newProfileEntity);
}

public ProfileEntity toEntity(ProfileDTO profileDTO){


    return ProfileEntity.builder()
            .id(profileDTO.getId())
            .fullName(profileDTO.getFullName())
            .email(profileDTO.getEmail())
            .password(profileDTO.getPassword())
            .profileImageUrl(profileDTO.getProfileImageUrl())
            .createdAt(profileDTO.getCreatedAt())
            .updatedAt(profileDTO.getUpdatedAt())
            .build();
}

public ProfileDTO toDTO(ProfileEntity profileEntity){
    return ProfileDTO.builder()
    .id(profileEntity.getId())
            .fullName(profileEntity.getFullName())
            .email(profileEntity.getEmail())
            .profileImageUrl(profileEntity.getProfileImageUrl())
            .createdAt(profileEntity.getCreatedAt())
            .updatedAt(profileEntity.getUpdatedAt())
            .build();
}


}
