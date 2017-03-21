package crhodes.fetch;

public class XOrGate extends Gate {

    private boolean A;
    private boolean B;
    private boolean output;

    public void setInputs(boolean sA, boolean sB) {
        A = sA;
        B = sB;
    }

    public boolean getA() {
        return A;
    }

    public boolean getB() {
        return B;
    }

    @Override
    public void print() {
        System.out.println("XOR: [A: " + A + ",B: " + B + "] --> " + output);
    }

    @Override
    public void execute() {
        output = (A != B);
    }

    @Override
    public boolean getOutput() {
        return output;
    }

    public boolean equals(XOrGate other) {
        return (A == other.getA()) && (B == other.getB()) && (output == other.getOutput());
    }

    public void makeEqual(XOrGate other) {
        A = other.getA();
        B = other.getB();
        output = other.getOutput();
    }
}
