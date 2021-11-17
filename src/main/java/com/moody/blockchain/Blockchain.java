package com.moody.blockchain;

import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

public class Blockchain implements Serializable {

    private static final String CHAIN_FILE 		= 	"master/chain.bin";

    private static final LinkedList<Block> DB 	= 	new LinkedList<>();

    private static final String LEDGER_FILE		=	"ledger.txt";

    public static void nextBlock( Block newBlock ) {
        DB.add(newBlock);
        persist();
    }

    private static void persist() {
        try(
                FileOutputStream fos = new FileOutputStream( CHAIN_FILE );
                ObjectOutputStream out = new ObjectOutputStream( fos );
        ) {
            out.writeObject( DB );
            System.out.println( ">>> Master file updated!" );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Block> get(){
        try(
                FileInputStream fis = new FileInputStream( CHAIN_FILE );
                ObjectInputStream in = new ObjectInputStream( fis );
        ) {
            return (LinkedList<Block>) in.readObject();
        } catch (IOException | ClassNotFoundException e  ) {
            e.printStackTrace();
            return null;
        }
    }

    public static void distribute() {
        String chain = new GsonBuilder().setPrettyPrinting().create().toJson( DB );
        try {
            Files.write(
                    Paths.get( LEDGER_FILE ),
                    chain.getBytes(),
                    StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}