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

    private static class Node {
        Block block;
        Node next;

        Node(Block blockValue) {
            this.block = blockValue;
            this.next = null;
        }
    }

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, initial, null); // prevHash should be ignored
        this.first = new Node(firstBlock);
        this.last = this.first;
        this.size = 1;
        this.balance = initial;
    }

    public Block mine(int amount) throws NoSuchAlgorithmException {
        return new Block(size, amount, last.block.getHash());
    }

    public int getSize() {
        return size;
    }

    public void append(Block blk) {
        if (blk.getPrevHash().equals(this.getHash()) && blk.getAmount() + balance >= 0) {
            Node newNode = new Node(blk);
            last.next = newNode;
            last = newNode;
            size += 1;
            balance += blk.getAmount();
        } else {
            throw new IllegalArgumentException("Previous hash does not match the last block's hash.");
        }
    }

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

    public Hash getHash() {
        return last.block.getHash();
    }

    public boolean isValidBlockChain() {
        Node cur = first.next;
        while (cur.next != null) {
            if (cur.next.block.getPrevHash().equals(cur.block.getHash()) == false) {
                return false;
            }
        }
        return true;
    }

    public void printBalances() {
        int aliceBalance = balance;
        int bobBalance = first.block.getAmount() - aliceBalance;
        System.out.println("Alice: " + aliceBalance + ", Bob: " + bobBalance);
    }

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
