package com.moody.blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Transaction implements Serializable {

    private final int SIZE = 5;

    List<String> tranxLst;

    public Transaction() {
        tranxLst = new ArrayList<>( SIZE );
    }

    public void add( String tranx ){
        tranxLst.add(tranx);
    }



    @Override
    public String toString() {
        return "Transaction [tranxLst=" + tranxLst + "]";
    }

}