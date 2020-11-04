package com.antonioalv.photouploadapp.profile;

import com.antonioalv.photouploadapp.bucket.BucketName;
import com.antonioalv.photouploadapp.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        // 1. check if image is not empty
        // 2. if file is an image
        if(file.isEmpty()) {
            throw new IllegalStateException(String.format("Cannot upload empty file [%d]", file.getSize()));
        }
        if(!file.getContentType().contains("image")) {
            throw new IllegalStateException(String.format("File must be an image"));
        }
        // 3. the user exists in our database
        UserProfile userProfile = userProfileDataAccessService.getUserProfile(userProfileId);
        // 4. grab some metadata from fle if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        // 5. store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfile.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.save(path, filename, Optional.of(metadata),
                    file.getInputStream());
            userProfile.setUserProfileImageLink(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadProfileImage(UUID userProfileId) {
        UserProfile userProfile = userProfileDataAccessService.getUserProfile(userProfileId);

        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                userProfile.getUserProfileId());

        return userProfile.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }
}
