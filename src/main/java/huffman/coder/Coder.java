package huffman.coder;

import huffman.model.TreeNode;
import huffman.model.FrequencyTable;
import huffman.model.HuffmanTree;
import huffman.util.FileValidator;

import java.io.*;
import java.util.*;

public class Coder {
    private Map<Byte, Integer> frequencies;
    private final Map<Byte, String> codes = new HashMap<>();

    public void encode(String inputPath, String outputPath) throws IOException {
        FileValidator.validationCheck(inputPath, outputPath);

        frequencies = FrequencyTable.buildFrequencyTable(inputPath);

        try (InputStream textReader = new BufferedInputStream(new FileInputStream(inputPath));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(outputPath))) {

            TreeNode root = HuffmanTree.buildHuffmanTree(frequencies);
            generateCodes(root);

            writeFrequencyTable(out);
            encode_symbols(textReader, out);

        }
    }

    private void generateCodes(TreeNode root) {
        if (frequencies.size() == 1) {
            codes.put(root.content, "0");
        } else {
            createCodes("", root);
        }
    }


    private void createCodes(String code, TreeNode root) {
        if (root.content != null) {
            codes.put(root.content, code);
            return;
        }

        if (root.left != null) {
            createCodes(code + '0', root.left);
        }

        if (root.right != null) {
            createCodes(code + '1', root.right);
        }
    }


    public void writeFrequencyTable(OutputStream out) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(out);
        dataOut.writeInt(frequencies.size());

        for (Map.Entry<Byte, Integer> node : frequencies.entrySet()) {
            byte symbol = node.getKey();
            int count = node.getValue();

            dataOut.writeByte(symbol);
            dataOut.writeInt(count);
        }
    }

    public int BitRemain() {
        int count = 0;

        for (Map.Entry<Byte, Integer> entry : frequencies.entrySet()) {
            byte content = entry.getKey();
            int freq = entry.getValue();
            int lenCode = codes.get(content).length();

            count += (freq * lenCode) % 8;
            count = count % 8;
        }

        return count;
    }


    public void encode_symbols(InputStream in, OutputStream out) throws IOException {
        BitOutputStream bitOut = new BitOutputStream(out);

        int sym;
        int paddingBits = (8 - (BitRemain() + 1)) % 8;
        bitOut.writePaddingBits(paddingBits);

        while((sym = in.read()) != -1) {
            String code = codes.get((byte) sym);
            if (code == null) {
                throw new IllegalStateException("Не найден код для байта: " + (byte) sym);
            }

            bitOut.writeBits(code);
        }
        bitOut.flush();
    }

}
