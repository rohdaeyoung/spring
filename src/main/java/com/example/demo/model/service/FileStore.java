package com.example.demo.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

// [11주차 제공] 업로드 파일 저장소 : 프로젝트 폴더/uploads/ 에 저장하고, DB 에는 파일 이름만 남긴다
@Component
public class FileStore {

    // ① 허용한 확장자만 (jsp, html, exe … 는 거부)
    private static final Set<String> ALLOWED = Set.of("jpg", "jpeg", "png", "gif", "webp");

    // ④ 별도 폴더 : 웹 폴더(static) 가 아닌 프로젝트 폴더/uploads/
    private final Path root = Paths.get("uploads").toAbsolutePath().normalize();

    // 파일 저장 → 서버가 새로 지은 파일 이름 (파일을 안 골랐으면 null)
    public String save(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null; // 포스터 없는 글
        }
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (ext == null || !ALLOWED.contains(ext.toLowerCase())) {          // ① 허용한 확장자만
            throw new IllegalArgumentException("이미지 파일(jpg, png, gif, webp)만 올릴 수 있습니다.");
        }
        try (InputStream in = file.getInputStream()) {
            byte[] head = in.readNBytes(12);
            if (!isImage(head)) {                                           // ② 파일 앞부분으로 진짜 이미지인지
                throw new IllegalArgumentException("이미지 파일이 아닙니다.");
            }
            String storedName = UUID.randomUUID() + "." + ext.toLowerCase(); // ③ 이름은 서버가 새로
            Files.createDirectories(root);
            Path target = root.resolve(storedName);
            try (InputStream all = file.getInputStream()) {
                Files.copy(all, target);                                    // ④ uploads/ 에 저장
            }
            return storedName;                                              // → DB 에는 이름만
        } catch (IOException e) {
            throw new UncheckedIOException("파일 저장에 실패했습니다.", e);
        }
    }

    // 파일 삭제 (글을 지울 때 · DB 저장이 실패했을 때)
    public void delete(String storedName) {
        if (storedName == null || storedName.isBlank()) {
            return;
        }
        Path target = root.resolve(storedName).normalize();
        if (!target.startsWith(root)) { // ../ 로 다른 폴더 파일을 지우지 못하게
            return;
        }
        try {
            Files.deleteIfExists(target);
        } catch (IOException e) {
            // 파일 삭제 실패는 글 삭제를 막지 않는다 (쓰레기 파일만 남는다)
        }
    }

    // 파일 첫 바이트(매직 넘버)로 이미지 종류 확인 : 이름 · Content-Type 은 바꿀 수 있지만 내용은 못 속인다
    private boolean isImage(byte[] b) {
        if (b.length >= 3 && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8 && (b[2] & 0xFF) == 0xFF) {
            return true; // JPG : FF D8 FF
        }
        if (b.length >= 4 && (b[0] & 0xFF) == 0x89 && b[1] == 'P' && b[2] == 'N' && b[3] == 'G') {
            return true; // PNG : 89 50 4E 47
        }
        if (b.length >= 4 && b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8') {
            return true; // GIF : 47 49 46 38
        }
        return b.length >= 12 && b[0] == 'R' && b[1] == 'I' && b[2] == 'F' && b[3] == 'F'
                && b[8] == 'W' && b[9] == 'E' && b[10] == 'B' && b[11] == 'P'; // WEBP : RIFF....WEBP
    }
}
