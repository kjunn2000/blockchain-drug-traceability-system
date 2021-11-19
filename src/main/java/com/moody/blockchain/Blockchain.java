package com.moody.blockchain;

import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Blockchain implements Serializable {

    private static final String CHAIN_FILE 		= 	"master/chain.bin";

    private static final String LEDGER_FILE		=	"ledger.txt";

    public static final LinkedList<Block> DB 	= 	new LinkedList<>();

    public static void configure() {
        Block genesis = new Block( "0", null);
        Blockchain.nextBlock(genesis);
        distribute();
    }

    public static void loadMasterFile(){
        DB.addAll(get());
    }

    public static void nextBlock( Block newBlock ) {
        if (DB != null) {
            DB.add(newBlock);
        }
        persist();
    }

    public static void persist() {
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

    public static void mining(int index, String prevHash) {
        for (Block block : DB) {
            if(index > 0) {
                index--;
                continue;
            }
            block.getHeader().setPreviousHash(prevHash);
            block.generateHashValue();
            prevHash = block.getHeader().getCurrentHash();
        }
    }

    public static Optional<Block> findBlock(String drugId){
        return DB.stream()
                .filter(block -> Objects.nonNull(block.getDrug()) && block.getDrug().getDrugId().toString().equals(drugId))
                .findFirst();
    }
}