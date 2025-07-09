package huffman.decoder;

import huffman.model.TreeNode;
import huffman.util.FileValidator;
import huffman.model.HuffmanTree;

import java.io.*;
import java.util.*;

public class Decoder {
    private Map<Byte, Integer> frequencies;

    public void decode(String inputPath, String outputPath) throws IOException {
        FileValidator.validationCheck(inputPath, outputPath);


        try(InputStream in = new BufferedInputStream(new FileInputStream(inputPath));
        OutputStream textWriter = new BufferedOutputStream(new FileOutputStream(outputPath))) {

            frequencies = ReadFrequencyTable(in);

            TreeNode root = HuffmanTree.buildHuffmanTree(frequencies);

            decodeText(root, in, textWriter);
        }
    }

    public Map<Byte, Integer> ReadFrequencyTable(InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);

        int size = dataIn.readInt();
        if (size <= 0 || size > 256) {
            throw new IOException("Недопустимый размер таблицы частот: " + size);
        }

        Map<Byte, Integer> frequencies = new HashMap<>();

        for (int i = 0; i < size; i++) {
            byte b;
            int count;

            try {
                b = dataIn.readByte();
                count = dataIn.readInt();
            } catch (IOException e) {
                throw new IOException("Неожиданный конец файла при чтении таблицы частот");
            }

            if (count <= 0) {
                throw new IOException("Недопустимое значение частоты: " + count);
            }

            frequencies.put(b, count);
        }

        return frequencies;
    }



    public void decodeText(TreeNode root, InputStream in, OutputStream out) throws IOException {
        BitInputStream bitIn = new BitInputStream(in);
        bitIn.skipPaddingBits();

        TreeNode node = root;

        if (frequencies.size() == 1) {
            while (bitIn.readBit() != -1) {
                out.write(node.content);
            }
            return;
        }

        int bit;
        while ((bit = bitIn.readBit()) != -1) {
            if (bit == 1) {
                node = node.right;
            } else {
                node = node.left;
            }

            if (node == null) {
                throw new IOException("Ошибка декодирования: некорректный путь в дереве");
            }

            if (node.content != null) {
                out.write(node.content);
                node = root;
            }

        }
    }
}
