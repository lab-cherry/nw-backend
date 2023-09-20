package lab.cherry.nw.service.Impl;

import io.minio.*;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.UserInfo;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.service.MinioService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

	private final MinioClient minioClient;
	private final MinioAdminClient adminClient;
	
	public boolean isMinioConnected() {
		try {
			// 버킷 목록을 가져와서 Minio 연결 상태 확인
			minioClient.listBuckets();
			return true; // 연결이 성공적으로 확인됨
		} catch (Exception e) {
			// Minio 연결 예외 처리
			return false; // 연결 실패
		}
	}
	public Map<String, UserInfo> listUsers() throws InvalidCipherTextException, NoSuchAlgorithmException, IOException, InvalidKeyException {

        return adminClient.listUsers();
	}

	public void newUser(UserEntity user) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidCipherTextException {
		
		// 새 사용자를 생성합니다.
		String userAccessKey = user.getId();
		String userSecretKey = user.getPassword();

		adminClient.addUser(userAccessKey, UserInfo.Status.ENABLED, userSecretKey, null, null);
			
	}

	public void deleteUser(UserEntity user) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidCipherTextException {

		// 사용자를 삭제합니다.
		String userAccessKey = user.getId();

		adminClient.deleteUser(userAccessKey);

	}
	
	public void setUserPolicy(String bucketName, String userId) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
		
		log.error("Minio Connect Check : {}", isMinioConnected());
		
		adminClient.setPolicy(userId, false, bucketName);
		
	}
	
	public void createGlobalPolicy(String bucketName) throws NoSuchAlgorithmException, IOException, InvalidKeyException {

        log.error("Minio Connect Check : {}", isMinioConnected());
		
		// TODO: bucketName-admin 또는 bucketName-user 권한으로 구분 기능 추후 개발 필요
		String policyJson = "{" +
							"\"Version\": \"2012-10-17\"," +
							"\"Statement\": [" +
							"{" +
							"\"Action\": [" +
							"\"s3:GetObject\", " +
							"\"s3:PutObject\", " +
							"\"s3:DeleteObject\", " +
							"\"s3:ListBucket\"], " +
							"\"Effect\": \"Allow\", " +
							"\"Resource\": [\"arn:aws:s3:::" + bucketName + "/*\"], " + // bucketName 변수 사용
							"\"Sid\": \"\"" + // Sid (선택 사항) 추가
							"}" +
							"]" +
							"}";

		adminClient.addCannedPolicy(bucketName, policyJson.replaceAll("'", "\""));

    }
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<String> listObjects(String bucketName, String prefix) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			// bucketName에 있는 objectName을 조회합니다.

			Iterable<Result<Item>> objects = null;

			if (prefix == null) {
				objects = minioClient.listObjects(ListObjectsArgs.builder()
							.bucket(bucketName)
							.build());
			} else {

				log.error("path is {}", prefix);

				objects = minioClient.listObjects(ListObjectsArgs.builder()
					.bucket(bucketName)
					.prefix(prefix)
					.build());
			}

            List<String> objecLists = new ArrayList<>();
			for (Result<Item> result : objects) {
		        Item item = result.get();
		        log.info("Object Name: {}", item.objectName());
				log.info("Object Size: {}", item.size());
				objecLists.add(item.objectName());
            }

			return objecLists;

		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}

		return null;
	}

	public InputStream getObject(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());
			

			log.error("bucketName is {}, objectName is {}", bucketName, objectName);

			// bucketName에 있는 objectName을 조회합니다.
			InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
			

			return stream;
