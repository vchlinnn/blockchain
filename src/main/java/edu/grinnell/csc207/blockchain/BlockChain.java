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
    }
}
