package huffman.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileValidator {
    public static void validationCheck(String inputPath, String outputPath) throws IOException {
        checkPath(inputPath, outputPath);
        checkInputFile(inputPath);
    }

    public static void checkInputFile(String inputPath) throws IOException {
        File inputFile = new File(inputPath);

        if (!inputFile.exists()) {
            throw new FileNotFoundException("Файл не существует: " + inputFile.getPath());
        }

        if (!inputFile.isFile()) {
            throw new IOException("Указан не обычный файл: " + inputFile.getPath());
        }

        if (!inputFile.canRead()) {
            throw new IOException("Нет прав на чтение файла: " + inputFile.getPath());
        }

        if (inputFile.length() == 0) {
            throw new IOException("Файл пустой: " + inputFile.getPath());
        }
    }

    public static void checkPath(String inputPath, String outputPath) {
        if (inputPath == null || inputPath.isBlank()) {
            throw new IllegalArgumentException("Путь к входному файлу не должен быть пустым.");
        }
        if (outputPath == null || outputPath.isBlank()) {
            throw new IllegalArgumentException("Путь к выходному файлу не должен быть пустым.");
        }
    }


}
