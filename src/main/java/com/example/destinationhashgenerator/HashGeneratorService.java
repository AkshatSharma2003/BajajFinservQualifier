package com.example.destinationhashgenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Random;

public class HashGeneratorService {

    public String generateHash(String rollNumber, String jsonFilePath) throws Exception {
        // Parse JSON file
        String destinationValue = parseJsonFile(jsonFilePath);
        if (destinationValue == null) {
            throw new Exception("Key 'destination' not found in JSON.");
        }

        // Generate random string
        String randomString = generateRandomString(8);

        // Concatenate and hash
        String concatenatedString = rollNumber + destinationValue + randomString;
        String md5Hash = generateMD5Hash(concatenatedString);

        // Format output
        return md5Hash + ";" + randomString;
    }

    private String parseJsonFile(String filePath) throws Exception {
        FileReader reader = new FileReader(filePath);
        JsonElement jsonElement = JsonParser.parseReader(reader);
        return findDestinationKey(jsonElement);
    }

    private String findDestinationKey(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            for (String key : jsonObject.keySet()) {
                if (key.equals("destination")) {
                    return jsonObject.get(key).getAsString();
                }
                String result = findDestinationKey(jsonObject.get(key));
                if (result != null) return result;
            }
        } else if (element.isJsonArray()) {
            for (JsonElement arrayElement : element.getAsJsonArray()) {
                String result = findDestinationKey(arrayElement);
                if (result != null) return result;
            }
        }
        return null;
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String generateMD5Hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
