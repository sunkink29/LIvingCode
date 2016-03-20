package livingCodeEclipse;

import java.util.Arrays;
import java.util.Random;

public class LivingCode {

	static String[] commands = {"if","up","light","down","!light","endIf"};
	static String[] methods = {"down","up","if","endIf"};
	static String[] variables = {"light","!light"};
	static LivingObject[] livingObjects = new LivingObject[100];
	static int longestLiving = 0;
	static LivingObject longestLived;
	static int totalTime = 0;
	static int amountSpawned = 0;
	static float totalChildern = 0;
	static float totalTimeAlive = 0;
	static Random random = new Random();

	public static void main(String[] args) {
		for(int i = 0; i < livingObjects.length; i++){
//			System.out.println("init");
			livingObjects[i] = new LivingObject();
			livingObjects[i].init();
			amountSpawned++;
			livingObjects[i].heritige[0] = amountSpawned;
		}

		while (longestLiving < 100 && totalTime != 1000) {
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
			totalTime ++;
		}
		for (int i=0;i<livingObjects.length;i++) {
			System.out.print(livingObjects[i].timeLiving+","+livingObjects[i].living+","+livingObjects[i].generation+" | ");
			for (int j=0;j<livingObjects[i].heritige.length;j++) {
				if (livingObjects[i].heritige[j] != null){
					System.out.print(livingObjects[i].heritige[j]+" ");
				}
			}
			System.out.println();
		}
		for (int i=0;i<longestLived.codeDna.length;i++) {
			System.out.print(commands[longestLived.codeDna[i]]+" ");
		}
		System.out.println();
		for (int i=0;i<longestLived.heritige.length;i++) {
			if (longestLived.heritige[i] != null) {
				System.out.print(longestLived.heritige[i]+" ");
			}
		}
		System.out.println();
		System.out.println(longestLived.timeLiving +" | "+longestLived.living+" | "+totalChildern/amountSpawned+" | "+totalTimeAlive/amountSpawned);
		System.out.println(amountSpawned + " " + totalTime);
	}
}