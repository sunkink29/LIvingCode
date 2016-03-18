package livingCodeEclipse;

import java.util.Arrays;
import java.util.Random;

public class LivingCode {

	static String[] commands = {"if","up","light","down","!light","endIf"};
	static String[] methods = {"down","up","if","endIf"};
	static String[] variables = {"light","!light"};
	static LivingObject[] livingObjects = new LivingObject[40];
	static int longestLiving = 0;
	static LivingObject longestLived;
	static int totalTime = 0;
	static int amountSpawned = 0;
	static Random random = new Random();

	public static void main(String[] args) {
		for(int i = 0; i < livingObjects.length; i++){
//			System.out.println("init");
			livingObjects[i] = new LivingObject();
			livingObjects[i].init();
			amountSpawned++;
		}

		while (longestLiving < 1000 && totalTime != 1000) {
			for(int i = 0; i < livingObjects.length; i++) {
				if (livingObjects[i].living) {
					//System.out.println(Arrays.toString(livingObjects[i].codeDna));
					livingObjects[i].runDna();
					if (livingObjects[i].yPosition > 5 || livingObjects[i].yPosition < -5) {
						livingObjects[i].life--;
					} 
					if (livingObjects[i].life <= 0) {
						livingObjects[i].living = false;
					}
					if (livingObjects[i].timeLiving == 6) {
						for( int j = 0; j < livingObjects.length; j++) {
							if (livingObjects[j].living) {
								livingObjects[j] = new LivingObject();
								livingObjects[j].init();
								livingObjects[j].codeDna = livingObjects[i].codeDna;
								livingObjects[j].genarateDna(random.nextInt(livingObjects[j].codeDna.length));
								livingObjects[j].fixCode();
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
	//				livingObjects[i].yPosition --;
//					System.out.println(livingObjects[i].yPosition);
				}
			}
			for (int i = 0; i < livingObjects.length; i++) {
				if (!livingObjects[i].living) {
					livingObjects[i] = new LivingObject();
					livingObjects[i].init();
					amountSpawned++;
					break;
				}
			}
			totalTime ++;
		}
		for (int i=0;i<longestLived.codeDna.length;i++) {
			System.out.print(commands[longestLived.codeDna[i]]+" ");
		}
		System.out.println(longestLived.timeLiving);
		System.out.println(amountSpawned + " " + totalTime);
	}
}