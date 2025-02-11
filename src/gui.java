import java.awt.Color;
import java.awt.GridLayout;

import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.net.URL;

public class gui {

	public gui(String grid, String output) {
		layout(grid, output);
	}

	public Object[] drawPanel(String grid, String output, JPanel panel) {
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
				Border blackBorder = BorderFactory.createLineBorder(Color.black);
				label = new JLabel();
				panel.add(label);
				label.setOpaque(true);
				label.setBorder(blackBorder);
				label.setBackground(Color.green);
				if (ethanX == i && ethanY == j) {
					ImageIcon img = new ImageIcon(this.getClass().getResource("soldier.png"));
					label.setIcon(img);
				} else if (submarineX == i && submarineY == j) {
					ImageIcon img = new ImageIcon(this.getClass().getResource("submarine.png"));
					label.setIcon(img);
				} else if (gridArray.contains(i + "," + j)) {
					ImageIcon img = new ImageIcon(this.getClass().getResource("soldier (1).png"));
					label.setIcon(img);
				}
				labels.add(label);
			}
		}
		Object[] returned = { panel, grid, output };
		return returned;
	}

	public void layout(String grid, String output) {

		try {
			URL url = this.getClass().getResource("alo_eh.wav");
			Clip clip = AudioSystem.getClip();
			// getAudioInputStream() also accepts a File or InputStream
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("Grid Layout");
		String newGrid = grid;
		for (int i = 0; i < output.split(",").length; i++) {
			JPanel panel = new JPanel();
			frame.add((JPanel) this.drawPanel(newGrid, output, panel)[0]);
			frame.getContentPane().setBackground(Color.BLACK);
			frame.setSize(1000, 1000);
			frame.setVisible(true);
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (output.split(",")[i].equals("up")) {
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
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("down")) {
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
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("right")) {
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
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("left")) {
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
				newGrid = tmp;
			}
			if (output.split(",")[i].equals("carry")) {
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

}
