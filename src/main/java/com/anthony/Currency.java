package com.anthony;

public class Currency {
    private String name;
    private String symbol;

    public Currency(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }
    public String getName(){
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
    public String format(double amount){
        return symbol + String.format("%.2f", amount);
    }


}
