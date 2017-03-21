package crhodes.fetch;

public class HalfAdder {
    private AndGate andy, and2;
    private OrGate orbit;
    private NotGate not1;
    private boolean sum;
    private boolean carry;

    public HalfAdder() {
        andy = new AndGate();
        and2 = new AndGate();
        orbit = new OrGate();
        not1 = new NotGate();
    }

    public void execute() {

        andy.execute();
        orbit.execute();

        carry = andy.getOutput();

        not1.setInput(andy.getOutput());
        not1.execute();

        and2.setInputs(orbit.getOutput(), not1.getOutput());
        and2.execute();

        sum = and2.getOutput();
    }

    public boolean getSum() {
        return sum;
    }

    public boolean getCarry() {
        return carry;
    }

    public void setInputs(boolean sA, boolean sB) {
        andy.setInputs(sA, sB);
        orbit.setInputs(sA, sB);
    }

    public boolean getA() {
        return andy.getA();
    }

    public boolean getB() {
        return andy.getB();
    }

    public void test() {
        setInputs(false, false);
        execute();
        print();

        setInputs(false, true);
        execute();
        print();

        setInputs(true, false);
        execute();
        print();

        setInputs(true, true);
        execute();
        print();
    }

    public void print() {
        String out = "";
        out += "Half Adder: [A: " + getA() + ",B: " + getB() + "] --> Sum: " + sum + " Carry: " + carry;
        System.out.println(out);
    }


}
