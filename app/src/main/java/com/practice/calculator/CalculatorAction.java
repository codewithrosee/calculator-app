package com.practice.calculator;

public enum CalculatorAction {


    ADD(R.string.add),
    SUBTRACT(R.string.subtract),
    DIVIDE(R.string.divide),
    MULTIPLY(R.string.multiply);
    private final int symbol;

    CalculatorAction(int symbol) {
        this.symbol = symbol;
    }

    public int getSymbol() {
        return symbol;
    }


}
