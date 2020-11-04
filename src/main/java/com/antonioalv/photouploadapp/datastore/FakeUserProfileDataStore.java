package com.antonioalv.photouploadapp.datastore;

import com.antonioalv.photouploadapp.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("fa5e9eac-c189-4167-af05-ae8426fec982"), "janetjones", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("bb41c880-1dc5-48b6-8cc9-98fdb1ce768f"), "antoniojunior", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
