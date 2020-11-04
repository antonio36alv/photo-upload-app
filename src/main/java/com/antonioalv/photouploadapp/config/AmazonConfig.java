package com.antonioalv.photouploadapp.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {
        // TODO change to env variables, depending on deployment service
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                System.getenv("AWS_ACCESS_KEY"),
                System.getenv("AWS_SECRET_KEY")
        );

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
