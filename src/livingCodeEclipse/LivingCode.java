package livingCodeEclipse;

import java.util.Random;

public class LivingCode {

	static String[] commands = {"if","up","light","down","!light","endIf"};
	static String[] methods = {"down","up","if","endIf"};
	static String[] variables = {"light","!light"};
	LivingObject[] livingObjects = new LivingObject[100];
	int longestLiving = 0;
	LivingObject longestLived;
	int totalTime = 0;
	int amountSpawned = 0;
	float totalChildern = 0;
	float totalTimeAlive = 0;
	Random random = new Random();

	public static void main(String[] args) {
		LivingCode mainObject = new LivingCode();
		mainObject.init();
		while (mainObject.longestLiving < 100 && mainObject.totalTime != 1000) {
			mainObject.update();
		}
		for (int i=0;i<mainObject.livingObjects.length;i++) {
			System.out.print(mainObject.livingObjects[i].timeLiving+","+mainObject.livingObjects[i].living+","+mainObject.livingObjects[i].generation+" | ");
			for (int j=0;j<mainObject.livingObjects[i].heritige.length;j++) {
				if (mainObject.livingObjects[i].heritige[j] != null){
					System.out.print(mainObject.livingObjects[i].heritige[j]+" ");
				}
			}
			System.out.println();
		}
		for (int i=0;i<mainObject.longestLived.codeDna.length;i++) {
			System.out.print(commands[mainObject.longestLived.codeDna[i]]+" ");
		}
		System.out.println();
		for (int i=0;i<mainObject.longestLived.heritige.length;i++) {
			if (mainObject.longestLived.heritige[i] != null) {
				System.out.print(mainObject.longestLived.heritige[i]+" ");
			}
		}
		System.out.println();
		System.out.println(mainObject.longestLived.timeLiving +" | "+mainObject.longestLived.living+" | "+ mainObject.totalChildern/mainObject.amountSpawned+" | "+mainObject.totalTimeAlive/mainObject.amountSpawned);
		System.out.println(mainObject.amountSpawned + " " + mainObject.totalTime);
	}
	
	public void init() {
		for(int i = 0; i < livingObjects.length; i++){
			livingObjects[i] = new LivingObject();
			livingObjects[i].init();
			amountSpawned++;
			livingObjects[i].heritige[0] = amountSpawned;
		}
	}
	
	public void update() {
		for(int i = 0; i < livingObjects.length; i++) {
			if (livingObjects[i].living) {
				if (random.nextInt(2) == 2) {
					livingObjects[i].yPosition++;
				} else {
					livingObjects[i].yPosition--;
				}
				//System.out.println(Arrays.toString(livingObjects[i].codeDna));
				livingObjects[i].runDna();
				if (livingObjects[i].yPosition > 5 || livingObjects[i].yPosition < -5) {
					livingObjects[i].life--;
				} 
				if (livingObjects[i].life <= 0) {
					livingObjects[i].living = false;
					totalChildern += livingObjects[i].children;
					totalTimeAlive += livingObjects[i].timeLiving;
					continue;
				}
				if (livingObjects[i].timeLiving % 6 == 0) {
					for( int j = 0; j < livingObjects.length; j++) {
						if (!livingObjects[j].living) {
							livingObjects[j] = new LivingObject();
							livingObjects[j].init();
							livingObjects[j].codeDna = livingObjects[i].codeDna;
							livingObjects[j].genarateDna(random.nextInt(livingObjects[j].codeDna.length));
							livingObjects[j].fixCode();
							livingObjects[j].heritige = livingObjects[i].heritige;
							livingObjects[j].generation = livingObjects[i].generation;
							livingObjects[j].generation ++;
							livingObjects[i].children ++;
							livingObjects[j].heritige[livingObjects[j].generation] = livingObjects[i].children;
							amountSpawned++;
							break;
						}
					}
				}
				livingObjects[i].timeLiving ++;
				if (livingObjects[i].timeLiving > longestLiving) {
					longestLiving = livingObjects[i].timeLiving;
					longestLived = livingObjects[i];
				}
			}
		}
		if (random.nextInt(5) == 5){
			for (int i = 0; i < livingObjects.length; i++) {
				if (!livingObjects[i].living) {
					livingObjects[i] = new LivingObject();
					livingObjects[i].init();
					amountSpawned++;
					break;
				}
			}
		}
		totalTime++;
	}
	
	
}