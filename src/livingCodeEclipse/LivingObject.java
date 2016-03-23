package livingCodeEclipse;

import java.util.Arrays;
import java.util.Random;

class LivingObject {
	int life = 10;
	boolean living = true;
	int yPosition = 7;
	Integer[] codeDna = new Integer[20];
	Integer[] heritige = new Integer[1000];
	int children = 0;
	int generation = 1;
	int timeLiving = 0;
	static int yPositionMin = -10;
	static int yPositionMax = 10;
	Random random = new Random();
	
	public void init(){
		yPosition = yPositionMin + (int)((random.nextInt(100)/100.0) * ((yPositionMax - yPositionMin) + 1));
		life = random.nextInt(10)+5;
		for (int i = 0; i < codeDna.length;i++) {
			genarateDna(i);
		}
	}
	
	void genarateDna(int i) {
		String command;
		boolean isInIf = checkIfInIf(i);
//		System.out.println(isInIf);
		if (i != 0 && LivingCode.commands[codeDna[i-1]] == "if") {
			command = LivingCode.variables[random.nextInt(LivingCode.variables.length)];
		} else if (isInIf) {
			command = LivingCode.methods[random.nextInt(LivingCode.methods.length)];
		} else {
			command = LivingCode.methods[random.nextInt(LivingCode.methods.length-1)];
		}
		if (command == "if" && isInIf) {
			command = LivingCode.methods[random.nextInt(LivingCode.methods.length-2)];
		}
		codeDna[i] = java.util.Arrays.asList(LivingCode.commands).indexOf(command);
	}
	void fixCode(){
		boolean isInIf = false;
		for (int i = 0;i<codeDna.length;i++) {
			String command = LivingCode.commands[codeDna[i]];
			isInIf = checkIfInIf(i);
			if (i != 0 && LivingCode.commands[codeDna[i-1]] == "if") {
				if (Arrays.asList(LivingCode.variables).indexOf(command) == -1) {
					genarateDna(i);
				}
			} else if (command == "endif" && !isInIf) {
				genarateDna(i);
			} else if (Arrays.asList(LivingCode.variables).indexOf(command) != -1 && LivingCode.commands[codeDna[i-1]] != "if") {
				genarateDna(i);
			} else if (command == "if" && isInIf) {
				genarateDna(i);
			}
		}
	}
	
	boolean checkIfInIf(int index) {
		boolean isInIf = false;
		for (int i=0;i<index;i++){
			String command = LivingCode.commands[codeDna[i]];
			if (command == "if") {
				isInIf = true;
			} else if (command == "endIf") {
				isInIf = false;
			}
		}
		return isInIf;
	}
	
	public void runDna() {
		IfData ifData = null;
		String method = "";
		for(int i = 0; i < codeDna.length; i++) {
			switch (LivingCode.commands[codeDna[i]]) {
				case "if": ifData = new IfData();
					break;
				case "endIf": ifData = null;
					break;
				case "light": if (ifData != null && ifData.stringIfArg == null)
					ifData.stringIfArg = "light";
					break;
				case "!light": if (ifData != null && ifData.stringIfArg == null)
					ifData.stringIfArg = "!light";
					break;
				case "down" : method = "down";
					break;
				case "up" : method = "up";
					break;
			}
			if (ifData != null && ifData.stringIfArg != null) {
				switch (ifData.stringIfArg) {
					case "light": if (yPosition > 0) 
						ifData.ifArg = true; 
						else 
						ifData.ifArg = false; 
						break;
					case "!light": if (yPosition < 0)
						ifData.ifArg = false;
						else
						ifData.ifArg = true;
						break;
				}
			}
			if (ifData != null && ifData.ifArg) {
				switch (method) {
					case "down": goDown();
						break;
					case "up": goUp();
						break;
				}
			} else if (ifData == null) {
				switch (method) {
					case "down": goDown();
						break;
					case "up": goUp();
						break;
				}
			}
		}
	}

	void goDown() {
		yPosition -= 1;
	}

	void goUp() {
		yPosition +=1;
	}
	
	static class IfData {
		
		String stringIfArg = "";
		boolean ifArg = false;
	}

}