package crhodes.fetch;

public class FetchPSW
{
	public boolean N, Z, V, C;
	public String name;
	
	public FetchPSW() {
		N = Z = V = C = false;
		name = "PSW";
	}
	
	public void setFlags(boolean n, boolean z, boolean v, boolean c) {
		N = n;
		Z = z;
		V = v;
		C = c;
	}
	
	public void setN() {N = true;}
	public void clrN() {N = false;}
	public boolean getN() {return N;}
	
	public void setZ() {Z = true;}
	public void clrZ() {Z = false;}
	public boolean getZ() {return Z;}
	
	public void setV() {V = true;}
	public void clrV() {V = false;}
	public boolean getV() {return V;}
	
	public void setC() {C = true;}
	public void clrC() {C = false;}
	public boolean getC() {return C;}
	
	public void print() {
		System.out.println("PSW: N=" + N + " Z=" + Z + " V=" + V + " C=" + C);
	}
}
