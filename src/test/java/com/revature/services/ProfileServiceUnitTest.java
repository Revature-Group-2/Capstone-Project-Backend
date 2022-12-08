package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

@ExtendWith(SpringExtension.class)
class ProfileServiceUnitTest {

	@Mock
	private ProfileRepository profileRepository;
	@Mock
	private UserService userService;
	
	@InjectMocks
	ProfileService profileService;
	
	@Test
	public void registerProfileCallsRepositorySave() {
		User user = mock(User.class);
		Profile profile = new Profile();
		profile.setOwner(user);
		when(profileRepository.save(profile)).thenReturn(profile);
        profileService.registerProfile(user);
        verify(profileRepository).save(profile);
	}
	
	@Test
	public void getProfileIfReal() {
		int id = 0;
		Profile profile = mock(Profile.class);
		when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
		try {
			profileService.getProfile(id);
            verify(profileRepository).findById(id);
        } catch (ProfileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}

	@Test
	public void getProfileThrowsIfEmpty() {
		int id = 0;
		when(profileRepository.findById(id)).thenReturn(Optional.empty());
		try {
			profileService.getProfile(id);
            fail();
        } catch (ProfileNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void getProfileByUserIfReal() {
		User user = new User(0,"","","","");
		Profile profile = mock(Profile.class);
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			try {
				profileService.getProfileByUser(user);
				verify(userService).findByCredentials("","");
			}catch (ProfileNotFoundException e)
			{
				e.printStackTrace();
	            fail();
			}
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
	
	@Test
	public void getProfileByUserThrowsNotFoundUser() {
		User user = new User(0,"","","","");
		Profile profile = mock(Profile.class);
		when(userService.findByCredentials("","")).thenReturn(Optional.empty());
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.of(profile));
			try {
				profileService.getProfileByUser(user);
				fail();
			}catch (ProfileNotFoundException e)
			{
				e.printStackTrace();
	            fail();
			}
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	@Test
	public void getProfileByUserThrowsNotFoundProfile() {
		User user = new User(0,"","","","");
		when(userService.findByCredentials("","")).thenReturn(Optional.of(user));
		try {
			when(profileRepository.findByOwner(user)).thenReturn(Optional.empty());
			try {
				profileService.getProfileByUser(user);
				fail();
			}catch (ProfileNotFoundException e)
			{
				e.printStackTrace();
	            return;
			}
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        }
	}
}
