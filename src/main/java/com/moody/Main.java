package com.moody;

import com.moody.blockchain.Blockchain;
import com.moody.gui.SupplierForm;

public class Main {


    public static void main(String[] args) throws Exception {
//        Blockchain.configure();
        Blockchain.loadMasterFile();

        SupplierForm form = new SupplierForm("Drug Traceability System");
//        ManufacturerForm form = new ManufacturerForm("Drug Traceability System");
        form.setVisible(true);
    }
}
