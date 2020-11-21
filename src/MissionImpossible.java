import java.util.*;
import java.util.Arrays;
import java.util.List;

public class MissionImpossible extends SearchProblem {
	static List<String> pickedIMF;

	public MissionImpossible() {
		pickedIMF = new ArrayList<String>();
	}

	public static String genGrid() {
		String outputGrid = "";
		// Axis initialization between 5 and 15 were x is the vertical and y is the
		// horizontal
		// and adding the axes to the output string as m,n indexed (0,2)
		int xAxis = (int) (Math.random() * 11) + 5;
		int yAxis = (int) (Math.random() * 11) + 5;
		outputGrid += xAxis + "," + yAxis + ";";

		// Adding Ethan's location ranging between the x and y axes
		// to the output string as ex, ey indexed (4,6)
		int ethanXlocation = (int) (Math.random() * xAxis);
		int ethanYlocation = (int) (Math.random() * yAxis);
		outputGrid += ethanXlocation + "," + ethanYlocation + ";";

		// Adding the submarine's location ranging between the x and y axes excluding
		// Ethan's location
		// to the output string as sx,sy indexed (4,6)
		while (true) {
			int submarineXlocation = (int) (Math.random() * xAxis);
			int submarineYlocation = (int) (Math.random() * yAxis);
			if ((submarineXlocation != ethanXlocation) || (submarineYlocation != ethanYlocation)) {
				outputGrid += submarineXlocation + "," + submarineYlocation + ";";
				break;
			}
		}

		// Initialize members number between 5 and 10
		int imfMembers = (int) (Math.random() * 6) + 5;
		// Initialize health for each member
		String health = "";
		// Generate positions of IMFmembers randomly
		for (int i = 0; i < imfMembers; i++) {
			while (true) {
				// Check if the new position of the member is empty before adding it
				int newMemberXlocation = (int) (Math.random() * (xAxis + 1));
				int newMemberYlocation = (int) (Math.random() * (yAxis + 1));
				String checkMemberPosition = newMemberXlocation + "," + newMemberYlocation;
				List<String> gridArray = Arrays.asList(outputGrid.replace(";", ",").split("(?<!\\G\\d+),"));
				if (!gridArray.contains(checkMemberPosition)) {
					outputGrid += newMemberXlocation + "," + newMemberYlocation;
					health += (int) (Math.random() * 99) + 1;
					if (i == imfMembers - 1) {
						health += ";";
					} else {
						outputGrid += ",";
						health += ",";
					}
					break;
				}
			}
		}
		outputGrid += ";" + health;

		int truckCarry = (int) (Math.random() * (imfMembers) + 1);
		outputGrid += truckCarry;
		return outputGrid;
	}

	public static int heuristicFunction(SearchTreeNode conState, List<String> imfMembers, String submarine, String operator) {
		int heurNumber = 0;
		if(!imfMembers.get(0).equals("")) {
			for (int i = 0; i < imfMembers.size(); i++) {
				// SUM | ethX - imfX | + | ethY - imfY |
				heurNumber += Math
						.abs((Integer.parseInt(conState.getState()[0]) - Integer.parseInt(imfMembers.get(i).split(",")[0])))
						+ Math.abs((Integer.parseInt(conState.getState()[1])
								- Integer.parseInt(imfMembers.get(i).split(",")[1])));
			}
		}
		
		heurNumber += Math.abs((Integer.parseInt(conState.getState()[0]) - Integer.parseInt(submarine.split(",")[0])))
				+ Math.abs((Integer.parseInt(conState.getState()[1]) - Integer.parseInt(submarine.split(",")[1])));
		// Adding at the end the distance to the submarine
		// The best case is that the number of IMF members and the distance to the
		// submarine is zero
		if(operator != null && operator.equals("Carry")) {
			heurNumber -= 1;
		}
		return heurNumber;
	}

