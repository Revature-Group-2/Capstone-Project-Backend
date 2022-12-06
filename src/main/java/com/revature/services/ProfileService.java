package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    public Profile registerProfile(User user) {
        Profile profile = new Profile();
        profile.setOwner(user);

        return profileRepository.save(profile);
    }

    public Profile getProfileByUser(User user) throws ProfileNotFoundException, UserNotFoundException {
        Optional<User> optionalUser = userService.findByCredentials(user.getEmail(), user.getPassword());

        if (optionalUser.isEmpty())
            throw new UserNotFoundException("The user has not been found");

        Optional<Profile> optionalProfile = profileRepository.findByOwner(optionalUser.get());

        if (optionalProfile.isEmpty()) 
            throw new ProfileNotFoundException("The profile related to the user " + user.getFirstName() + " "
                                               + user.getLastName() + " has not been found");

        return optionalProfile.get();
    }

    public Profile getProfile(int id) throws ProfileNotFoundException {
        Optional<Profile> optionalProfile  = profileRepository.findById(id);

        if (optionalProfile.isEmpty()) 
            throw new ProfileNotFoundException ("The profile related to the profile with ID " + id + " has not been found");
        
        return optionalProfile.get();
    }
}
