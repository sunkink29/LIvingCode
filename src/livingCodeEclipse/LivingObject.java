package livingCodeEclipse;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

class LivingObject {
	int maxLife = 10;
	int life;
	boolean living = true;
	int yPosition = 7;
	Integer[] codeDna = new Integer[15];
	Integer[] heritige = new Integer[1000];
	int children = 0;
	int generation = 1;
	int timeLiving = 0;
	Color color;
	static int spawnYMin = 10;
	static int spawnYMax = 15;
	static int yPositionMin;
	static int yPositionMax;
	static int safezoneHight = 6;
	static int safeMid = 0;
	Random random = new Random();
	int pointInCode = 0;
	IfData currentIfData = null;
	
	public LivingObject(long seed) {
		random.setSeed(seed);
		LivingCode.changeMidOfSafeZone(safeMid);
	}
	
	public void init(){
		color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		yPosition = spawnYMin + (int)((random.nextInt(100)/100.0) * ((spawnYMax - spawnYMin) + 1));
		maxLife = random.nextInt(10)+maxLife;
		life = maxLife;
		for (int i = 0; i < codeDna.length;i++) {
			genarateDna(i);
		}
	}
	
	void genarateDna(int i) {
		String command;
		boolean isInIf = checkIfInIf(i);
//		System.out.println(isInIf);
		if (i >= 1 && LivingCode.commands[codeDna[i-1]] == "if") {
			command = LivingCode.variables[random.nextInt(LivingCode.variables.length)];
		} else if (i >= 2 && LivingCode.commands[codeDna[i-2]] == "if") {
			command = LivingCode.methods[random.nextInt(LivingCode.methods.length)];
		} else if (i >= 3 && LivingCode.commands[codeDna[i-3]] == "if"){
			command = "endIf";
		} else {
			command = "if"; /*LivingCode.methods[random.nextInt(LivingCode.methods.length)];*/
		}
		if (command == "if" && isInIf) {
			command = LivingCode.methods[random.nextInt(LivingCode.methods.length-1)];
		}
		codeDna[i] = java.util.Arrays.asList(LivingCode.commands).indexOf(command);
	}
	void fixCode(){
		boolean isInIf = false;
		for (int i = 0;i<codeDna.length;i++) {
			String command = LivingCode.commands[codeDna[i]];
			isInIf = checkIfInIf(i);
			if (i != 0 && LivingCode.commands[codeDna[i-1]] == "if" && Arrays.asList(LivingCode.variables).indexOf(command) == -1) {
				genarateDna(i);
			} else if (command == "endif" && !isInIf || i >= 3 && LivingCode.commands[codeDna[i-3]] == "if") {
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
		IfData ifData = currentIfData;
		String method = "";
		if (pointInCode>=codeDna.length) {
			pointInCode = 0;
			ifData = null;
		}
		main:
		for(int i = pointInCode; i < codeDna.length; i++) {
			switch (LivingCode.commands[codeDna[i]]) {
				case "if": ifData = new IfData(); System.out.println("if");
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
						currentIfData = ifData;
						pointInCode = i+1;
						break main;
					case "up": goUp();
						currentIfData = ifData;
						pointInCode = i+1;
						break main;
				}
			} else if (ifData == null) {
				switch (method) {
					case "down": goDown();
						currentIfData = ifData;
						pointInCode = i+1;
						break main;
					case "up": goUp();
						currentIfData = ifData;
						pointInCode = i+1;
						break main;
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
		
		String stringIfArg = null;
		boolean ifArg = false;
	}

}