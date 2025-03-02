package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {
    private int blockNum;
    private int blockData;
    private Hash prevBlockHash;
    private long blockNonce;
    private Hash blockHash;

    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.blockNum = num;
        this.blockData = amount;
        this.prevBlockHash = prevHash;
        this.blockNonce = calculateNonce(num, amount, prevHash);
        this.blockHash = new Hash(calculateHash(num, amount, prevHash, blockNonce));
        /*
         * Mining:
         * Loop through all the possible nonce values.
         * For each candidate nonce, compute the hash of the block with the nonce.
         * If the hash is valid, then complete the block with that data. Otherwise, keep
         * iterating.
         */
    }

    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.blockNum = num;
        this.blockData = amount;
        this.prevBlockHash = prevHash;
        this.blockNonce = nonce;
        this.blockHash = new Hash(calculateHash(num, amount, prevHash, nonce));
        // Create hash
    }

    public int getNum() {
        return blockNum;
    }

    public int getAmount() {
        return blockData;
    }

    public long getNonce() {
        return blockNonce;
    }

    public Hash getPrevHash() {
        return prevBlockHash;
    }

    public Hash getHash() {
        return blockHash;
    }

    public String toString() {
        return "Block" + blockNum + " (Amount: " + blockData + ", Nonce: " + blockNonce + ", prevHash: " + prevBlockHash
                + ", hash:" + blockHash + ")";
    }

    private long calculateNonce(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        long curNonce = 0;
        while (new Hash(calculateHash(num, amount, prevHash, curNonce)).isValid() == false) {
            curNonce += 1;
        }
        return curNonce;
    }

    private byte[] calculateHash(int num, int amount, Hash prevHash, long curNonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(ByteBuffer.allocate(4).putLong(curNonce).array()); // take a value of type byte[]
        byte[] hash = md.digest();
        return hash;
    }

}
