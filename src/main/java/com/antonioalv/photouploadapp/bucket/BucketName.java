package com.antonioalv.photouploadapp.bucket;

public enum BucketName {

    PROFILE_IMAGE("photo-upload-app");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
