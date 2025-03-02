package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] hashData;

    public Hash(byte[] data) {
        this.hashData = data;
    }

    public byte[] getData() {
        return hashData;
    }

    public boolean isValid() {
        return hashData[0] == 0 && hashData[1] == 0 && hashData[2] == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashData.length; i++) {
            sb.append(String.format("%02x", Byte.toUnsignedInt(hashData[i])));
        }
        return sb.toString();
    }

    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            return Arrays.equals(hashData, o.getData());
        }
        return false;
    }
}