	public static ArrayList<SearchTreeNode> stateTransition(SearchTreeNode state, String grid) {
		ArrayList<SearchTreeNode> stateSpace = new ArrayList<SearchTreeNode>();
		String[] splittedGrid = grid.split(";");
		String[] parentState = state.getState();

		boolean isTop = isTop(parentState[0]);
		boolean isLeft = isLeft(parentState[1]);
		boolean isDown = isDown((Integer.parseInt(splittedGrid[0].split(",")[0]) - 1) + "", parentState[0]);
		boolean isRight = isRight((Integer.parseInt(splittedGrid[0].split(",")[1]) - 1) + "", parentState[1]);

		int parentDepth = state.getDepth();
		int costToRoot = state.getCostToRoot();
		String newHealth = "";
		for (int i = 0; i < state.getState()[4].split(",").length; i++) {
			if (i == state.getState()[4].split(",").length - 1) {
				if ((Integer.parseInt(state.getState()[4].split(",")[i]) - 2) < 0) {
					newHealth += "0";
				} else {
					newHealth += (Integer.parseInt(state.getState()[4].split(",")[i]) - 2);
				}
			} else {
				if ((Integer.parseInt(state.getState()[4].split(",")[i]) - 2) < 0) {
					newHealth += "0" + ",";
				} else {
					newHealth += (Integer.parseInt(state.getState()[4].split(",")[i]) - 2) + ",";
				}
			}
		}
		String imfMembersLocations = state.getState()[5];
//		List<String> imfMembers  = Arrays.asList(imfMembersLocations.split("(?<!\\G\\d+),"));
//		List<String> imfMembers  = Arrays.asList("");
		List<String> imfMembers  = new ArrayList<String>();

		if (imfMembersLocations.length() > 0) {
			imfMembers  = Arrays.asList(imfMembersLocations.split("(?<!\\G\\d+),"));
		}
		else {
			imfMembers  = Arrays.asList("");

		}


		if (!isTop) { // Creating up state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[6];
			newState[0] = (ethX - 1) + "";
			newState[1] = ethY + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			SearchTreeNode up = new SearchTreeNode(newState, state, "Up", newDepth, costToRoot, 0);
			int heuristicValue = heuristicFunction(up, imfMembers, grid.split(";")[2],up.getOperator());
			up.setHeuristicValue(heuristicValue);
			stateSpace.add(up);
		}
		if (!isLeft) { // Creating left state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[6];
			newState[0] = ethX + "";
			newState[1] = (ethY - 1) + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			SearchTreeNode left = new SearchTreeNode(newState, state, "Left", newDepth, costToRoot, 0);
			int heuristicValue = heuristicFunction(left, imfMembers, grid.split(";")[2],left.getOperator());
			left.setHeuristicValue(heuristicValue);
			stateSpace.add(left);
		}
		if (!isDown) { // Creating down state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[6];
			newState[0] = (ethX + 1) + "";
			newState[1] = ethY + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			SearchTreeNode down = new SearchTreeNode(newState, state, "Down", newDepth, costToRoot, 0);
			int heuristicValue = heuristicFunction(down, imfMembers, grid.split(";")[2], down.getOperator());
			down.setHeuristicValue(heuristicValue);
			stateSpace.add(down);
		}
		if (!isRight) { // Creating right state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[6];
			newState[0] = ethX + "";
			newState[1] = (ethY + 1) + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			SearchTreeNode right = new SearchTreeNode(newState, state, "Right", newDepth, costToRoot, 0);
			int heuristicValue = heuristicFunction(right, imfMembers, grid.split(";")[2], right.getOperator());
			right.setHeuristicValue(heuristicValue);
			stateSpace.add(right);
		}

//		List<String> gridArray = Arrays.asList(grid.split(";")[3].split("(?<!\\G\\d+),"));
//		int posIMF = 0;
//		String posEthanAndIMF = parentState[0] + "," + parentState[1];
//		while (posIMF < gridArray.size()) {
//			System.out.println((gridArray.get(posIMF).equals(posEthanAndIMF)
//					&& (Integer.parseInt(parentState[3]) < Integer.parseInt(grid.split(";")[5]))
//					&& (Integer.parseInt(parentState[2]) > 0) && !pickedIMF.contains(gridArray.get(posIMF))));
//			System.out.println("nafs el pos : "+(gridArray.get(posIMF).equals(posEthanAndIMF)));
//			System.out.println("a2al mn el carry : " + (Integer.parseInt(parentState[3]) < Integer.parseInt(grid.split(";")[5])));
//			System.out.println("3addad el IMF akbar mn zero : " + (Integer.parseInt(parentState[2]) > 0));
//			System.out.println("ma3adaash 3l IMF : " + pickedIMF.contains(gridArray.get(posIMF)));
//			System.out.println("el picked members locations : ");
//			for(int i =0;i<pickedIMF.size();i++) {
//				System.out.println(pickedIMF.get(i));
//			}
//
//			System.out.println();
//			if (gridArray.get(posIMF).equals(posEthanAndIMF)
//					&& (Integer.parseInt(parentState[3]) < Integer.parseInt(grid.split(";")[5]))
//					&& (Integer.parseInt(parentState[2]) > 0) && !pickedIMF.contains(gridArray.get(posIMF))) { // Creating
//																												// carry
//																												// state
//				System.out.println("da5al ahhh");
//				pickedIMF.add(gridArray.get(posIMF));
//				int ethX = Integer.parseInt(parentState[0]);
//				int ethY = Integer.parseInt(parentState[1]);
//				String remainingIMF = parentState[2];
//				String noOfcarry = parentState[3];
//				int newDepth = parentDepth + 1;
//				String[] newState = new String[5];
//				newState[0] = ethX + "";
//				newState[1] = ethY + "";
//				int newRemainingIMF = Integer.parseInt(remainingIMF) - 1;
//				newState[2] = newRemainingIMF + "";
//				int newNoOfCarry = Integer.parseInt(noOfcarry) + 1;
//				newState[3] = newNoOfCarry + "";
//				newState[4] = newHealth;
//				SearchTreeNode carry = new SearchTreeNode(newState, state, "Carry", newDepth, costToRoot,0);
//				int heuristicValue = heuristicFunction(carry, imfMembers, grid.split(";")[2]);
//				carry.setHeuristicValue(heuristicValue);
//				stateSpace.add(carry);
//			}
//
//			posIMF++;
//		}

		String[] prevMembersLocations = state.getState()[5].split("(?<!\\G\\d+),");
		String posEthanAndIMF = parentState[0] + "," + parentState[1];

		String outputMembers = "";
		boolean memberExist = false;
		for (int j = 0; j < prevMembersLocations.length; j++) {
			if ((prevMembersLocations[j]).equals(posEthanAndIMF)) {
				System.out.println("INN A2rab wa7ed " + prevMembersLocations[j]);
				memberExist = true;
			} else {
				if (j == 0 || outputMembers.equals("")) { // || outputMembers.equals("") Important Extra !!
					outputMembers = prevMembersLocations[j];
				} else {
					outputMembers = outputMembers + "," + prevMembersLocations[j];
				}

			}
		}

		if (memberExist && (Integer.parseInt(parentState[3]) < Integer.parseInt(grid.split(";")[5]))
				&& (Integer.parseInt(parentState[2]) > 0)) { // Creating carry state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[6];
			newState[0] = ethX + "";
			newState[1] = ethY + "";
			int newRemainingIMF = Integer.parseInt(remainingIMF) - 1;
			newState[2] = newRemainingIMF + "";
			int newNoOfCarry = Integer.parseInt(noOfcarry) + 1;
			newState[3] = newNoOfCarry + "";
			newState[4] = newHealth;
			newState[5] = outputMembers;
			System.out.println(outputMembers + "remaining membersss cuz same position");
			List<String> carryIMFMembers = Arrays.asList(outputMembers.split("(?<!\\G\\d+),"));
			SearchTreeNode carry = new SearchTreeNode(newState, state, "Carry", newDepth, costToRoot, 0);
			int heuristicValue = heuristicFunction(carry, carryIMFMembers, grid.split(";")[2], carry.getOperator());
			carry.setHeuristicValue(heuristicValue);
			stateSpace.add(carry);
		}

		if (splittedGrid[2].equals(posEthanAndIMF) && (Integer.parseInt(parentState[3]) > 0)) { // Creating drop state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[6];
			newState[0] = ethX + "";
			newState[1] = ethY + "";
			newState[2] = remainingIMF;
			int newNoOfCarry = Integer.parseInt(noOfcarry) - 1;
			newState[3] = newNoOfCarry + "";
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;

			SearchTreeNode drop = new SearchTreeNode(newState, state, "Drop", newDepth, costToRoot, 0);
			int heuristicValue = heuristicFunction(drop, imfMembers, grid.split(";")[2], drop.getOperator());
			drop.setHeuristicValue(heuristicValue);
			stateSpace.add(drop);
		}
		return stateSpace;

	}

