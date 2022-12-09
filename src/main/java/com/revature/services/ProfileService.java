package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.ChangePasswordDTO;
import com.revature.dtos.GeneralInformationDTO;
import com.revature.dtos.ProfileBackgroundDTO;
import com.revature.dtos.ProfileEducationDTO;
import com.revature.dtos.ProfileLocationDTO;
import com.revature.dtos.ProfileMaritalStatusDTO;
import com.revature.dtos.ProfileWorkDTO;
import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.NoNameException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongPasswordException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    public User changePassword(ChangePasswordDTO changePassword, User sessionUser) throws WrongPasswordException, EmailReservedException {
        Optional<User> repoUser = userService.findByCredentials(sessionUser.getEmail(), changePassword.getOldPassword());

        if (repoUser.isEmpty())
            throw new WrongPasswordException("The current password is wrong.");

        User user = repoUser.get();
        user.setPassword(changePassword.getNewPassword());

        return userRepository.save(user);
    }

    public GeneralInformationDTO getGeneralInformation(User sessionUser) throws ProfileNotFoundException {
        Optional<Profile> optionalProfile = profileRepository.findByOwner(sessionUser);

        if (optionalProfile.isEmpty()) 
            throw new ProfileNotFoundException("The profile related to " + sessionUser.getFirstName() + " "
                        + sessionUser.getLastName() + " user not has been found");
        
        Profile profile = optionalProfile.get();

        return new GeneralInformationDTO(profile);
    }

    public Profile updateGeneralInformation(GeneralInformationDTO generalInfo, User sessionUser) throws UserNotFoundException, ProfileNotFoundException, NoNameException, EmailReservedException {
        Optional<User> repoUser = userService.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword());

        if (repoUser.isEmpty())
            throw new UserNotFoundException("The session user has not been found. Try to re-login");

        User user = repoUser.get();

        Profile profile = getProfileByUser(user);

        if (generalInfo.getFirstName().trim().equals(""))
            throw new NoNameException("The first name cannot be empty or consist of spaces");
        user.setFirstName(generalInfo.getFirstName());

        if (generalInfo.getLastName().trim().equals(""))
            throw new NoNameException("The last name cannot be empty or consist of spaces");
        user.setLastName(generalInfo.getLastName());
        user.setEmail(generalInfo.getEmail());
        
        profile.setOwner(user);
        profile.setGender(generalInfo.getGender());
        profile.setDob(generalInfo.getDob());
        profile.setPhoneNumber(generalInfo.getPhoneNumber());

        /* If user's old and new do not match and email already exists in the DB */
        if (!sessionUser.getEmail().equals(generalInfo.getEmail()) && userRepository.findByEmail(generalInfo.getEmail()).isPresent())
            throw new EmailReservedException("The email " + generalInfo.getEmail() + " is being used.");

        userRepository.save(user);
        return profileRepository.save(profile);
    }

    public ProfileLocationDTO getProfileLocation(User sessionUser) throws ProfileNotFoundException {
        Optional<Profile> optionalProfile = profileRepository.findByOwner(sessionUser);

        if (optionalProfile.isEmpty()) 
            throw new ProfileNotFoundException("The profile related to " + sessionUser.getFirstName() + " "
                        + sessionUser.getLastName() + " user not has been found");
        
        Profile profile = optionalProfile.get();

        return new ProfileLocationDTO(profile);
    }

    public Profile updateProfileLocation(ProfileLocationDTO profileLocation, User sessionUser) throws UserNotFoundException, ProfileNotFoundException {
        Optional<User> repoUser = userService.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword());

        if (repoUser.isEmpty())
            throw new UserNotFoundException("The session user has not been found. Try to re-login");

        User user = repoUser.get();

        Profile profile = getProfileByUser(user);

        profile.setCurrentCity(profileLocation.getCurrentCity());
        profile.setCurrentCountry(profileLocation.getCurrentCountry());
        profile.setBornCity(profileLocation.getBornCity());
        profile.setBornCountry(profileLocation.getBornCountry());

        return profileRepository.save(profile);
    }

    public ProfileEducationDTO getProfileEducation(User sessionUser) throws ProfileNotFoundException {
        Optional<Profile> optionalProfile = profileRepository.findByOwner(sessionUser);

        if (optionalProfile.isEmpty()) 
            throw new ProfileNotFoundException("The profile related to " + sessionUser.getFirstName() + " "
                        + sessionUser.getLastName() + " user not has been found");
        
        Profile profile = optionalProfile.get();

        return new ProfileEducationDTO(profile);
    }


    public Profile updateProfileEducation(ProfileEducationDTO profileEducation, User sessionUser) throws UserNotFoundException, ProfileNotFoundException {
        Optional<User> repoUser = userService.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword());

        if (repoUser.isEmpty())
            throw new UserNotFoundException("The session user has not been found. Try to re-login");

        User user = repoUser.get();

        Profile profile = getProfileByUser(user);

        profile.setSchoolName(profileEducation.getSchoolName());

        return profileRepository.save(profile);
    }


    public ProfileWorkDTO getProfileWork(User sessionUser) throws ProfileNotFoundException {
        Optional<Profile> optionalProfile = profileRepository.findByOwner(sessionUser);

        if (optionalProfile.isEmpty()) 
            throw new ProfileNotFoundException("The profile related to " + sessionUser.getFirstName() + " "
                        + sessionUser.getLastName() + " user not has been found");
        
        Profile profile = optionalProfile.get();

        return new ProfileWorkDTO(profile);
    }

    public Profile updateProfileWork(ProfileWorkDTO profileWork, User sessionUser) throws UserNotFoundException, ProfileNotFoundException {
        Optional<User> repoUser = userService.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword());

        if (repoUser.isEmpty())
            throw new UserNotFoundException("The session user has not been found. Try to re-login");

        User user = repoUser.get();

        Profile profile = getProfileByUser(user);

        profile.setJobTitle(profileWork.getJobTitle());
        profile.setCompanyName(profileWork.getCompanyName());
        profile.setCompanyUrl(profileWork.getCompanyUrl());

        return profileRepository.save(profile);
    }


    public ProfileMaritalStatusDTO getProfileMaritalStatus(User sessionUser) throws ProfileNotFoundException {
        Optional<Profile> optionalProfile = profileRepository.findByOwner(sessionUser);

        if (optionalProfile.isEmpty()) 
            throw new ProfileNotFoundException("The profile related to " + sessionUser.getFirstName() + " "
                        + sessionUser.getLastName() + " user not has been found");
        
        Profile profile = optionalProfile.get();

        return new ProfileMaritalStatusDTO(profile);
    }


    public Profile updateProfileMaritalStatus(ProfileMaritalStatusDTO profileMaritalStatus, User sessionUser) throws UserNotFoundException, ProfileNotFoundException {
        Optional<User> repoUser = userService.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword());

        if (repoUser.isEmpty())
            throw new UserNotFoundException("The session user has not been found. Try to re-login");

        User user = repoUser.get();

        Profile profile = getProfileByUser(user);

        profile.setMaritalStatus(profileMaritalStatus.getMaritalStatus());

        return profileRepository.save(profile);
    }

    public Profile updateProfileBackground(ProfileBackgroundDTO profileBackground, User sessionUser) throws UserNotFoundException, ProfileNotFoundException {
        Optional<User> repoUser = userService.findByCredentials(sessionUser.getEmail(), sessionUser.getPassword());

        if (repoUser.isEmpty())
            throw new UserNotFoundException("The session user has not been found. Try to re-login");

        User user = repoUser.get();

        Profile profile = getProfileByUser(user);

        profile.setBackgroundImageUrl(profileBackground.getUrl());

        return profileRepository.save(profile);
    }


    
    
}
