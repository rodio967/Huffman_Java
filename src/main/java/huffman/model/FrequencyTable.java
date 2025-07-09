package huffman.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FrequencyTable {
    public static Map<Byte, Integer> buildFrequencyTable(String inputPath) throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(inputPath))) {
            Map<Byte, Integer> freqMap = new HashMap<>();

            int sym;
            while ((sym = in.read()) != -1) {
                Byte b = (byte) sym;
                freqMap.put(b, freqMap.getOrDefault(b, 0) + 1);
            }

            if (freqMap.isEmpty()) {
                throw new IllegalStateException("Не удалось подсчитать частоты символов. Файл может быть повреждён.");
            }

            return freqMap;
        }
    }
}
