package lab.cherry.nw.configuration.bean;

import io.minio.MinioClient;
import io.minio.admin.MinioAdminClient;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * ClassName : MinioConfig
 * Type : class
 * Description : Minio 연결에 필요한 정보를 포함하고 있는 클래스입니다.
 * Related : All
 * </pre>
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MinioConfig {
	
	@Value("${minio.url}")
    private String minioUrl;

	@Value("${minio.access-key}")
    private String accessKey;

	@Value("${minio.secret-key}")
    private String secretKey;


	@Bean
    public MinioAdminClient minioAdminClient() throws MinioException {

		log.error("url = {}", minioUrl);
		log.error("accessKey = {}", accessKey);
		log.error("secretKey = {}", secretKey);

		// Minio 클라이언트 생성
		MinioAdminClient adminClient = MinioAdminClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();

		// 연결 및 작업 시간 제한 설정
		adminClient.setTimeout(10000, 10000, 10000); // connectTimeout, writeTimeout, readTimeout

		return adminClient;
	}

	@Bean
    public MinioClient minioClient() throws MinioException {

		log.error("url = {}", minioUrl);
		log.error("accessKey = {}", accessKey);
		log.error("secretKey = {}", secretKey);

        // Minio 클라이언트 생성
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();

        // 연결 및 작업 시간 제한 설정
        minioClient.setTimeout(10000, 10000, 10000); // connectTimeout, writeTimeout, readTimeout

        return minioClient;
    }

	public boolean isMinioConnected() {
		try {
			// 버킷 목록을 가져와서 Minio 연결 상태 확인
			minioClient().listBuckets();
			return true; // 연결이 성공적으로 확인됨
		} catch (Exception e) {
			// Minio 연결 예외 처리
			return false; // 연결 실패
		}
	}

}