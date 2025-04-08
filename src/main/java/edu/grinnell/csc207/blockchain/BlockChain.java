package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {

    private Node first;
    private Node last;
    private int size;
    private int balance;

    /**
     * A node in the blockchain
     */
    private static class Node {
        Block block;
        Node next;

        /**
         * Node constructor
         * @param blockValue the block to store in this node
         */
        Node(Block blockValue) {
            this.block = blockValue;
            this.next = null;
        }
    }

    /**
     * Blockchain constructore with inital balance
     * @param initial initial balance
     * @throws NoSuchAlgorithmException
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, initial, null); // prevHash should be ignored
        this.first = new Node(firstBlock);
        this.last = this.first;
        this.size = 1;
        this.balance = initial;
    }

    /**
     * Mine a new block by finding a valid nonce
     * @param amount the transaction amount
     * @return the mined block
     * @throws NoSuchAlgorithmException
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        return new Block(size, amount, last.block.getHash());
    }

    /**
     * Returns the number of blocks
     * @return The size of the blockchain
     */
    public int getSize() {
        return size;
    }

    /**
     * Appends a new block to the end of the blockchain 
     * @param blk The block to append
     * @throws IllegalArgumentException
     */
    public void append(Block blk) {
        if (blk.getPrevHash().equals(this.getHash()) && blk.getAmount() + balance >= 0) {
            Node newNode = new Node(blk);
            last.next = newNode;
            last = newNode;
            size += 1;
            balance += blk.getAmount();
        } else {
            throw new IllegalArgumentException("Hash not match");
        }
    }

    /**
     * Removes the last block 
     * @return True if the last block was successfully removed
     */
    public boolean removeLast() {
        if (first == last) {
            return false;
        } else {
            Node prev = first;
            while (prev.next != last) {
                prev = prev.next;
            }
            balance -= last.block.getAmount();
            prev.next = null;
            last = prev;
            size -= 1;
            return true;
        }
    }

    /**
     * Returns the hash of the last block
     * @return The hash of the last block
     */
    public Hash getHash() {
        return last.block.getHash();
    }

    /**
     * Checks if the blockchain is valid
     * @return True if the blockchain is valid, false otherwise
     */
    public boolean isValidBlockChain() {
        Node cur = first;
        while (cur.next != null) {
            if (cur.next.block.getPrevHash().equals(cur.block.getHash()) == false) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    /**
     * Prints the balances of Alice and Bob
     *
     */
    public void printBalances() {
        int aliceBalance = balance;
        int bobBalance = first.block.getAmount() - aliceBalance;
        System.out.println("Alice: " + aliceBalance + ", Bob: " + bobBalance + "\n");
    }

    /**
     * Returns a string representation of the blockchain
     * @return A string representation of the blockchain
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node cur = first;
        while (cur != null) {
            sb.append(cur.block.toString()).append("\n");
            cur = cur.next;
        }
        return sb.toString();
    }
}