//			List<String> objectList = FormatConverter.convertInputStreamToList(stream);
//			
//			return objectList;

		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
		
		return null;
	}

	public boolean bucketExists(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			// Check whether 'my-bucketname' exist or not.
			boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

			if (found) {
				log.info("{} bucket이 존재합니다.", bucketName);
				return true;
			} else {
				log.info("{} bucket이 존재하지 않습니다.", bucketName);
				return false;
			}
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}

		return false;
	}

	public void createBucketIfNotExists(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			// bucketName이 존재하는지 확인 후, 없다면 bucket 생성
			if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
				minioClient.makeBucket(MakeBucketArgs.builder()
						.bucket(bucketName)
//						.region("eu-west-1")
//						.objectLock(true)
						.build());
				log.info("{} bucket이 생성 완료되었습니다.", bucketName);
			}
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
	}
	public void uploadObject(String bucketName, String objectName, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			// Object를 업로드합니다.
			minioClient.putObject(
					PutObjectArgs.builder()
					.bucket(bucketName)
					.object(objectName)
					.contentType(file.getContentType())
					.stream(file.getInputStream(), file.getSize(), -1)
				.build()
			);

			log.info("Object가 업로드되었습니다.");
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
	}

	public void setBucketPolicy(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			// BucketName 에 Policy를 설정합니다.
			StringBuilder builder = new StringBuilder();
			builder.append("{\n");
			builder.append("    \"Statement\": [\n");
			builder.append("        {\n");
			builder.append("            \"Action\": [\n");
			builder.append("                \"s3:GetBucketLocation\",\n");
			builder.append("                \"s3:ListBucket\"\n");
			builder.append("            ],\n");
			builder.append("            \"Effect\": \"Allow\",\n");
			builder.append("            \"Principal\": \"*\",\n");
			builder.append("            \"Resource\": \"arn:aws:s3:::" + bucketName + "\"\n");
			builder.append("        },\n");
			builder.append("        {\n");
			builder.append("            \"Action\": \"s3:GetObject\",\n");
			builder.append("            \"Effect\": \"Allow\",\n");
			builder.append("            \"Principal\": \"*\",\n");
			builder.append("            \"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"\n");
			builder.append("        }\n");
			builder.append("    ],\n");
			builder.append("    \"Version\": \"2012-10-17\"\n");
			builder.append("}\n");

			minioClient.setBucketPolicy(
					SetBucketPolicyArgs.builder().bucket(bucketName).config(builder.toString()).build());

			log.info("{} Policy 생성이 완료되었습니다.", minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build()));
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
	}

	public void deleteBucket(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			// Bucket를 삭제합니다.
			boolean found =
          minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
			if (found) {
				minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
				log.info("{} bucket이 삭제되었습니다.", bucketName);
			} else {
				log.info("{} bucket을 찾을 수 없습니다.", bucketName);
			}
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
	}

	public void deleteObject(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			// Object를 삭제합니다.
			minioClient.removeObject(
					RemoveObjectArgs.builder()
						.bucket(bucketName)
						.object(objectName)
					.build());

			log.info("Object가 삭제되었습니다.");
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
	}

	public void deleteObjects(String bucketName, List<DeleteObject> objectList) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {

			log.error("Minio Connect Check : {}", isMinioConnected());

			//			List<DeleteObject> objects = new LinkedList<>();
			//			objects.add(new DeleteObject("my-objectname1"));
			//			objects.add(new DeleteObject("my-objectname2"));
			//			objects.add(new DeleteObject("my-objectname3"));

			Iterable<Result<DeleteError>> results =
				minioClient.removeObjects(
						RemoveObjectsArgs.builder().bucket(bucketName).objects(objectList).build());
			for (Result<DeleteError> result : results) {
				DeleteError error = result.get();
				log.info("{} Object 삭제하는데 문제가 발생했습니다. {} ", error.objectName(), error.message());
			}

			log.info("ObjectList가 삭제되었습니다.");
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
	}
	
	public String getPresignedURL(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		try {
			
			// minioService.getPresignedURL(user, "test", "subFolder/64ed89aa9e813b5ab16da6dd");
			// expiration 기간 동안 유지되는 링크 제공

			log.error("Minio Connect Check : {}", isMinioConnected());

			Map<String, String> reqParams = new HashMap<String, String>();
			reqParams.put("response-content-type", "application/json");

			// Presigned URL 생성
			return minioClient.getPresignedObjectUrl(
					GetPresignedObjectUrlArgs.builder()
					.method(Method.GET)
					.bucket(bucketName)
					.object(objectName)
					.expiry(1, TimeUnit.HOURS)	// 시간
					.extraQueryParams(reqParams)
					.build());
		} catch (MinioException e) {
			log.error("Error occurred: " + e);
		}
		return null;
	}
}