package crhodes.fetch;

public class FetchMem
{
	public byte[] v;
	public int offset = 0;
	public int size = 1000;
	public boolean type; //prog=1, data=0
	
	public FetchMem() {
		v = new byte[size];
	}
	
	public void setByte(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			v[index] = 127;
	}
	
	public void setByte(int index, byte data) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			v[index] = data;
	}
	
	public void clrByte(int index){
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			v[index] = 0;
	}
	
	public byte getByte(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			return v[index];
	}
	
	public void print(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			System.out.println("MEM: " + index + ": " + v[index]);
	}
	
	
	public void testMem() {
		
	}
}
