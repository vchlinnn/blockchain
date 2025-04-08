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

    /**
     * New block constructor with no nonce provided
     * @param num The block number
     * @param amount Transaction amount
     * @param prevHash hash of previous block
     * @throws NoSuchAlgorithmException if the sha-256 algo is not available
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.blockNum = num;
        this.blockData = amount;
        this.prevBlockHash = prevHash;
        this.blockNonce = calculateNonce(num, amount, prevHash);
        this.blockHash = new Hash(calculateHash(num, amount, prevHash, blockNonce));
    }

    /**
     * New block constructor with nonce provided
     * @param num The block number
     * @param amount Transaction amount
     * @param prevHash hash of previous block
     * @param nonce provided nonce
     * @throws NoSuchAlgorithmException if the sha-256 algo is not available
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.blockNum = num;
        this.blockData = amount;
        this.prevBlockHash = prevHash;
        this.blockNonce = nonce;
        this.blockHash = new Hash(calculateHash(num, amount, prevHash, nonce));
        // Create hash
    }

    /**
     * Return the block number
     * @return block number
     */
    public int getNum() {
        return blockNum;
    }

    /**
     * Return transaction amount
     * @return transaction amount
     */
    public int getAmount() {
        return blockData;
    }

    /**
     * Return nonce value
     * @return none value
     */
    public long getNonce() {
        return blockNonce;
    }

    /**
     * Return hash of prev block
     * @return hash of prev block 
     */
    public Hash getPrevHash() {
        return prevBlockHash;
    }

    /**
     * Return hash of this block
     * @return hash of this block
     */
    public Hash getHash() {
        return blockHash;
    }

    /**
     * Return the content of the block as a string
     * @return A string representation of the block
     */
    public String toString() {
        return "Block " + blockNum + " (Amount: " + blockData 
                + ", Nonce: " + blockNonce + ", prevHash: "
                + prevBlockHash + ", hash:" + blockHash.toString() + ")";
    }

    /**
     * Mines a valid nonce for the block such that the hash of the block satisfies
     *
     * @param num the block number
     * @param amount the transaction amount
     * @param prevHash the hash of prev block
     * @return a valid nonce value
     * @throws NoSuchAlgorithmException
     */
    private long calculateNonce(int num, int amount, 
        Hash prevHash) throws NoSuchAlgorithmException {
        long curNonce = 0;
        while (true) {
            Hash hash = new Hash(calculateHash(num, amount, prevHash, curNonce));
            if (hash.isValid()) {
                return curNonce;
            }
            curNonce++;
        }
    }

    /**
     * Calculate the hash of the block using the algorithm
     * @param num the block number
     * @param amount the transaction amount
     * @param prevHash the hash of prev block
     * @param curNonce the nonce value
     * @return the calculated hash 
     * @throws NoSuchAlgorithmException
     */
    private byte[] calculateHash(int num, int amount, 
        Hash prevHash, long curNonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(ByteBuffer.allocate(4).putInt(num).array());
        md.update(ByteBuffer.allocate(8).putLong(curNonce).array());
        md.update(ByteBuffer.allocate(4).putInt(amount).array());
        if (prevHash != null) {
            md.update(prevHash.getData());
        }
        byte[] hash = md.digest();
        return hash;
    }

}
