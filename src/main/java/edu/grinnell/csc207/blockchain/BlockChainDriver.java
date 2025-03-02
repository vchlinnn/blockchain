package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     * 
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException
     * @throws NumberFormatException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (args.length != 1) {
            System.out.println("Usage: java BlockChainDriver <initial_amount>");
            return;
        }

        BlockChain blockChain = new BlockChain(Integer.parseInt(args[0]));
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(blockChain.toString());
            System.out.print("Command? ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "mine":
                    System.out.print("Amount transferred? ");
                    int amount = scanner.nextInt();
                    Block block = blockChain.mine(amount);
                    System.out.println("amount = " + amount + ", nonce = " + block.getNonce());
                    break;

                case "append":
                    System.out.print("Amount transferred? ");
                    int amountAppend = scanner.nextInt();
                    System.out.print("Nonce? ");
                    long nonce = scanner.nextLong();
                    Block blockAppend = new Block(blockChain.getSize(), amountAppend, blockChain.getHash(), nonce);
                    blockChain.append(blockAppend);
                    break;

                case "remove":
                    blockChain.removeLast();
                    break;

                case "check":
                    if (blockChain.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    }
                    break;

                case "report":
                    blockChain.printBalances();
                    break;

                case "help":
                    System.out.println("Valid commands:");
                    System.out.println("    mine: discovers the nonce for a given transaction");
                    System.out.println("    append: appends a new block onto the end of the chain");
                    System.out.println("    remove: removes the last block from the end of the chain");
                    System.out.println("    check: checks that the block chain is valid");
                    System.out.println("    report: reports the balances of Alice and Bob");
                    System.out.println("    help: prints this list of commands");
                    System.out.println("    quit: quits the program");
                    break;

                case "quit":
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
