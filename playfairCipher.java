import java.util.*;

/**
 * A class that implements the Playfair cipher for encryption and decryption of text.
 */
public class playfairCipher {
    /**
     * Main method to demonstrate the Playfair cipher encryption and decryption.
     * @param args Command-line arguments (not used in this context).
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter secret key:");
        String secretKey = in.nextLine();
        System.out.println("Enter plain text (type 'exit' to finish input):");
        String plainText = in.useDelimiter("exit").next();
        char[][] keyGenerated = generateKey(secretKey.toUpperCase());
        String[] encryptedTextArr = encryption(plainText.toUpperCase(), keyGenerated);
        StringBuilder cipherTextBuilder = new StringBuilder();
        for (String piece : encryptedTextArr) {
            cipherTextBuilder.append(piece);
        }
        String cipherText = cipherTextBuilder.toString();
        String[] decryptedTextArr = decryption(cipherText, keyGenerated);
        System.out.println("Generated Key:");
        for (char[] chars : keyGenerated) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
        System.out.println("Encrypted Text:");
        for (String s : encryptedTextArr) {
            System.out.print(s);
        }
        System.out.println();
        System.out.println("Decrypted Text:");
        for (String s : decryptedTextArr) {
            System.out.print(s);
        }
    }

    /**
     * Generates the key matrix for the Playfair cipher based on the given secret key.
     * @param secretKey The secret key used for generating the key matrix.
     * @return The generated key matrix.
     */
    public static char[][] generateKey(String secretKey) {
        char[][] matrixKey = new char[5][5];
        String alphabetLetters = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        StringBuilder uniqueKey = new StringBuilder();
        Set<Character> usedChars = new HashSet<>();

        // Add unique characters from the secretKey to the uniqueKey builder
        for (char c : secretKey.toCharArray()) {
            if (!usedChars.contains(c) && alphabetLetters.contains(String.valueOf(c))) {
                usedChars.add(c);
                uniqueKey.append(c);
            }
        }

        // Add remaining characters to the uniqueKey builder
        for (char c : alphabetLetters.toCharArray()) {
            if (!usedChars.contains(c)) {
                uniqueKey.append(c);
            }
        }

        // Fill the matrixKey with characters from the uniqueKey
        int index = 0;
        for (int i = 0; i < matrixKey.length; i++) {
            for (int j = 0; j < matrixKey[i].length; j++) {
                matrixKey[i][j] = uniqueKey.charAt(index++);
            }
        }

        return matrixKey;
    }
    /**
     * Encrypts the given plain text using the Playfair cipher algorithm.
     * @param plainText The plain text to be encrypted.
     * @param keyGenerated The key matrix generated using the secret key.
     * @return An array of encrypted text pieces.
     */
    public static String[] encryption(String plainText, char[][] keyGenerated) {
        // Sanitize the input text by removing spaces, punctuation, and replacing 'J' with 'I'
        String sanitized = plainText.replace(" ", "");
        sanitized = sanitized.replace(".", "");
        sanitized = sanitized.replace(",", "");
        sanitized = sanitized.replace("\n", "");
        sanitized = sanitized.replace("J", "I");
        int pairsNum = (sanitized.length() / 2) + (sanitized.length() % 2);

        int count = 0;
        String[] pairsArr = new String[pairsNum];
        for (int i = 0; i < sanitized.length(); i += 2) {
            if (i + 1 < sanitized.length()) {
                if (sanitized.charAt(i) == sanitized.charAt(i + 1)) {
                    // Ensure not to exceed the pairsArr length
                    if (count < pairsArr.length) {
                        pairsArr[count++] = sanitized.charAt(i) + "X";
                    }
                } else {
                    pairsArr[count++] = sanitized.substring(i, i + 2);
                }
            } else {
                if (count < pairsArr.length) {
                    pairsArr[count] = sanitized.charAt(i) + "Z";
                }
            }
        }
        for (int i = 0; i < pairsArr.length; i++) {
            char firstCharPair = pairsArr[i].charAt(0);
            char secondCharPair = pairsArr[i].charAt(1);
            int[] checkFirstCharPair = {-1, -1};
            int[] checkSecondCharPair = {-1, -1};
            for (int row = 0; row < keyGenerated.length; row++) {
                for (int col = 0; col < keyGenerated[row].length; col++) {
                    if (keyGenerated[row][col] == firstCharPair) {
                        checkFirstCharPair = new int[]{row, col};
                    }
                    if (keyGenerated[row][col] == secondCharPair) {
                        checkSecondCharPair = new int[]{row, col};
                    }
                }
            }
            // Check if the characters were found in the key matrix
            if (checkFirstCharPair[1] == checkSecondCharPair[1]) {
                firstCharPair = keyGenerated[(checkFirstCharPair[0] + 1) % 5][checkFirstCharPair[1]];
                secondCharPair = keyGenerated[(checkSecondCharPair[0] + 1) % 5][checkSecondCharPair[1]];
            } else if (checkFirstCharPair[0] == checkSecondCharPair[0]) {
                firstCharPair = keyGenerated[checkFirstCharPair[0]][(checkFirstCharPair[1] + 1) % 5];
                secondCharPair = keyGenerated[checkSecondCharPair[0]][(checkSecondCharPair[1] + 1) % 5];
            } else {
                firstCharPair = keyGenerated[checkFirstCharPair[0]][checkSecondCharPair[1]];
                secondCharPair = keyGenerated[checkSecondCharPair[0]][checkFirstCharPair[1]];
            }
            pairsArr[i] = "" + firstCharPair + secondCharPair;
        }
        return pairsArr;
    }
    /**
     * Decrypts the given cipher text using the Playfair cipher algorithm.
     * @param cipherText The cipher text to be decrypted.
     * @param keyGenerated The key matrix generated using the secret key.
     * @return An array of decrypted text pieces.
     */
    public static String[] decryption(String cipherText, char[][] keyGenerated) {
        String sanitized = cipherText.replace(" ", "");
        int pairsNum = sanitized.length() / 2;
        String[] pairsArr = new String[pairsNum];

        for (int i = 0; i < sanitized.length(); i += 2) {
            pairsArr[i / 2] = sanitized.substring(i, i + 2);
        }

        for (int i = 0; i < pairsArr.length; i++) {
            char firstCharPair = pairsArr[i].charAt(0);
            char secondCharPair = pairsArr[i].charAt(1);
            int[] checkFirstCharPair = {-1, -1};
            int[] checkSecondCharPair = {-1, -1};

            // Find positions in the key matrix
            for (int row = 0; row < keyGenerated.length; row++) {
                for (int col = 0; col < keyGenerated[row].length; col++) {
                    if (keyGenerated[row][col] == firstCharPair) {
                        checkFirstCharPair = new int[]{row, col};
                    }
                    if (keyGenerated[row][col] == secondCharPair) {
                        checkSecondCharPair = new int[]{row, col};
                    }
                }
            }

            // Apply the decryption rules of Playfair cipher
            if (checkFirstCharPair[1] == checkSecondCharPair[1]) {
                // Move up in the column
                firstCharPair = keyGenerated[(checkFirstCharPair[0] + 4) % 5][checkFirstCharPair[1]];
                secondCharPair = keyGenerated[(checkSecondCharPair[0] + 4) % 5][checkSecondCharPair[1]];
            } else if (checkFirstCharPair[0] == checkSecondCharPair[0]) {
                // Move left in the row
                firstCharPair = keyGenerated[checkFirstCharPair[0]][(checkFirstCharPair[1] + 4) % 5];
                secondCharPair = keyGenerated[checkSecondCharPair[0]][(checkSecondCharPair[1] + 4) % 5];
            } else {
                // Rectangle case: swap columns
                firstCharPair = keyGenerated[checkFirstCharPair[0]][checkSecondCharPair[1]];
                secondCharPair = keyGenerated[checkSecondCharPair[0]][checkFirstCharPair[1]];
            }

            pairsArr[i] = "" + firstCharPair + secondCharPair;
        }

        return pairsArr;
    }

}