package crhodes.fetch;

public class OrGate extends Gate {

    private boolean A;
    private boolean B;
    private boolean output = false;


    public void setInputs(boolean sA, boolean sB) {
        A = sA;
        B = sB;
    }

    @Override
    public void print() {
        System.out.println("OR: [A: " + A + ",B: " + B + "] --> " + output);
    }

    @Override
    public void execute() {
        output = A || B;
    }

    @Override
    public boolean getOutput() {
        return output;
    }

    public boolean equals(OrGate other) {
        return (A == other.getA()) && (B == other.getB()) && (output == other.getOutput());
    }

    public void makeEqual(OrGate other) {
        A = other.getA();
        B = other.getB();
        output = other.getOutput();
    }

    public boolean getA() {
        return A;
    }

    public boolean getB() {
        return B;
    }
}
