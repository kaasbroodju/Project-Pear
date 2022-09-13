package com.oep.hu.pear.forms.application;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class FileUploadService {

	private static final String FILE_LOCATION = "uploaded-files/";
	private static final String SALT = System.getenv("file_salt");

	public String saveFile(String uploadDir, String uid, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(FILE_LOCATION + uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
		String savedFileName = hashFileName(FILE_LOCATION + uploadDir, uid) + fileExtension;

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(savedFileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + uid, ioe);
		}
		return savedFileName;
	}

//	public void saveFileUnHashed(String uploadDir, String uid, MultipartFile multipartFile) throws IOException {
//		Path uploadPath = Paths.get(FILE_LOCATION + uploadDir + uid);
//
//		if (!Files.exists(uploadPath)) {
//			Files.createDirectories(uploadPath);
//		}
//
//		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
////		String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
//
//		try (InputStream inputStream = multipartFile.getInputStream()) {
//			Path filePath = uploadPath.resolve(fileName);
//			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException ioe) {
//			throw new IOException("Could not save image file: " + uid, ioe);
//		}
//	}

	public byte[] getFile(String uploadDir, String uid) throws IOException {
		Path uploadPath = Paths.get(FILE_LOCATION + uploadDir);

		if (!Files.exists(uploadPath)) {
			throw new IOException("file not found");
		}

		Path filePath = uploadPath.resolve(uid);
		return Files.readAllBytes(filePath);

	}

	public byte[] getFileHashed(String uploadDir, String uid, String fileExtension) throws IOException {
		Path uploadPath = Paths.get(FILE_LOCATION + uploadDir);

		if (!Files.exists(uploadPath)) {
			throw new IOException("file not found");
		}

		Path filePath = uploadPath.resolve(hashFileName(FILE_LOCATION + uploadDir, uid) + fileExtension);
		return Files.readAllBytes(filePath);

	}

//	public byte[] getFileOutZip(String uploadDir, String uid, String fileName) throws IOException {
//		Path uploadPath = Paths.get(FILE_LOCATION + uploadDir, uid);
//
//		if (!Files.exists(uploadPath)) {
//			throw new IOException("file not found");
//		}
//
//		Path filePath = uploadPath.resolve(fileName);
//		return Files.readAllBytes(filePath);
//	}

//	public List<String> getFileNamesInDir(String uploadDir, String uid) {
//		File[] files = new File(FILE_LOCATION + uploadDir + uid).listFiles();
//		if (files == null) {
//			return Collections.emptyList();
//		} else {
//			return Arrays.stream(files).map(File::getName).toList();
//		}
//	}

//	public byte[] getFilesInDirZipped(String uploadDir, String uid) {
//		File[] files = new File(FILE_LOCATION + uploadDir + uid).listFiles();
//
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try(ZipOutputStream zos = new ZipOutputStream(baos)) {
//			for (File file : files) {
//				ZipEntry entry = new ZipEntry(file.getName());
//
//				zos.putNextEntry(entry);
//				zos.write(Files.readAllBytes(file.toPath()));
//				zos.closeEntry();
//			}
//		} catch(IOException ioe) {
//			ioe.printStackTrace();
//		}
//
//		return baos.toByteArray();
//	}

	public byte[] getFileListZipped(String dir, Map<String, String> locations) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try(ZipOutputStream zos = new ZipOutputStream(baos)) {
			for (Map.Entry<String, String> fileEntry : locations.entrySet()) {
				ZipEntry entry = new ZipEntry(fileEntry.getKey());

				zos.putNextEntry(entry);
				zos.write(Files.readAllBytes(new File(FILE_LOCATION + dir + fileEntry.getValue()).toPath()));
				zos.closeEntry();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}

		return baos.toByteArray();


	}

	public boolean deleteFile(String dir, String fileName) throws IOException {
		return Files.deleteIfExists(new File(FILE_LOCATION + dir + fileName).toPath());
	}

	public static String hashFileName(String dir, String uid) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String s = FILE_LOCATION + dir + uid + SALT;

		md.update(s.getBytes());

		byte[] bytes = md.digest();

		// This bytes[] has bytes in decimal format. Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
