package crhodes.fetch;

public class FullAdder {
    private HalfAdder h1, h2;
    private OrGate oGate;

    private boolean A;
    private boolean B;
    private boolean C;

    //output values
    private boolean sum;
    private boolean carry;

    public FullAdder() {
        h1 = new HalfAdder();
        h2 = new HalfAdder();
        oGate = new OrGate();
    }

    //getters for variables
    public boolean getA() {
        return A;
    }

    public boolean getB() {
        return B;
    }

    public boolean getC() {
        return C;
    }

    public boolean getSum() {
        return sum;
    }

    public boolean getCarry() {
        return carry;
    }

    public void setInputs(boolean sA, boolean sB, boolean sC) {
        A = sA;
        B = sB;
        C = sC;
    }

    public void print() {
        String out = "";
        out += "Full Adder: [A: " + A + ",B: " + B + ",C: " + C + "] --> Sum: " + sum + " Carry: " + carry;
        System.out.println(out);
    }

    public void execute() {
        //sum from 1 into 2, sum = sum 2
        //carry = or both carry values
        h1.setInputs(B, C);
        h1.execute();

        h2.setInputs(A, h1.getSum());
        h2.execute();

        oGate.setInputs(h1.getCarry(), h2.getCarry());
        oGate.execute();

        sum = h2.getSum();
        carry = oGate.getOutput();
    }


}
