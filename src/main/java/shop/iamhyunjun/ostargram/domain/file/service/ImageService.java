package shop.iamhyunjun.ostargram.domain.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    //AwsConfig에 Bean 등록 해둬서 의존성 주입 받음
    private final S3Client s3;

    // 파일 업로드인데 메서드를 추출해뒀음
    public String uploadFile(MultipartFile multipartFile) {
        //컨텐츠 타입명
        String contentType = multipartFile.getContentType();
        //업로드한 파일명
        String originalFilename = multipartFile.getOriginalFilename();
        //저장되는 파일명
        String storeFileName = createStoreFileName(originalFilename);

        putS3Object(s3, bucket, storeFileName, multipartFile, contentType);

        return getURL(s3, bucket, storeFileName).toString();
    }

    // S3 버킷에 저장하는 메소드
    public static String putS3Object(S3Client s3, String bucketName, String objectKey, MultipartFile multipartFile, String contentType) {

        // request
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType(contentType)  // 이미지 보기
                .build();

        PutObjectResponse response = s3.putObject(putOb, RequestBody.fromBytes(getObjectFile(multipartFile)));
        return response.eTag();
    }

    // 이미지의 byte 배열을 구하는 매서드
    // inputStream close 하려고 씀
    private static byte[] getObjectFile(MultipartFile multipartFile) {

        InputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            bytesArray = multipartFile.getBytes();
            fileInputStream = multipartFile.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

    //이미지 URL 얻어오는 메서드
    public URL getURL(S3Client s3, String bucketName, String keyName) {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucket)
                .key(keyName)
                .build();
        try {
            URL url = s3.utilities().getUrl(request);
            System.out.println("The URL for  " + keyName + " is " + url);
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return s3.utilities().getUrl(request);
    }

    //저장되는 파일명 만드는 메서드
    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext; //확장자를 붙여주기 위한 작업
    }

    //확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
