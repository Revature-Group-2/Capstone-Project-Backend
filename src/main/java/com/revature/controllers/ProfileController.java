package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.ChangePasswordDTO;
import com.revature.dtos.GeneralInformationDTO;
import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.NoNameException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongPasswordException;
import com.revature.models.Message;
import com.revature.models.Profile;
import com.revature.models.User;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.ProfileService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    
    @Authorized
    @GetMapping
    public ResponseEntity<Object> getOwnProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {            
            return ResponseEntity.ok(profileService.getProfileByUser(user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            // If a user exists in one table and does not exist in another, it means it was created manually in data.sql
            return ResponseEntity.ok(profileService.registerProfile(user));
        }  
    }

    
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfile(@PathVariable int id) {
        try {
            Profile profile = profileService.getProfile(id);

            return ResponseEntity.ok(profile);
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Authorized
    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            user = profileService.changePassword(changePasswordDTO, user);
            session.setAttribute("user", user);
            return ResponseEntity.ok().body(new Message<User>("The password is successfully updated", user));
        } catch (WrongPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EmailReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Not Tested */
    @Authorized
    @GetMapping("/general-information")
    public ResponseEntity<Object> getGeneralInformation(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            GeneralInformationDTO generalInformation = profileService.getGeneralInProfile(user);
            return ResponseEntity.ok().body(generalInformation);
        } catch (ProfileNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Not Tested */
    @Authorized
    @PostMapping("/general-information")
    public ResponseEntity<Object> updateGeneralInformation(@RequestBody GeneralInformationDTO generalInfo, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            Profile profile = profileService.updateGeneralInformation(generalInfo, user);

            session.setAttribute("user", profile.getOwner());
            return ResponseEntity.ok().body(new Message<Profile>("The password is successfully updated", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoNameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EmailReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
