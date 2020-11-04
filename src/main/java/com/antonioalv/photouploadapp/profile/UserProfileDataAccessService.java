package com.antonioalv.photouploadapp.profile;

import com.antonioalv.photouploadapp.datastore.FakeUserProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserProfileDataAccessService {

    private final FakeUserProfileDataStore fakeUserProfileDataStore;

    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDataStore fakeUserProfileDataStore) {
        this.fakeUserProfileDataStore = fakeUserProfileDataStore;
    }

    List<UserProfile> getUserProfiles() {
        return fakeUserProfileDataStore.getUserProfiles();
    }

    UserProfile getUserProfile(UUID userProfileId) {
        return fakeUserProfileDataStore.getUserProfiles()
                                        .stream()
                                        .filter(user -> user.getUserProfileId().equals(userProfileId))
                                        .findFirst()
                                        .orElseThrow(() ->
                                                new IllegalStateException(String.format("User profile not found %d", userProfileId)));
    }
}
