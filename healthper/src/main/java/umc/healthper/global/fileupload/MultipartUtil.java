package umc.healthper.global.fileupload;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class MultipartUtil {

    private static final String BASE_DIR = "images";

    /**
     * Return Local User Home Directory Path
     */
    public static String getLocalHomeDirectory() {
        return System.getProperty("user.home");
    }

    /**
     * 새로운 파일의 교유 ID를 생성
     * @return 36자리의 UUID
     */
    public static String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Multipart의 ContentType 값에서 / 이후의 파일 확장자만 추출
     * @param contentType   ex. image/png
     * @return      ex. png
     */
    public static String getFormat(String contentType) {
        if (StringUtils.hasText(contentType)) {
            return contentType.substring(contentType.lastIndexOf('/') + 1);
        }

        return null;
    }

    /**
     * 파일의 전체 경로 생성
     * @param fileId - 생성된 파일 고유 ID
     * @param format - 확장자
     */
    public static String createFullPath(String fileId, String format) {
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }
}
