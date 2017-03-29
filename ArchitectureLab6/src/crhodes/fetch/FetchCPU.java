package crhodes.fetch;

public class FetchCPU
{
	public FetchRegister r0, r1, r2, r3, pc, sp;
	public FetchPSW psw;
	public FetchALU alu;
	public FetchMem data;
	public FetchMem prog;
	private int progPosition = 0;
	private int destinationAddress = 0;
	private boolean skipNextLine = false;
	private boolean branched =  false;
	private boolean destinationIsRegister = false;
	private boolean destinationIsInProg = false;
	
	public FetchCPU() {
		alu = new FetchALU();
		data = new FetchMem();
		
		//create 4 8-bit registers
		r0 = new FetchRegister("r0");
		r1 = new FetchRegister("r1");
		r2 = new FetchRegister("r2");
		r3 = new FetchRegister("r3");
		
		pc = new FetchRegister("pc");
		sp = new FetchRegister("sp");
		
		//create 4-bit PSW
		psw = new FetchPSW();
	}
	
	public void loadTestProgram() {
		String theLine = "";
		//set up program
		theLine="1111 0000 0000";
		prog.setData(0, stringToBools(theLine));
		
//		theLine="";
//		prog.setData(1, stringToBools(theLine));
//		
//		
//		//initialize memory
//		theLine="";
//		data.setData(100, stringToBools(theLine));
//		
//		theLine="";
//		data.setData(101, stringToBools(theLine));
		
	}
	
	public boolean[] stringToBools(String input) {
		input.replace(" ", "");
		if(input.length() == 0) return new boolean[] {false};
		
		boolean[] out = new boolean[input.length()];
		for(int i = 0; i < out.length; i++) {
			out[i] = '1' == input.charAt(input.length()-1-i) ? true:false;
		}
		
		return out;
	}
	
	public void executeProgram() {
		boolean[] cmd = prog.getCommand(progPosition);
		parseCommand(cmd);
		
		//advance the program pointer
		if(branched) {
			branched=false;
			skipNextLine = false;
		} else if(skipNextLine) {
			progPosition += 2;
			skipNextLine = false;
		} else {
			progPosition += 1;
		}
		
	}
	
	private boolean[] getDataFromAddress(boolean[] ssss, boolean isDestination) {
		if(isDestination) destinationAddress = 0;
		boolean[] out = new boolean[] {false};
		if(ssss[3]) {
			//10NN
			//11NN	
			skipNextLine = true;
			if(ssss[2]) {
				//NN is address of operand
				boolean[] address = prog.getData(progPosition+1);
				int add = boolsToInt(address);
				
				if(isDestination) destinationAddress = add;
				
				out = data.getData(add);
				
			} else {
				//NN is operand
				if(isDestination) {
					destinationIsInProg = true;
					destinationAddress = progPosition+1;
				}
				out = prog.getData(progPosition+1);
			}
			
		} else {
			FetchRegister theReg;
			//01RN (content is address)
			//00RN (content is content)
			if(ssss[1]) { //2,3
				theReg = (ssss[0] ? r3:r2);
			} else {
				theReg = (ssss[0] ? r1:r0);
			}
			
			//content is address
			if(ssss[2]) {
				boolean[] address = theReg.getBits();
				int add = boolsToInt(address);
				if(isDestination) {
					destinationAddress = add;
				}
				out = data.getData(add);
			} else {
				//content is content
				if(isDestination) {
					destinationIsRegister = true;
					destinationAddress = boolsToInt(new boolean[] {ssss[1], ssss[0]});
				}
				out = theReg.getBits();
			}
		}
		return out;
	}
	
	private void commitToDestination(boolean[] theData) {
		if(destinationIsRegister) {
			FetchRegister reg;
			switch(destinationAddress) {
				case 0:
					reg = r0;
					break;
				case 1:
					reg = r1;
					break;
				case 2:
					reg = r2;
					break;
				case 3:
					reg = r3;
					break;
				default:
					//discard information
					System.out.println("Incorrect register for memory write");
					reg = new FetchRegister();
					break;
			}
			reg.setData(theData);
			
		} else if(destinationIsInProg) {
			prog.setData(destinationAddress, theData);
			
		} else {
			data.setData(destinationAddress, theData);
			
		}
		destinationIsInProg = false;
		destinationIsRegister = false;
	}
	
	public void parseCommand(boolean[] cmd) {
		boolean[] dddd = new boolean[12];
		//
		if(cmd[3]) {
			//1XXX is 1-value ops
			dddd = getDataFromAddress(prog.getSource(progPosition), true);
			if(cmd[2]) {
				//11XX
				if(cmd[1]) {
					//111X
					if(cmd[0]) {
						//1111
						//HALT
						alu.hltOp(dddd, psw);;
					} else {
						//1110
						//BEQ dddd
						alu.beqOp(dddd, psw);
						if(psw.getZ()) {
							branched = true;
							progPosition = boolsToInt(dddd);
						}
					}
				} else {
					//110X
					if(cmd[0]) {
						//1101
						//BNE dddd
						alu.bneOp(dddd, psw);
						if(!psw.getZ()) {
							branched = true;
							progPosition = boolsToInt(dddd);
						}
					} else {
						//1100 
						alu.negOp(dddd, psw);
					}
				}
			} else {
				//10XX
				if(cmd[1]) {
					//101X
					if(cmd[0]) {
						//1011
						alu.decOp(dddd, psw);
					} else{
						//1010
						alu.incOp(dddd, psw);
					}
				} else {
					//100X
					if(cmd[0]) {
						//1001
						alu.setOp(dddd, psw);
					} else {
						//1000
						alu.clrOp(dddd, psw);
					}
				}
			}
		} else {
			//0XXX means 2-value ops
			boolean[] ssss = getDataFromAddress(prog.getSource(progPosition), false);
			dddd = getDataFromAddress(prog.getDestination(progPosition), true);
			if(cmd[2]) {
				//01XX
				if(cmd[1]) {
					//011X
					if(cmd[0]) {
						//0111
						alu.movOp(ssss, dddd, psw);
					} else {
						//0110
						alu.divOp(ssss, dddd, psw);
					}
				} else {
					//010X
					if(cmd[0]) {
						//0101
						//non-functional right now
						alu.mulOp(ssss, dddd, psw);
					} else {
						//0100 
						alu.subOp(ssss, dddd, psw);
					}
				}
			} else {
				//00XX
				if(cmd[1]) {
					//001X
					if(cmd[0]) {
						//0011
						alu.addOp(ssss, dddd, psw);
					} else{
						//0010
						alu.xOrOp(ssss, dddd, psw);
					}
				} else {
					//000X
					if(cmd[0]) {
						//0001
						alu.orOp(ssss, dddd, psw);
					} else {
						//0000
						alu.andOp(ssss,dddd,psw);
					}
				}
			}
		}
		
		if(!branched) {
			commitToDestination(dddd);
		}
	}
	
	
	public static int boolsToInt(boolean[] b) {
		int val = 0;
		for(int pow = 0; pow < b.length; pow++) {
			if(b[pow])
				val += Math.pow(2, pow);
		}
		return val;
	}
	
	public static byte boolsToByte(boolean[] b) {
		byte val = 0;
		
		String s = "";
		for(int i = 0; i < b.length; i++) {
			s = (b[i] ? "1":"0") + s;
		}
		
		val = Byte.valueOf(s, 2);
		return val;
	}
	
	
}
