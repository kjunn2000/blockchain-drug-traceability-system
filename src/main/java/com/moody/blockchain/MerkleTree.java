package com.moody.blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MerkleTree {

    private List<TransactionRecord> tranxLst;
    private String root = "0";

    public String getRoot() {
        return root;
    }

    private MerkleTree(List<TransactionRecord> tranxLst) {
        super();
        this.tranxLst = tranxLst;
    }

    private static MerkleTree instance;
    public static MerkleTree getInstance( List<TransactionRecord> tranxLst ) {
        if( instance == null ) {
            return new MerkleTree(tranxLst);
        }
        return instance;
    }

    public void build() {
        List<String> tempLst = this.tranxLst.stream()
                .map(TransactionRecord::toString)
                .collect(Collectors.toList());

        List<String> hashes = genTranxHashLst( tempLst );
        while(  hashes.size() != 1 ) {
            hashes = genTranxHashLst( hashes );
        }
        this.root = hashes.get(0);
    }


    private List<String> genTranxHashLst(List<String> tranxLst) {
        List<String> hashLst = new ArrayList<>();
        int i = 0;
        while( i < tranxLst.size() ) {

            String left = tranxLst.get(i);
            i++;

            String right = "";
            if( i != tranxLst.size() ) right = tranxLst.get(i);

            String hash = Hasher.hash(left.concat(right) , "SHA-256");
            hashLst.add(hash);
            i++;
        }
        return hashLst;
    }
}