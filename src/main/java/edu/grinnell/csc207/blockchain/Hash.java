package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] hashData;

    /**
     * Hash constructor
     * @param data the hash data
     */
    public Hash(byte[] data) {
        this.hashData = data;
    }

    /**
     * Get the hash data
     * @return the hash data
     */
    public byte[] getData() {
        return hashData;
    }

    /**
     * Check if the hash is valid
     * @return true if the hash is valid, false otherwise
     */
    public boolean isValid() {
        return hashData[0] == 0 && hashData[1] == 0 && hashData[2] == 0;
    }

    /**
     * Convert the hash to a string representation
     * @return the string representation of the hash
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashData.length; i++) {
            sb.append(String.format("%02x", Byte.toUnsignedInt(hashData[i])));
        }
        return sb.toString();
    }

    /**
     * Check if two hashes are equal
     * @param other the other hash to compare with
     * @return true if the hashes are equal, false otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            return Arrays.equals(hashData, o.getData());
        }
        return false;
    }
}
