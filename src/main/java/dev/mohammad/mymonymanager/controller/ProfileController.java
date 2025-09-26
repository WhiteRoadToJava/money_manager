package dev.mohammad.mymonymanager.controller;


import dev.mohammad.mymonymanager.dbo.ProfileDTO;
import dev.mohammad.mymonymanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> toDTO(@RequestBody ProfileDTO profileDTO){
        ProfileDTO registerProfile = profileService.registerProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerProfile);
    }

    // to do the email activation step 7
    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token){
        boolean isActivated = profileService.activateProfile(token);
        if(isActivated){
            return ResponseEntity.ok("profile activated successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Activation Token is not found or already used.");
        }
    }
}
