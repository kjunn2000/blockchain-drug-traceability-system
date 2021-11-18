package com.moody.blockchain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BusinessType {
    SUPPLIER, MANUFACTURER, DISTRIBUTOR, PHARMACY;

    public static List<String> getStrings(){
        return Arrays.stream(BusinessType.values()).map(Enum::toString).collect(Collectors.toList());
    }
}
