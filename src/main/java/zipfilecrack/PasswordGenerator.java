package zipfilecrack;

public class PasswordGenerator {
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MAX_LENGTH = 4;

    public static void generatePasswords(String current, String zipFilePath, PasswordCracker cracker) {
        if (current.length() == MAX_LENGTH) {
            System.out.println("Thử mật khẩu: " + current); // In ra mật khẩu đầy đủ
            cracker.addPasswordTask(current, zipFilePath);
            return;
        }

        for (char c : CHARSET.toCharArray()) {
            generatePasswords(current + c, zipFilePath, cracker);
        }
    }
}