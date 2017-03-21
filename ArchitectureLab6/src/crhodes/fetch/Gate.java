package crhodes.fetch;

public abstract class Gate {
    /**
     *
     */
    abstract public void print();

    /**
     * Sets output based on inputs and gate type
     */
    abstract public void execute();


    /**
     * @return
     */
    abstract public boolean getOutput();

}
