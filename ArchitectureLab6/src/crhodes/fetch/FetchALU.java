package crhodes.fetch;

import java.util.Arrays;

public class FetchALU
{
	private FullAdder[] adders;
	// private XOrGate[] opGates;

	public static int WORD_SIZE = 8;

	private NotGate[] notGates;
	private AndGate[] andGates;
	private OrGate[] orGates;
	private XOrGate[] xOrGates;

	private boolean[] S;

	// private boolean subtract;
	private int opBits = 3;

	private boolean[] op = new boolean[opBits];

	private boolean overflow;

	private int bits; // needed?

	private boolean[] A, B;

	// private final String NEGATIVE = "-", POSITIVE = "+", ZERO = "";
	// private String sign = POSITIVE;

	public boolean[] getA()
	{
		return Arrays.copyOf(A, bits);
	}

	public boolean[] getB()
	{
		return Arrays.copyOf(B, bits);
	}

	public FetchALU()
	{
		this(WORD_SIZE);
	}

	public FetchALU(int nBits)
	{
		bits = nBits;

		adders = new FullAdder[bits];
		// opGates = new XOrGate[bits];
		notGates = new NotGate[bits];
		andGates = new AndGate[bits];
		orGates = new OrGate[bits];
		xOrGates = new XOrGate[bits];

		S = new boolean[bits];
		A = new boolean[bits];
		B = new boolean[bits];

		for (int i = 0; i < bits; i++)
		{
			adders[i] = new FullAdder();
			// opGates[i] = new XOrGate();
			notGates[i] = new NotGate();
			andGates[i] = new AndGate();
			orGates[i] = new OrGate();
			xOrGates[i] = new XOrGate();
		}

	}

	public void setA(boolean[] sA) {
		for(int i = 0; i < A.length; i++) {
			if (i < sA.length)
			{
				A[i] = sA[i];
			} else
			{
				A[i] = false;
			}
		}
	}
	
	public void setB(boolean[] sB) {
		for(int i = 0; i < B.length; i++) {
			if (i < sB.length)
			{
				B[i] = sB[i];
			} else
			{
				B[i] = false;
			}
		}
	}
	
	public void setInputs(boolean[] sA, boolean[] sB, boolean[] sOp)
	{
		// if(A.length != bits || B.length != bits) {
		// System.out.println("Error. Length of inputs must be equal to number
		// of bits");
		// //TODO: set inputs to zero if array not long enough
		// return;
		// }
		for (int i = 0; i < bits; i++)
		{
			setA(sA);

			setB(sB);
		}
		// subtract = op;
		for (int i = 0; i < op.length; i++)
		{
			if (i < sOp.length)
				op[i] = sOp[i];
			else op[i] = false;
		}
	}

	public void execute()
	{

		if (op[2])
		{// 1XX
			if (op[1])
			{// 11X
				if (op[0])
				{ // 111
					negate();
				} else
				{ // 110
					set();
				}
			} else
			{ // 10
				if (op[0])
				{// 101
					clear();
				} else
				{ // 100
					addSubtract(true);
				}
			}
		} else
		{ // 0XX
			if (op[1])
			{ // 01X
				if (op[0])
				{// 011
					addSubtract(false);
				} else
				{// 010
					xor();
				}
			} else
			{ // 00X
				if (op[0])
				{// 001
					or();
				} else
				{// 000
					and();
				}
			}
		}

		// set carry = overflow;
		// carry = needs more bits than have
		// overflow = overflow into sign bit
		// negative
		// negative, zero, overflow, carry
	}

	// public String getSign() {
	// return sign;
	// }

	private void and()
	{
		for (int i = 0; i < bits; i++)
		{
			andGates[i].setInputs(A[i], B[i]);
			andGates[i].execute();
			S[i] = andGates[i].getOutput();
		}
		overflow = false;
	}

	private void xor()
	{
		for (int i = 0; i < bits; i++)
		{
			xOrGates[i].setInputs(A[i], B[i]);
			xOrGates[i].execute();
			S[i] = xOrGates[i].getOutput();
		}
		overflow = false;
	}

	private void or()
	{
		for (int i = 0; i < bits; i++)
		{
			orGates[i].setInputs(A[i], B[i]);
			orGates[i].execute();
			S[i] = orGates[i].getOutput();
		}
		overflow = false;
	}

	private void clear()
	{
		for (int i = 0; i < bits; i++)
		{
			A[i] = false;
			B[i] = false;
			S[i] = false;
		}
		overflow = false;
	}

	private void set()
	{
		for (int i = 0; i < bits; i++)
		{
			A[i] = true;
			B[i] = true;
			S[i] = true;
		}
	}

	private void negate()
	{
		for (int i = 0; i < bits; i++)
		{
			notGates[i].setInput(A[i]);
			notGates[i].execute();
			A[i] = notGates[i].getOutput();

			notGates[i].setInput(B[i]);
			notGates[i].execute();
			B[i] = notGates[i].getOutput();

			notGates[i].setInput(S[i]);
			notGates[i].execute();
			S[i] = notGates[i].getOutput();
		}
	}

