package com.example.destinationhashgenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Random;

public class DestinationHashGeneratorApplication {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar DestinationHashGenerator.jar <RollNumber> <JsonFilePath>");
            System.exit(1);
        }

        String rollNumber = args[0].toLowerCase().replace(" ", "");
        String jsonFilePath = args[1];

        try {
            HashGeneratorService service = new HashGeneratorService();
            String result = service.generateHash(rollNumber, jsonFilePath);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
