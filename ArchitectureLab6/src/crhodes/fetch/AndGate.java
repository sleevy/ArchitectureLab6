package crhodes.fetch;

public class AndGate extends Gate {

    private boolean A;
    private boolean B;
    private boolean output = false;

    public boolean getA() {
        return A;
    }

    public boolean getB() {
        return B;
    }

    public void setInputs(boolean sA, boolean sB) {
        A = sA;
        B = sB;
    }

    @Override
    public void print() {
        System.out.println("AND: [A: " + A + ",B: " + B + "] --> " + output);
    }

    @Override
    public void execute() {
        output = A & B;
    }

    public boolean equals(AndGate other) {
        return (A == other.getA()) && (B == other.getB()) && (output == other.getOutput());
    }

    @Override
    public boolean getOutput() {
        return output;
    }


    public void makeEqual(AndGate other) {
        A = other.getA();
        B = other.getB();
        output = other.getOutput();
    }

}
