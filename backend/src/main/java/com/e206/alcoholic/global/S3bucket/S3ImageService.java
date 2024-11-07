package com.e206.alcoholic.global.S3bucket;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${AWS_BUCKET_NAME}")
    private final String bucketName;

    public S3ImageService(AmazonS3 amazonS3, @Value("${AWS_BUCKET_NAME}") String bucket) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucket;
    }

    @Transactional
    public String upload(MultipartFile image) {
        String s3FileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(image.getSize());
        objectMetadata.setContentType(image.getContentType());

        try {
            amazonS3.putObject(bucketName, s3FileName, image.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

}
