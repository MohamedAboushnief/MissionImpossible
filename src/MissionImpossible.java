import java.util.*;
import java.util.Arrays;
import java.util.List;

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
		int totalHealth=0;
		String[] healthOfMembersNew = Health.split(",");
		for (String memberHealth : healthOfMembersNew) {
			int s = Integer.parseInt(memberHealth);
			if (s == 0) {
				death++;
			}
			totalHealth++;
		}
		
		int ethx=Integer.parseInt(currentNode.getState()[0]);
		int ethy=Integer.parseInt(currentNode.getState()[1]);
		
		String[] MembersLocations = currentNode.getState()[5].split("(?<!\\G\\d+),");
		int sumOfdistance=0;
//		for(int i=0;i< MembersLocations.length;i++) {
//			sumOfdistance= sumOfdistance+ (Math.abs((ethx-Integer.parseInt(MembersLocations[i].split(",")[0])))+ Math.abs((ethy-Integer.parseInt(MembersLocations[i].split(",")[1]))));
//			
//		}
		
		
		
		
//		int parentNodesCost = getParentsCost(currentNode);
		
		
//		int cost = healthOfMembersNew.length*2 + ((death) * 10000)+ sumOfdistance*totalHealth ;
		int cost = healthOfMembersNew.length*2 + ((death) * 100000) ;
		
		return cost;
		}
		return 0;
	}

	public static int getParentsCost(SearchTreeNode Node) {
		int cost = 0;

		if (Node.getCostToRoot() == 0) {
			return 0;
		} else {
			return getParentsCost(Node.getParentNode()) + Node.getCostToRoot();
		}

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
//		String Health = state.getState()[4];

		String imfMembersLocations = state.getState()[5];
//		List<String> imfMembers  = Arrays.asList(imfMembersLocations.split("(?<!\\G\\d+),"));
//		List<String> imfMembers  = Arrays.asList("");
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

		if (!isTop) { // Creating up state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[7];
			newState[0] = (ethX - 1) + "";
			newState[1] = ethY + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode up = new SearchTreeNode(newState, state, "Up", newDepth, newCostToRoot, 0);
			stateSpace.add(up);
		}
		if (!isLeft) { // Creating left state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[7];
			newState[0] = ethX + "";
			newState[1] = (ethY - 1) + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode left = new SearchTreeNode(newState, state, "Left", newDepth, newCostToRoot, 0);
			stateSpace.add(left);
		}
		if (!isDown) { // Creating down state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[7];
			newState[0] = (ethX + 1) + "";
			newState[1] = ethY + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode down = new SearchTreeNode(newState, state, "Down", newDepth, newCostToRoot, 0);
			stateSpace.add(down);
		}
		if (!isRight) { // Creating right state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[7];
			newState[0] = ethX + "";
			newState[1] = (ethY + 1) + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			newState[4] = newHealth;
			newState[5] = imfMembersLocations;
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode right = new SearchTreeNode(newState, state, "Right", newDepth, newCostToRoot, 0);
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
						if (k == newHealth.split(",").length - 1 || afterCarryHealth.equals("")) {
							afterCarryHealth += newHealth.split(",")[k]+ ",";
						} else {

							afterCarryHealth += newHealth.split(",")[k] + ",";
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
//			System.out.println(outputMembers + "remaining membersss cuz same position");
			List<String> carryIMFMembers = Arrays.asList(outputMembers.split("(?<!\\G\\d+),"));
			int newCostToRoot = GetCost(state, newHealth) + costToRoot;
			SearchTreeNode carry = new SearchTreeNode(newState, state, "Carry", newDepth, newCostToRoot, 0);
//			int heuristicValue = heuristicFunction(carry, carryIMFMembers, grid.split(";")[2], carry.getOperator());
//			carry.setHeuristicValue(heuristicValue);
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
			SearchTreeNode drop = new SearchTreeNode(newState, state, "Drop", newDepth, newCostToRoot, 0);
//			int heuristicValue = heuristicFunction(drop, imfMembers, grid.split(";")[2], drop.getOperator());
//			drop.setHeuristicValue(heuristicValue);
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