	public static boolean isTop(String ethanPos) {
		if (ethanPos.charAt(0) == '0') {
			return true;
		}
		return false;
	}

	public static boolean isDown(String maxGrid, String ethanPos) {
		if (ethanPos.charAt(0) == maxGrid.charAt(0)) {

			return true;
		}
		return false;
	}

	public static boolean isLeft(String ethanPos) {
		if (ethanPos.charAt(0) == '0') {
			return true;
		}
		return false;
	}

	public static boolean isRight(String maxGrid, String ethanPos) {
		if (ethanPos.charAt(0) == maxGrid.charAt(0)) {
			return true;
		}
		return false;
	}

	public static String solve(String grid, String strategy, MissionImpossible m) {
		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0", "0,0" };
		String[] members = grid.split(";")[3].split(",");
		int membersNum = members.length / 2;
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth };
		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0);
		if (strategy == "DFS") {
			return DFS(init, grid, goal, 100000, m);
		}
		if (strategy == "BFS") {
			return BFS(init, grid, goal, m);
		}
		if (strategy == "ID") {
			return ID(init, grid, goal, 1000);
		}
		if (strategy == "UCS") {
			// Call UCS
		}
		if (strategy == "A*") {
			// Call A*
		}
		if (strategy == "greedy") {
			// Call greedy
		}
		return "Please enter a correct strategy";
	}

	public static void main(String[] args) {
//		MissionImpossible m = new MissionImpossible();
//		String solved = m.solve(m.genGrid(), "ID", m);
//		System.out.println(solved);
		gui.main(null);
	}
}
