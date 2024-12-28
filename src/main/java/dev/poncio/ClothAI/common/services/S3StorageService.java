package dev.poncio.ClothAI.common.services;

import dev.poncio.ClothAI.company.CompanyEntity;
import dev.poncio.ClothAI.storage.StorageEntity;
import dev.poncio.ClothAI.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3StorageService {

    @Autowired
    private StorageService storageService;

    public String uploadFile(CompanyEntity company, String fileNameWithPath, InputStream fileInputStream) throws IOException {
        StorageEntity storage = getConfig(company);
        String key = this.buildFileUrl(company, fileNameWithPath);
        get(company).putObject(request ->
                        request
                                .bucket(storage.getBucketName())
                                .key(key),
                RequestBody.fromInputStream(fileInputStream, fileInputStream.available()));
        return key;
    }

    public InputStream downloadFile(CompanyEntity company, String fullPath) {
        StorageEntity storage = getConfig(company);
        ResponseBytes<GetObjectResponse> response = get(company).getObject(request ->
                        request
                                .bucket(storage.getBucketName())
                                .key(this.buildFileUrl(company, fullPath)),
                ResponseTransformer.toBytes());
        return response.asInputStream();
    }

    private S3Client get(CompanyEntity company) {
        StorageEntity storage = getConfig(company);
        AwsCredentials credentials = AwsBasicCredentials.create(storage.getAccessKey(), storage.getSecretKey());
        return S3Client
                .builder()
                .region(Region.of(storage.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    private String buildPublicURL(CompanyEntity company, String fileNameWithPath) {
        StorageEntity storage = getConfig(company);
        return String.format("https://%s.s3.%s.%s/%s",
                storage.getBucketName(),
                storage.getRegion(),
                storage.getServiceEndpoint()
                        .replace("s3.", "")
                        .replace("http://", "")
                        .replace("https://", "")
                        .replace("s3://", ""),
                this.buildFileUrl(company, fileNameWithPath));
    }

    private String buildFileUrl(CompanyEntity company, String fileNameWithPath) {
        StorageEntity storage = getConfig(company);
        String fullPath = new File(storage.getBasePrefix(), fileNameWithPath).getPath();
        fullPath = fullPath.replaceAll("\\\\", "/");
        if (fullPath.charAt(0) == '/') {
            fullPath = fullPath.substring(1);
        }
        return fullPath;
    }

    private StorageEntity getConfig(CompanyEntity company) {
        return this.storageService.getStorageByCompany(company);
    }

}
