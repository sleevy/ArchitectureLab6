package crhodes.fetch;

public class NotGate extends Gate {

    private boolean A;
    private boolean output = true;

    public void setInput(boolean sA) {
        A = sA;
    }

    public boolean getA() {
        return A;
    }

    @Override
    public void print() {
        System.out.println("NOT: [A: " + A + "] --> " + output);
    }

    @Override
    public void execute() {
        output = !A;
    }

    @Override
    public boolean getOutput() {
        return output;
    }

    public boolean equals(NotGate other) {
        return (A == other.getA()) && (output == other.getOutput());
    }

    public void makeEqual(NotGate other) {
        A = other.getA();
        output = other.getOutput();
    }
}
