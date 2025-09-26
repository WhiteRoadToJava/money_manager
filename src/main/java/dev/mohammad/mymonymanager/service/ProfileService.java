package dev.mohammad.mymonymanager.service;


import dev.mohammad.mymonymanager.dbo.ProfileDTO;
import dev.mohammad.mymonymanager.entity.ProfileEntity;
import dev.mohammad.mymonymanager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final EmailService emailService;


    public ProfileDTO registerProfile(ProfileDTO profileDTO) {
        ProfileEntity newProfileEntity = toEntity(profileDTO);
        // to do the email activation- step 3
        newProfileEntity.setActivationToken(UUID.randomUUID().toString());
        newProfileEntity = profileRepository.save(newProfileEntity);


        // to do the wmail activation step 4
        String activationLink = "http://localhost:8080/api/v1.0/activate?token=" + newProfileEntity.getActivationToken();
        String subject = "activate your Money Manager account";
        String body = "Click on the following link to activate your account:" + activationLink;
        emailService.sendEmail(newProfileEntity.getEmail(), subject, body);


        return toDTO(newProfileEntity);
    }

    public ProfileEntity toEntity(ProfileDTO profileDTO) {


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

    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }


    // to do the email activation step 6
    public boolean activateProfile(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(profile ->{
                    profile.setIsActive(true);
                    profileRepository.save(profile);
                    return true;
                })
                .orElse(false);

    }


}
