package huffman.decoder;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream {
    private final InputStream in;
    private int currentByte = 0;
    private int bitIndex = 0;

    public BitInputStream(InputStream in) {
        this.in = in;
    }

    public void skipPaddingBits() throws IOException {
        currentByte = in.read();
        if (currentByte == -1) {
            throw new EOFException("Файл закончился до начала данных");
        }

        while ((currentByte & (1 << 7 - bitIndex)) == 0) {
            bitIndex++;
        }

        bitIndex++;
        if (bitIndex == 8) {
            bitIndex = 0;
            currentByte = in.read();
        }
    }

    public int readBit() throws IOException {
        if (currentByte == -1) {
            return -1;
        }

        int bit;

        if ((currentByte & (1 << 7 - bitIndex)) != 0) {
            bit = 1;
        } else {
            bit = 0;
        }

        bitIndex++;

        if (bitIndex == 8) {
            bitIndex = 0;
            currentByte = in.read();
        }

        return bit;
    }


}
