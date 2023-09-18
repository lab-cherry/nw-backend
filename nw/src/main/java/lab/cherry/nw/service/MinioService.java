package lab.cherry.nw.service;


import io.minio.admin.UserInfo;
import io.minio.messages.DeleteObject;
import lab.cherry.nw.model.UserEntity;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * ClassName : MinioService
 * Type : interface
 * Description : 파일 관리에 필요한 Minio와 관련된 함수를 정리한 인터페이스입니다.
 * Related : MinioServiceImpl
 * </pre>
 */
@Component
public interface MinioService {
	
	// minioAdminClient
	Map<String, UserInfo> listUsers() throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidCipherTextException;
	void newUser(UserEntity user) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidCipherTextException;
	void deleteUser(UserEntity user) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidCipherTextException;
	
	void setUserPolicy(String bucketName, String userId) throws NoSuchAlgorithmException, IOException, InvalidKeyException;
	void createGlobalPolicy(String bucketName) throws NoSuchAlgorithmException, IOException, InvalidKeyException;
	
	
	// minioClient
	List<String> listObjects(String bucketName, String prefix) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	InputStream getObject(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	boolean bucketExists(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	void createBucketIfNotExists(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	void uploadObject(String bucketName, String objectName, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	void setBucketPolicy(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	void deleteBucket(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	void deleteObject(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	void deleteObjects(String bucketName, List<DeleteObject> objectList) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
	String getPresignedURL(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
}