package crhodes.fetch;

public class FetchMem
{
	public Cell[] v;
	public int offset = 0;
	public int size = 1000;
	public boolean type; //prog=1, data=0
	
	public FetchMem() {
		v = new Cell[size];
		for(int i = 0; i < v.length; i++) {
			v[i] = new Cell();
		}
	}
	
	public void setData(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			v[index].setData(new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true });
	}
	
	public void setData(int index, boolean[] data) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			v[index].setData(data);
	}
	
	public void clrData(int index){
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			v[index].setData(new boolean[] {false});;
	}
	
	public boolean[] getData(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			return v[index].getData();
	}
	
	public void print(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		else
			System.out.println("MEM: " + index + ": " + v[index]);
	}
	
	public boolean[] getDestination(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
			
		
		boolean[] data = new boolean[4];
		boolean[] cellData = v[index].getData();
		for(int i = 0; i < 4; i++) {
			data[i] = cellData[i];
		}
		
		return data;
	}
	
	public boolean[] getSource(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		
		
		boolean[] data = new boolean[4];
		boolean[] cellData = v[index].getData();
		for(int i = 4; i < 8; i++) {
			data[i] = cellData[i];
		}
		
		return data;
	}
	
	public boolean[] getCommand(int index) {
		if(index > v.length) {
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		}
		
		boolean[] data = new boolean[4];
		boolean[] cellData = v[index].getData();
		for(int i = 8; i < 12; i++) {
			data[i] = cellData[i];
		}
		
		return data;
	}
	
	
	
	public void testMem() {
		
	}
	
	private class Cell {
		boolean[] data;
		public Cell() {
			data = new boolean[12];
		}
		
		public boolean[] getData() {
			return data;
		}
		
		public void setData(boolean[] newData) {
			for(int i = 0; i < data.length; i++) {
				if(i < newData.length) {
					data[i] = newData[i];
				} else {
					data[i] = false;
				}
			}
		}
	}
}
