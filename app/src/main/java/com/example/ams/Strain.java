package com.example.ams;

public class Strain {

    String StrainType;
    String ThcEditText;
    String PriceEditText;
    String SupplyAvailability;

    public Strain() {
    }

    public Strain(String priceEditText, String strainType,  String supplyAvailability,  String thcEditText) {
        PriceEditText = priceEditText;
        StrainType = strainType;
        SupplyAvailability = supplyAvailability;
        ThcEditText = thcEditText;
    }


    public String getStrainType() {
        return StrainType;
    }

    public void setStrainType(String strainType) {
        StrainType = strainType;
    }

    public String getThcEditText() {
        return ThcEditText;
    }

    public void setThcEditText(String thcEditText) {
        ThcEditText = thcEditText;
    }

    public String getPriceEditText() {
        return PriceEditText;
    }

    public void setPriceEditText(String priceEditText) {
        PriceEditText = priceEditText;
    }

    public String getSupplyAvailability() {
        return SupplyAvailability;
    }

    public void setSupplyAvailability(String supplyAvailability) {
        SupplyAvailability = supplyAvailability;
    }
}
