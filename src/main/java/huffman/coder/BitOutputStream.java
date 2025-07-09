package huffman.coder;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {
    private final OutputStream out;
    private int currentByte = 0;
    private int bitIndex = 0;

    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    public void writeBits(String code) throws IOException {
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '1') {
                currentByte |= (1 << 7 - bitIndex);
            }

            bitIndex++;

            if (bitIndex == 8) {
                flushCurrentByte();
            }
        }
    }

    public void writePaddingBits(int paddingBitCount) throws IOException {
        bitIndex +=  paddingBitCount;
        currentByte |= (1 << 7 - bitIndex);
        bitIndex++;

        if (bitIndex == 8) {
            flushCurrentByte();
        }
    }

    public void flush() throws IOException {
        if (bitIndex > 0) {
            out.write(currentByte);
        }
        out.flush();
    }

    public void flushCurrentByte() throws IOException {
        out.write(currentByte);
        currentByte = 0;
        bitIndex = 0;
    }
}
