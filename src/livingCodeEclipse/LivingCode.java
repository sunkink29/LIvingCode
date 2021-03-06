package livingCodeEclipse;

import java.awt.Color;
import java.util.Random;

public class LivingCode {

	static String[] commands = {"if","up","light","down","!light","endIf"};
	static String[] methods = {"down","up","if"};
	static String[] variables = {"light","!light"};
	LivingObject[] livingObjects;
	int longestLiving = 0;
	LivingObject longestLived;
	int totalTime = 0;
	int amountSpawned = 0;
	float totalChildern = 0;
	float totalTimeAlive = 0;
	Random random = new Random();
	static int defaltNumOfObjects = 20;
	long randNumGenSeed;
	
	public LivingCode() {
		this(defaltNumOfObjects,0);
	}
	
	public LivingCode(int num) {
		this(num,0);
	}
	
	public LivingCode(long seed) {
		this(defaltNumOfObjects, seed);
	}
	
	public LivingCode(int num, long seed) {
		livingObjects = new LivingObject[num];
		if (randNumGenSeed == 0) {
		randNumGenSeed = random.nextLong();
		}
		random.setSeed(randNumGenSeed);
	}
	
	static void changeMidOfSafeZone(int mid) {
		LivingObject.yPositionMax = mid + LivingObject.safezoneHight/2;
		LivingObject.yPositionMin = mid - LivingObject.safezoneHight/2;
		LivingObject.safeMid = mid;
	}

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
		System.out.println(mainObject.randNumGenSeed);
	}
	
	public void init() {
		for(int i = 0; i < livingObjects.length; i++){
			livingObjects[i] = new LivingObject(random.nextLong());
			livingObjects[i].init();
			amountSpawned++;
			livingObjects[i].heritige[0] = amountSpawned;
		}
	}
	
	public void update() {
		for(int i = 0; i < livingObjects.length; i++) {
			if (livingObjects[i].living) {
//				int goUpOrDown = random.nextInt(1);
//				if (goUpOrDown == 1) {
//					livingObjects[i].yPosition++;
//				} else if (goUpOrDown == 5) {
//					livingObjects[i].yPosition--;
//				}
				livingObjects[i].runDna();
				if (livingObjects[i].yPosition > LivingObject.yPositionMax || livingObjects[i].yPosition < LivingObject.yPositionMin) {
					livingObjects[i].life--;
				} 
				if (livingObjects[i].life <= 0) {
					livingObjects[i].living = false;
					totalChildern += livingObjects[i].children;
					totalTimeAlive += livingObjects[i].timeLiving;
					continue;
				}
				if (livingObjects[i].timeLiving % 10 == 0) {
					for( int j = 0; j < livingObjects.length; j++) {
						if (!livingObjects[j].living) {
							livingObjects[j] = new LivingObject(random.nextLong());
							livingObjects[j].init();
							livingObjects[j].yPosition = livingObjects[i].yPosition;
							livingObjects[j].codeDna = livingObjects[i].codeDna;
							livingObjects[j].genarateDna(random.nextInt(livingObjects[j].codeDna.length));
							livingObjects[j].fixCode();
							livingObjects[j].heritige = livingObjects[i].heritige;
							livingObjects[j].generation = livingObjects[i].generation;
							livingObjects[j].generation ++;
							livingObjects[i].children ++;
							livingObjects[j].heritige[livingObjects[j].generation] = livingObjects[i].children;
							int red = livingObjects[i].color.getRed();
							int green = livingObjects[i].color.getGreen();
							int blue = livingObjects[i].color.getGreen();
							int rgbRandomInt = random.nextInt(2);
							int randomint = 10;
							if (rgbRandomInt == 0) {
								if (random.nextBoolean() && red + 10 <= 255 || red == 0){
									red += random.nextInt(randomint);
								} else if (red >= randomint) {
									red -= random.nextInt(randomint);
								} else {
									red -= random.nextInt(red);
								}
							} else if(rgbRandomInt == 1) {
								if (random.nextBoolean() && green + 10 <= 255 || green == 0) {
									green += random.nextInt(randomint);
								} else if (green >= randomint) {
									green -= random.nextInt(randomint);
								} else {
									green -= random.nextInt(green);
								}
							} else {
								if (random.nextBoolean() && blue + 10 <= 255 || blue == 0) {
									blue += random.nextInt(randomint);
								} else if(blue >= randomint) {
									blue -= random.nextInt(randomint);
								} else {
									blue -= random.nextInt(blue);
								}
							}
							livingObjects[j].color = new Color(red, green, blue);
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
					livingObjects[i] = new LivingObject(random.nextLong());
					livingObjects[i].init();
					amountSpawned++;
					break;
				}
			}
		}
		totalTime++;
//		if (longestLiving == 100 && longestLived.living) {
//			changeMidOfSafeZone(random.nextInt(livingObjects));
//		}
	}
}