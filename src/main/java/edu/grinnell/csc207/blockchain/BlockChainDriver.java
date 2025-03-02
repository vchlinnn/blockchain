package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    private static Block lastBlock;

    public BlockChainDriver(int num) throws NoSuchAlgorithmException {
        BlockChainDriver.lastBlock = new Block(0, num, null);
    }

    /**
     * The main entry point for the program.
     * 
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException
     * @throws NumberFormatException
     */
    public static void main(String[] args) throws NumberFormatException, NoSuchAlgorithmException {
        BlockChainDriver blockChain = new BlockChainDriver(Integer.parseInt(args[0]));
        lastBlock.toString();

    }
}
