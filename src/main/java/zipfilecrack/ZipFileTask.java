package zipfilecrack;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import java.io.File;

public class ZipFileTask {
    public static boolean tryPassword(String password, String zipFilePath) {
        try {
            // Kiểm tra file tồn tại trước khi xử lý
            File file = new File(zipFilePath);
            if (!file.exists()) {
                System.err.println("File không tồn tại: " + zipFilePath);
                return false;
            }

            // Tạo đối tượng ZipFile
            ZipFile zipFile = new ZipFile(zipFilePath);

            // Kiểm tra nếu tệp ZIP được mã hóa
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());

                // Tạo thư mục đầu ra nếu chưa tồn tại
                File outputDir = new File("extracted");
                if (!outputDir.exists()) {
                    if (!outputDir.mkdir()) {
                        System.err.println("Không thể tạo thư mục đầu ra.");
                        return false;
                    }
                }

                // Giải nén tất cả tệp vào thư mục đầu ra
                zipFile.extractAll(outputDir.getAbsolutePath());
                System.out.println("Giải nén thành công với mật khẩu: " + password);
                return true; // Mật khẩu đúng
            } else {
                System.out.println("Tệp không được mã hóa hoặc mật khẩu sai.");
                return false;
            }

        } catch (ZipException e) {
            System.err.println("Lỗi khi thử mật khẩu '" + password + "': " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi bất ngờ: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi để debug
            return false;
        }
    }
}