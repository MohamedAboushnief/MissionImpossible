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
import java.util.concurrent.TimeUnit;

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

	public static Object[] drawPanel(String grid, String output, JPanel panel) {
		int numberInX = (Integer.parseInt(grid.split(";")[0].split(",")[0]));
		int numberInY = (Integer.parseInt(grid.split(";")[0].split(",")[1]));
		String totalHealth = grid.split(";")[4];
		int submarineX = Integer.parseInt(grid.split(";")[2].split(",")[0]);
		int submarineY = Integer.parseInt(grid.split(";")[2].split(",")[1]);
		int ethanX = Integer.parseInt(grid.split(";")[1].split(",")[0]);
		int ethanY = Integer.parseInt(grid.split(";")[1].split(",")[1]);
		String memberLocations = grid.split(";")[3];
		List<String> gridArray = Arrays.asList(memberLocations.replace(";", ",").split("(?<!\\G\\d+),"));
		panel = new JPanel(new GridLayout(numberInX, numberInY));
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
				} else if (gridArray.contains(i + "," + j)) {
//					label.setBackground(Color.red);
					label.setText("imf");
				} else {
//					label.setBackground(Color.black);
				}
				labels.add(label);
			}
		}
		Object[] returned = { panel, grid, output };
		return returned;
	}

	public static void layout(String grid, String output) {
		JFrame frame = new JFrame("Grid Layout");
		String newGrid = grid;
		for (int i = 0; i < output.split(",").length; i++) {
			JPanel panel = new JPanel();
			frame.add((JPanel) drawPanel(newGrid, output, panel)[0]);
			frame.setSize(1000, 1000);
			frame.setVisible(true);
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			System.out.println(output.split(",")[i]);
			if (output.split(",")[i].equals("Up")) {
				String tmp = newGrid.split(";")[0];
				tmp += ";";
				tmp += (Integer.parseInt(newGrid.split(";")[1].split(",")[0]) - 1) + "";
				tmp += ",";
				tmp += newGrid.split(";")[1].split(",")[1];
				tmp += ";";
				tmp += newGrid.split(";")[2];
				tmp += ";";
				if (!newGrid.split(";")[3].equals("")) {
					tmp += newGrid.split(";")[3];
					tmp += ";";
				}
				tmp += newGrid.split(";")[4];
				if (newGrid.split(";").length == 6) {
					tmp += ";";
					tmp += newGrid.split(";")[5];
				}
				System.out.println(tmp);
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("Down")) {
				String tmp = newGrid.split(";")[0];
				tmp += ";";
				tmp += (Integer.parseInt(newGrid.split(";")[1].split(",")[0]) + 1) + "";
				tmp += ",";
				tmp += newGrid.split(";")[1].split(",")[1];
				tmp += ";";
				tmp += newGrid.split(";")[2];
				tmp += ";";
				if (!newGrid.split(";")[3].equals("")) {
					tmp += newGrid.split(";")[3];
					tmp += ";";
				}
				tmp += newGrid.split(";")[4];
				if (newGrid.split(";").length == 6) {
					tmp += ";";
					tmp += newGrid.split(";")[5];
				}
				System.out.println(tmp);
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("Right")) {
				String tmp = newGrid.split(";")[0];
				tmp += ";";
				tmp += newGrid.split(";")[1].split(",")[0];
				tmp += ",";
				tmp += (Integer.parseInt(newGrid.split(";")[1].split(",")[1]) + 1) + "";
				tmp += ";";
				tmp += newGrid.split(";")[2];
				tmp += ";";
				if (!newGrid.split(";")[3].equals("")) {
					tmp += newGrid.split(";")[3];
					tmp += ";";
				}
				tmp += newGrid.split(";")[4];
				if (newGrid.split(";").length == 6) {
					tmp += ";";
					tmp += newGrid.split(";")[5];
				}
				System.out.println(tmp);
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("Left")) {
				String tmp = newGrid.split(";")[0];
				tmp += ";";
				tmp += newGrid.split(";")[1].split(",")[0];
				tmp += ",";
				tmp += (Integer.parseInt(newGrid.split(";")[1].split(",")[1]) - 1) + "";
				tmp += ";";
				tmp += newGrid.split(";")[2];
				tmp += ";";
				if (!newGrid.split(";")[3].equals("")) {
					tmp += newGrid.split(";")[3];
					tmp += ";";
				}
				tmp += newGrid.split(";")[4];
				if (newGrid.split(";").length == 6) {
					tmp += ";";
					tmp += newGrid.split(";")[5];
				}
				System.out.println(tmp);
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("Carry")) {
				String tmp = newGrid.split(";")[0];
				tmp += ";";
				tmp += newGrid.split(";")[1].split(",")[0];
				tmp += ",";
				tmp += newGrid.split(";")[1].split(",")[1];
				tmp += ";";
				tmp += newGrid.split(";")[2];
				tmp += ";";
				String tmpNotCarried = "";
				for (int j = 0; j < newGrid.split(";")[3].split("(?<!\\G\\d+),").length; j++) {
					if (!newGrid.split(";")[3].split("(?<!\\G\\d+),")[j].equals(newGrid.split(";")[1])) {
						tmpNotCarried += newGrid.split(";")[3].split("(?<!\\G\\d+),")[j];
						if (j != newGrid.split(";")[3].split("(?<!\\G\\d+),").length - 1) {
							tmpNotCarried += ",";
						}
					}

				}
				tmp += tmpNotCarried;
				tmp += ";";
				tmp += newGrid.split(";")[4];
				if (newGrid.split(";").length == 6) {
					tmp += ";";
					tmp += newGrid.split(";")[5];
				}
				newGrid = tmp;
			}
		}

	}

	public static void main(String args[]) {
		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
		String output = "Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Down,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Up,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Down,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Up,Carry,Right,Right,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Up,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Up,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Left,Carry,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Down,Drop";
		layout(grid, output);
	}
}
