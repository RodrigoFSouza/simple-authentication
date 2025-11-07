package br.com.cronos.simple_security.util;

public class CnpjValidator {

    private CnpjValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Validates a CNPJ (Brazilian company tax ID).
     * CNPJ format: XX.XXX.XXX/XXXX-XX or XXXXXXXXXXXXXX (14 digits)
     *
     * @param cnpj the CNPJ to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return false;
        }

        // Remove all non-digit characters
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // CNPJ must have exactly 14 digits
        if (cnpj.length() != 14) {
            return false;
        }

        // Check if all digits are the same (invalid CNPJs)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calculate first verification digit
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
        }
        int digit1 = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        // Check first verification digit
        if (Character.getNumericValue(cnpj.charAt(12)) != digit1) {
            return false;
        }

        // Calculate second verification digit
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
        }
        int digit2 = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        // Check second verification digit
        return Character.getNumericValue(cnpj.charAt(13)) == digit2;
    }

    /**
     * Formats a CNPJ string to the standard format: XX.XXX.XXX/XXXX-XX
     *
     * @param cnpj the CNPJ to format (digits only)
     * @return formatted CNPJ
     */
    public static String format(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return cnpj;
        }

        // Remove all non-digit characters
        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return cnpj;
        }

        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, 2),
                cnpj.substring(2, 5),
                cnpj.substring(5, 8),
                cnpj.substring(8, 12),
                cnpj.substring(12, 14));
    }

    /**
     * Removes formatting from a CNPJ, leaving only digits
     *
     * @param cnpj the CNPJ to clean
     * @return CNPJ with only digits
     */
    public static String clean(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return cnpj;
        }
        return cnpj.replaceAll("[^0-9]", "");
    }
}
