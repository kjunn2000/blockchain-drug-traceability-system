package com.moody.blockchain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

public class Drug implements Serializable {
    private UUID drugId;
    private String name;
    private BigInteger quantity;
    private String unitPrice;
    private String description;
    private DrugStatus status;

    public Drug(String name, BigInteger quantity, String unitPrice, String description, DrugStatus status) {
        this.drugId = UUID.randomUUID();
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.status = status;
    }

    public UUID getDrugId() {
        return drugId;
    }

    public void setDrugId(UUID drugId) {
        this.drugId = drugId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DrugStatus getStatus() {
        return status;
    }

    public void setStatus(DrugStatus status) {
        this.status = status;
    }
}
