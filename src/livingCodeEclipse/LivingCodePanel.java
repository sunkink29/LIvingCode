package livingCodeEclipse;
import java.awt.*;
import java.util.Arrays;

import javax.swing.*;


public class LivingCodePanel extends JPanel {
	
	boolean hasDrawnObjects = false;
	LivingCode mainLivingCode = new LivingCode(10);
	
	public static void main(String[] args) {
		JFrame window = new JFrame("LivingCode");
		LivingCodePanel content = new LivingCodePanel();
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(120,70);
        window.setSize(400,300);
        content.mainLivingCode.init();
        Color background = new Color(255, 255, 255);
        content.setBackground(background);
        window.setVisible(true);
        long lastPaint = System.currentTimeMillis();
		while (content.mainLivingCode.longestLiving < 100 && content.mainLivingCode.totalTime != 1000) {
			if (content.hasDrawnObjects && System.currentTimeMillis() - lastPaint == 250) {
				lastPaint = System.currentTimeMillis();
				content.hasDrawnObjects = false;
				content.mainLivingCode.update();
				content.repaint();
				for (int i=0;i<content.mainLivingCode.longestLived.codeDna.length;i++) {
					System.out.print(LivingCode.commands[content.mainLivingCode.longestLived.codeDna[i]]+" ");
				}
				System.out.println();
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int hight = getHeight();
		int boxSize = 5;
		int perfectRangeMid = hight/2;
		int yPosStepHight = 2;
		int margin = (width - boxSize * mainLivingCode.livingObjects.length) / mainLivingCode.livingObjects.length;
		Color currentColor;
		currentColor = Color.BLACK;
		g.setColor(currentColor);
		g.drawLine(0, perfectRangeMid+LivingObject.yPositionMin*yPosStepHight, width, perfectRangeMid+LivingObject.yPositionMin*yPosStepHight);
		g.drawLine(0, perfectRangeMid+LivingObject.yPositionMax*yPosStepHight, width, perfectRangeMid+LivingObject.yPositionMax*yPosStepHight);
		for (int i=0;i<mainLivingCode.livingObjects.length;i++) {
			if (mainLivingCode.livingObjects[i].living) {
				Color startColor = mainLivingCode.livingObjects[i].color;
				Color deadColor = Color.WHITE;
				float ratio = (float)mainLivingCode.livingObjects[i].life/mainLivingCode.livingObjects[i].maxLife;
				int red = (int)Math.abs((ratio * startColor.getRed()) + ((1 - ratio) * deadColor.getRed()));
				int green = (int)Math.abs((ratio * startColor.getGreen()) + ((1 - ratio) * deadColor.getGreen()));
				int blue = (int)Math.abs((ratio * startColor.getBlue()) + ((1 - ratio) * deadColor.getBlue()));
				currentColor = new Color(red,green,blue);
				g.setColor(currentColor);
				g.fillRect(margin+(margin*i)+(boxSize*i)-boxSize/2,perfectRangeMid - mainLivingCode.livingObjects[i].yPosition*yPosStepHight-boxSize/2, boxSize, boxSize);
			}
		}
		hasDrawnObjects = true;
	}
}
