package ru.otus.dataprocessor;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final Path filePath;

    public FileSerializer(String fileName) {
        this.filePath = Path.of(fileName);
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        String json = new Gson().toJson(data);
        try {
            Files.writeString(filePath, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
