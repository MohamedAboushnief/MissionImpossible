import java.util.*;
import java.util.Arrays;
import java.util.List;

//import com.sun.tools.classfile.ConstantPool.CONSTANT_String_info;

public class MissionImpossible extends SearchProblem {
	static List<Integer> pickedIMF = new ArrayList<Integer>();

	public MissionImpossible() {

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

	// calculates cost
	public static int GetCost(SearchTreeNode currentNode, String Health) {

		if (!Health.equals("")) {

			// calculate sum of new health
			int death = 0;
			int totalHealth = 0;
			String[] healthOfMembersNew = Health.split(",");
			for (String memberHealth : healthOfMembersNew) {
				int s = Integer.parseInt(memberHealth);
				if (s == 0) {
					death++;
				}
				totalHealth++;
			}

			int cost = healthOfMembersNew.length * 2 + ((death) * 100000);

			return cost;
		}
		return 0;
	}

	public static int heuristicFunction(SearchTreeNode conState, List<String> imfMembers, String submarine,
			String operator) { // first heuristic returns the cheapest path to all IMF members remaining and
								// the cheapest path to the submarine
		int heurNumber = 0;
		if (!imfMembers.get(0).equals("")) {
			for (int i = 0; i < imfMembers.size(); i++) {
				// SUM | ethX - imfX | + | ethY - imfY |
				heurNumber += Math.abs(
						(Integer.parseInt(conState.getState()[0]) - Integer.parseInt(imfMembers.get(i).split(",")[0])))
						+ Math.abs((Integer.parseInt(conState.getState()[1])
								- Integer.parseInt(imfMembers.get(i).split(",")[1])));
			}

		}

		heurNumber += Math.abs((Integer.parseInt(conState.getState()[0]) - Integer.parseInt(submarine.split(",")[0])))
				+ Math.abs((Integer.parseInt(conState.getState()[1]) - Integer.parseInt(submarine.split(",")[1])));

		// Adding at the end the distance to the submarine and adding number of carry
		// The best case is that the number of IMF members and the distance to the
		// submarine is zero
		
		if (heurNumber == 0) {  // to let the goal state only have the zero heuristic value 
			heurNumber += 1;
		}

		return heurNumber;
	}

	public static int heuristicFunction_2(SearchTreeNode conState, List<String> imfMembers,
			String[] healthsOfImfMembers, String operator) { // second heuristic returns the cheapest path the IMF
																// member with the
		// least health

		// SUM | ethX - imfX | + | ethY - imfY | where imf member has the least health
		int heurNumber = 0;
		int index = 0;
		if (!imfMembers.get(0).equals("") && !healthsOfImfMembers[0].equals("")) { // getting the minimum imf health 
			int minhealth = Integer.parseInt(healthsOfImfMembers[0]);
			for (int i = 0; i < healthsOfImfMembers.length; i++) {
				if (minhealth > Integer.parseInt(healthsOfImfMembers[i])) {
					minhealth = Integer.parseInt(healthsOfImfMembers[i]);
					index = i;
				}
			}
			heurNumber = Math.abs(
					(Integer.parseInt(conState.getState()[0]) - Integer.parseInt(imfMembers.get(index).split(",")[0])))
					+ Math.abs((Integer.parseInt(conState.getState()[1])
							- Integer.parseInt(imfMembers.get(index).split(",")[1])));

		}
		if (heurNumber == 0) { // to let the goal state only have the zero heuristic value
			heurNumber += 1;
		}

		return heurNumber;
	}

	public static ArrayList<SearchTreeNode> stateTransition(SearchTreeNode state, String grid, int heuristicFlag) {
		ArrayList<SearchTreeNode> stateSpace = new ArrayList<SearchTreeNode>();
		String[] splittedGrid = grid.split(";");
		String[] parentState = state.getState();

		boolean isTop = isTop(parentState[0]);
		boolean isLeft = isLeft(parentState[1]);
		boolean isDown = isDown((Integer.parseInt(splittedGrid[0].split(",")[0]) - 1) + "", parentState[0]);
		boolean isRight = isRight((Integer.parseInt(splittedGrid[0].split(",")[1]) - 1) + "", parentState[1]);

		int parentDepth = state.getDepth();
		int costToRoot = state.getCostToRoot();

		String imfMembersLocations = state.getState()[5];

		List<String> imfMembers = new ArrayList<String>();

		if (imfMembersLocations.length() > 0) {
			imfMembers = Arrays.asList(imfMembersLocations.split("(?<!\\G\\d+),"));
		} else {
			imfMembers = Arrays.asList("");

		}

		String newHealth = "";
		if (!state.getState()[4].equals("")) {
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
		}

		if (imfMembersLocations.length() > 0) {
			imfMembers = Arrays.asList(imfMembersLocations.split("(?<!\\G\\d+),"));
		} else {
			imfMembers = Arrays.asList("");

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
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode up = new SearchTreeNode(newState, state, "up", newDepth, newCostToRoot, 0, state.getStrategyName());
			int heuristicValue = 0;
			if(heuristicFlag == 1) {
				heuristicValue = heuristicFunction(up, imfMembers, grid.split(";")[2], up.getOperator());
			}
			else if(heuristicFlag == 2) {
				heuristicValue = heuristicFunction_2(up, imfMembers, newHealth.split(","), up.getOperator());
			}

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
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode left = new SearchTreeNode(newState, state, "left", newDepth, newCostToRoot, 0, state.getStrategyName());
	

			int heuristicValue = 0;
			if(heuristicFlag == 1) {
				heuristicValue = heuristicFunction(left, imfMembers, grid.split(";")[2], left.getOperator());
			}
			else if(heuristicFlag == 2){
				heuristicValue = heuristicFunction_2(left, imfMembers, newHealth.split(","), left.getOperator());
			}

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
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode down = new SearchTreeNode(newState, state, "down", newDepth, newCostToRoot, 0, state.getStrategyName());
			int heuristicValue = 0;
			if(heuristicFlag == 1) {
				heuristicValue = heuristicFunction(down, imfMembers, grid.split(";")[2], down.getOperator());
			}
			else if(heuristicFlag == 2){
				heuristicValue = heuristicFunction_2(down, imfMembers, newHealth.split(","), down.getOperator());
			}

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
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode right = new SearchTreeNode(newState, state, "right", newDepth, newCostToRoot, 0, state.getStrategyName());
			int heuristicValue = 0;
			if(heuristicFlag == 1) {
				heuristicValue = heuristicFunction(right, imfMembers, grid.split(";")[2], right.getOperator());
			}
			else if(heuristicFlag == 2){
				heuristicValue = heuristicFunction_2(right, imfMembers, newHealth.split(","), right.getOperator());
			}

			right.setHeuristicValue(heuristicValue);
			stateSpace.add(right);
		}



		String[] prevMembersLocations = state.getState()[5].split("(?<!\\G\\d+),");
		String posEthanAndIMF = parentState[0] + "," + parentState[1];

		String outputMembers = "";
		String afterCarryHealth = "";

		boolean memberExist = false;
		for (int j = 0; j < prevMembersLocations.length; j++) {
			if ((prevMembersLocations[j]).equals(posEthanAndIMF)) {
				memberExist = true;
				for (int k = 0; k < newHealth.split(",").length; k++) {
					if (j != k) {
						if (afterCarryHealth.length() == 0) {
							afterCarryHealth += newHealth.split(",")[k];
						}

						else {

							afterCarryHealth += "," + newHealth.split(",")[k];
						}
					}
				}
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
			newState[4] = afterCarryHealth;
			newState[5] = outputMembers;
			List<String> carryIMFMembers = Arrays.asList(outputMembers.split("(?<!\\G\\d+),"));
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode carry = new SearchTreeNode(newState, state, "carry", newDepth, newCostToRoot, 0, state.getStrategyName());
			int heuristicValue = 0;
			if(heuristicFlag == 1) {
				heuristicValue = heuristicFunction(carry, carryIMFMembers, grid.split(";")[2], carry.getOperator());
			}
			else if(heuristicFlag == 2){
				heuristicValue = heuristicFunction_2(carry, imfMembers, newHealth.split(","), carry.getOperator());
			}
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
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode drop = new SearchTreeNode(newState, state, "drop", newDepth, newCostToRoot, 0 ,state.getStrategyName());
			int heuristicValue = 0;
			if(heuristicFlag == 1) {
				heuristicValue = heuristicFunction(drop, imfMembers, grid.split(";")[2], drop.getOperator());
			}
			else if(heuristicFlag == 2){
				heuristicValue = heuristicFunction_2(drop, imfMembers, newHealth.split(","), drop.getOperator());
			}
			drop.setHeuristicValue(heuristicValue);
			stateSpace.add(drop);
		}

		return stateSpace;

	}

	public static boolean isTop(String ethanPos) {
		if (ethanPos.equals("0")) {
			return true;
		}
		return false;
	}

	public static boolean isDown(String maxGrid, String ethanPos) {
		if (ethanPos.equals(maxGrid)) {
			return true;
		}
		return false;
	}

	public static boolean isLeft(String ethanPos) {
		if (ethanPos.equals("0")) {
			return true;
		}
		return false;
	}

	public static boolean isRight(String maxGrid, String ethanPos) {
		if (ethanPos.equals(maxGrid)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		String test = genGrid();
		System.out.println(test);

	}
}