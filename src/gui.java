import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.media.sound.Toolkit;
import com.sun.tools.javac.launcher.Main;

public class gui {

	public static void layout(String grid, String output) {

		int numberInX = (Integer.parseInt(grid.split(";")[0].split(",")[0]));
		int numberInY = (Integer.parseInt(grid.split(";")[0].split(",")[1]));
		String totalHealth = grid.split(";")[4];
		int submarineX = Integer.parseInt(grid.split(";")[2].split(",")[0]);
		int submarineY = Integer.parseInt(grid.split(";")[2].split(",")[1]);

		int ethanX = Integer.parseInt(grid.split(";")[1].split(",")[0]);
		int ethanY = Integer.parseInt(grid.split(";")[1].split(",")[1]);
		String memberLocations = grid.split(";")[3];
		List<String> gridArray = Arrays.asList(memberLocations.replace(";", ",").split("(?<!\\G\\d+),"));
		JFrame frame = new JFrame("Grid Layout");
		JPanel panel = new JPanel(new GridLayout(numberInX, numberInY));
		frame.add(panel);
		JLabel label, ethansLabel = new JLabel();
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		for (int i = 0; i < numberInX; i++) {
			for (int j = 0; j < numberInY; j++) {
				label = new JLabel();
				panel.add(label);
				label.setOpaque(true);
				if (ethanX == i && ethanY == j) {
//					label.setBackground(Color.cyan);
					label.setText("ethan");
					ethansLabel.setText("ethan");
				} else if (submarineX == i && submarineY == j) {
//					label.setBackground(Color.blue);
					label.setText("submarine");
				}
				else if (gridArray.contains(i + "," + j)) {
//					label.setBackground(Color.red);
					label.setText("imf");
				} else {
//					label.setBackground(Color.black);
				}
				labels.add(label);
			}
		}
		String[] outputLength = output.split(",");
		boolean v = false;
		for (int i = 0; i < outputLength.length; i++) {
			System.out.println(outputLength[i]);
			if (outputLength[i].equals("up") && !v) {
//				v = true;
//				ethansLabel = new JLabel("ethan");
				JLabel newLabel = new JLabel();
				newLabel.setText("t");
//				panel.add(ethansLabel, ethanX );
				panel.add(newLabel, 0);
//				ethanX -= 1;
			}
		}
		frame.setSize(1000, 1000);
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String output = "up,right,carry,down,down,down,right,carry,down,left,carry,left,left,left,drop"
				+ "up,carry,up,right,carry,down,right,carry,down,left,left,drop";
		layout(grid, output);
	}
}
