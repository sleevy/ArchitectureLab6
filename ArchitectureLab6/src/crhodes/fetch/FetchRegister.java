package crhodes.fetch;

public class FetchRegister
{
	public int wordSize = 8;
	public boolean[] v;
	public String name;
	
	public FetchRegister() {
		v = new boolean[wordSize];
		name = "NA";
	}
	
	public FetchRegister(String aName) {
		this();
		name = aName;
	}
	
	public void setBit(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
		v[index] = true;
	}
	
	public void clrBit(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
		v[index] = false;
	}
	
	public boolean getBit(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
		return v[index];
	}
	
	public void setBits() {
		for(int i = 0; i < v.length; i++) {
			v[i] = true;
		}
	}
	
	public void clrBits() {
		for(int i = 0; i < v.length; i++) {
			v[i] = false;
		}
	}
	
	public boolean[] getBits() {
		return v;
	}
	
	public void setBits(byte val) {
		setBits(boolsFromByte(val));
	}
	
	public boolean[] boolsFromByte(byte b) {
		String s = Integer.toBinaryString(new Byte(b).intValue());
		boolean[] out = new boolean[8];
		for(int i = 0; i < out.length; i++) {
			out[out.length - 1 - i] = s.charAt(i) == '1' ? true:false;
		}
		return out;
	}
	
	public void setBits(boolean[] vals) {
		for(int i = 0; i < v.length; i++) {
			v[i] = vals[i];
		}
	}
	
	public void print() {
		String out = "";
		for(int i = 0; i < v.length; i++) {
			out = (v[i] ? "1":"0") + out;
		}
		System.out.println(out);
	}
}
