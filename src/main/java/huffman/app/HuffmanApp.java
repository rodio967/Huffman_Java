package huffman.app;

import huffman.coder.Coder;
import huffman.decoder.Decoder;

import java.io.*;

public class HuffmanApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            guide();
            return;
        }

        String mode = args[0];
        String inputPath = args[1];
        String outputPath = args[2];

        switch (mode) {
            case "c":
                long start_Coder = System.nanoTime();
                try {
                    Coder coder = new Coder();
                    coder.encode(inputPath, outputPath);

                    long end_Coder = System.nanoTime();
                    System.out.printf("Сжатие завершено за %.3f секунд%n", (end_Coder - start_Coder) / 1_000_000_000.0);
                } catch (IOException | IllegalStateException | IllegalArgumentException e) {
                    System.err.println("Ошибка при кодировании: " + e.getMessage());
                }

                break;

            case "d":
                long start_Decoder = System.nanoTime();
                try {
                    Decoder decoder = new Decoder();
                    decoder.decode(inputPath, outputPath);

                    long end_Decoder = System.nanoTime();
                    System.out.printf("Рассжатие завершено за %.3f секунд%n", (end_Decoder - start_Decoder) / 1_000_000_000.0);
                } catch (IOException | IllegalArgumentException e) {
                    System.err.println("Ошибка при декодировании: " + e.getMessage());
                }
                break;

            default:
                guide();
        }
    }

    private static void guide() {
        System.out.println("Неверный ввод. Использование:");
        System.out.println("Для сжатия файла:");
        System.out.println("java -jar huffman.jar c <входной_файл> <выходной_файл>");
        System.out.println("Для распаковки файла:");
        System.out.println("java -jar huffman.jar d <входной_файл> <выходной_файл>");
        System.out.println();
        System.out.println("Пример:");
        System.out.println("java -jar huffman.jar c input.txt compressed.huff");
        System.out.println("java -jar huffman.jar d compressed.huff output.txt");
    }

}
