package zipfilecrack;

public class PasswordGenerator {
    public static final String CHARSET = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static String getCharset() {
        return CHARSET;
    }
    public static void generatePasswords(String current, String zipFilePath, PasswordCracker cracker, int maxLength) {
        if (current.length() == maxLength) {
            cracker.addPasswordTask(current, zipFilePath);
            return;
        }

        for (char c : CHARSET.toCharArray()) {
            if (cracker.isFound()) break;
            generatePasswords(current + c, zipFilePath, cracker, maxLength);
        }
    }
}