	private void addSubtract(boolean subtract)
	{
		for (int i = 0; i < bits; i++)
		{
			xOrGates[i].setInputs(B[i], subtract);
			xOrGates[i].execute();

			adders[i].setInputs(A[i], xOrGates[i].getOutput(),
					(i == 0) ? subtract : adders[i - 1].getCarry());
			adders[i].execute();

			S[i] = adders[i].getSum();
		}
		overflow = adders[bits - 1].getCarry();
		if (subtract)
		{
			overflow = !overflow;
		}
	}

	public boolean[] getSums()
	{
		return S;
	}

	public boolean getOverflow()
	{
		return overflow;
	}

	public boolean[] getOp()
	{
		// return subtract;
		return op;
	}

	public void print()
	{
		String out = "";

		System.out.println(out);
	}

	public void encodeOp(boolean[] theOp)
	{
		// TODO: Something...
	}

	public void andOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		setA(ssss);
		setB(dddd);
		and();
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = S[i];
		}
		psw.setFlags(false, false, false, false);
	}

	public void orOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		setA(ssss);
		setB(dddd);
		or();
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = S[i];
		}
		psw.setFlags(false, false, false, false);
	}

	public void xOrOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		setA(ssss);
		setB(dddd);
		xor();
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = S[i];
		}
		psw.setFlags(false, false, false, false);
	}

	public void addOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		setA(ssss);
		setB(dddd);
		addSubtract(false);
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = S[i];
		}
		psw.setFlags(false, false, false, false);
		if(isZero()) psw.setZ();
		else if(overflow) psw.setC();
	}

	public void subOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		setA(ssss);
		setB(dddd);
		addSubtract(true);
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = S[i];
		}
		psw.setFlags(false, false, false, false);
		if(isZero()) psw.setZ();
		else if(overflow) psw.setC();
	}

	
	public void mulOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		setA(ssss);
		setB(dddd);
		for(int i = 0; i < A.length; i++) {
			for(int j = 0; j < B.length; j++) {
				//B[i] * A[i]
			}
		}
	}

	public void divOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		
	}

	public void movOp(boolean[] ssss, boolean[] dddd, FetchPSW psw)
	{
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = ssss[i];
		}
		psw.setFlags(false, false, false, false);
	}
	
	public void clrOp(boolean[] dddd, FetchPSW psw) {
		clear();
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = S[i];
		}
		psw.setFlags(false, false, false, false);
	}
	
	public void setOp(boolean[] dddd, FetchPSW psw) {
		set();
		for(int i = 0; i < dddd.length; i++) {
			dddd[i] = S[i];
		}
		psw.setFlags(false, false, false, false);
	}
	
	public void incOp(boolean[] dddd, FetchPSW psw) {
		setA(new boolean[] {true});
		setB(dddd);
		addSubtract(false);
		for(int i = 0; i < S.length; i++) {
			dddd[i] = S[i];	
			}
		psw.setFlags(false, false, false, false);
		if(isZero()) psw.setZ();
		else if(overflow) psw.setV();
	}
	
	public void decOp(boolean[] dddd, FetchPSW psw) {
		setA(dddd);
		setB(new boolean[] {true});
		addSubtract(true);
		for(int i = 0; i < S.length; i++) {
		dddd[i] = S[i];	
		}
		psw.setFlags(false, false, false, false);
		if(isZero()) psw.setZ();
		else if(overflow) psw.setN();
		
	}
	
	public void negOp(boolean[] dddd, FetchPSW psw) {
		setA(dddd);
		negate();
		for(int i = 0; i < A.length; i++) {
			dddd[i] = A[i];
		}
		psw.setFlags(false, false, false, false);
		
	}
	
	public void bneOp(boolean[] dddd, FetchPSW psw) {
		if(isZero()) psw.setZ();
		//TODO: I have no idea why this is in this class
	}
	
	public void beqOp(boolean[] dddd, FetchPSW psw) {
		if(isZero()) psw.setZ();
		//TODO: what?
	}
	
	public void hltOp(boolean[] dddd, FetchPSW psw) {
		System.exit(0);
	}
	
	public void shiftLeft(boolean[] vals, FetchPSW psw) {
		if(vals[vals.length-1]) psw.setV();
		setA(vals);
		for(int i = A.length-1; i > 0 ;i--) {
			A[i] = A[i-1];
		}
		A[0] = false;
		for(int i = 0; i < A.length; i++) {
			vals[i] = A[i];
		}
	}
	
	public void shiftRight(boolean[] vals, FetchPSW psw) {
		setA(vals);
		A[A.length-1] = false;
		for(int i = 0; i < A.length - 1; i++) {
			A[i] = A[i+1];
		}
		
		for(int i = 0; i < A.length; i++) {
			vals[i] = A[i];
		}
	}
	
	public boolean isZero() {
		for(int i = 0; i < S.length; i++) {
			if(S[i]) return false;
		}
		return true;
	}
}
