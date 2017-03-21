package crhodes.fetch;

public class FetchCPU
{
	public FetchRegister r0, r1, r2, r3, pc, sp;
	public FetchPSW psw;
	public FetchALU alu;
	public FetchMem data;
	public FetchMem prog;
	
	public FetchCPU() {
		alu = new FetchALU();
		data = new FetchMem();
		
		//create 4 8-bit registers
		r0 = new FetchRegister("r0");
		r0 = new FetchRegister("r1");
		r0 = new FetchRegister("r2");
		r0 = new FetchRegister("r3");
		
		pc = new FetchRegister("pc");
		sp = new FetchRegister("sp");
		
		//create 4-bit PSW
		psw = new FetchPSW();
	}
	
	
	
	public int boolsToInt(boolean[] b) {
		int val = 0;
		for(int pow = 0; pow < b.length; pow++) {
			if(b[pow])
				val += Math.pow(2, pow);
		}
		return val;
	}
	
	public byte boolsToByte(boolean[] b) {
		byte val = 0;
		
		String s = "";
		for(int i = 0; i < b.length; i++) {
			s = (b[i] ? "1":"0") + s;
		}
		
		val = Byte.valueOf(s, 2);
		return val;
	}
	
	
}
