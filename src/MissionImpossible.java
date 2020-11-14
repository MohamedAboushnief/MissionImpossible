import java.util.*;
import java.util.Arrays;
import java.util.List;

public class MissionImpossible extends SearchProblem {
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
				List<String> gridArray = Arrays.asList(outputGrid.replace(";",",").split("(?<!\\G\\d+),"));
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
		
		int truckCarry = (int) (Math.random() * (imfMembers + 1));
		outputGrid += truckCarry;
		return outputGrid;
	}
	
	public static ArrayList<SearchTreeNode> stateTransition(SearchTreeNode state, String grid) {
		ArrayList<SearchTreeNode> stateSpace = new ArrayList<SearchTreeNode>();
		String[] splittedGrid = grid.split(";");
		boolean isTop = isTop(splittedGrid[1]);
		boolean isLeft = isLeft(splittedGrid[1]);
		boolean isDown = isDown(splittedGrid[0],splittedGrid[1]);
		boolean isRight = isRight(splittedGrid[0],splittedGrid[1]);

		String[] parentState = state.getState();
		int parentDepth = state.getDepth();
		int costToRoot = state.getCostToRoot();

		if(!isTop) {   // Creating up state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[4];
			newState[0] = (ethX - 1) + "";
			newState[1] = ethY  + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			SearchTreeNode up = new SearchTreeNode(newState,state,"Up",newDepth,costToRoot);
			stateSpace.add(up);
		}
		if(!isLeft) {   // Creating left state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[4];
			newState[0] = ethX + "";
			newState[1] = (ethY - 1)  + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			SearchTreeNode left = new SearchTreeNode(newState,state,"Left",newDepth,costToRoot);
			stateSpace.add(left);
		}
		if(!isDown) {  // Creating down state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[4];
			newState[0] = (ethX + 1) + "";
			newState[1] = ethY   + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			SearchTreeNode down = new SearchTreeNode(newState,state,"Down",newDepth,costToRoot);
			stateSpace.add(down);		
		}
		if(!isRight) { // Creating right state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[4];
			newState[0] = ethX + "";
			newState[1] = (ethY + 1)  + "";
			newState[2] = remainingIMF;
			newState[3] = noOfcarry;
			SearchTreeNode right = new SearchTreeNode(newState,state,"Right",newDepth,costToRoot);
			stateSpace.add(right);
		}
		
		List<String> gridArray = Arrays.asList(grid.replace(";",",").split("(?<!\\G\\d+),"));
		int posIMF = 3;
		String posEthanAndIMF = "(" + parentState[0] + "," + parentState[1] + ")";
		while(posIMF < gridArray.size()) {
			if(gridArray.get(posIMF).equals(posEthanAndIMF)) { // Creating carry state
				int ethX = Integer.parseInt(parentState[0]);
				int ethY = Integer.parseInt(parentState[1]);
				String remainingIMF = parentState[2];
				String noOfcarry = parentState[3];
				int newDepth = parentDepth + 1;
				String[] newState = new String[4];
				newState[0] = ethX + "";
				newState[1] = ethY + "";
				int newRemainingIMF = Integer.parseInt(remainingIMF) - 1;
				newState[2] = newRemainingIMF + "";
				int newNoOfCarry = Integer.parseInt(noOfcarry) + 1;
				newState[3] = newNoOfCarry + "";
				SearchTreeNode carry = new SearchTreeNode(newState,state,"Carry",newDepth,costToRoot);
				stateSpace.add(carry);
			}
			
			posIMF++;
		}
		
		if(gridArray.get(2).equals(posEthanAndIMF)) { // Creating drop state
			int ethX = Integer.parseInt(parentState[0]);
			int ethY = Integer.parseInt(parentState[1]);
			String remainingIMF = parentState[2];
			String noOfcarry = parentState[3];
			int newDepth = parentDepth + 1;
			String[] newState = new String[4];
			newState[0] = ethX + "";
			newState[1] = ethY + "";
			newState[2] = remainingIMF;
			int newNoOfCarry = Integer.parseInt(noOfcarry) - 1;
			newState[3] = newNoOfCarry + "";
			SearchTreeNode drop = new SearchTreeNode(newState,state,"Drop",newDepth,costToRoot);
			stateSpace.add(drop);
		}
		return stateSpace;
		
		

	}
	public static boolean isTop(String ethanPos) {
		if(ethanPos.charAt(0) == '0') {
			return true;
		}
		return false;
	}
	public static boolean isDown(String maxGrid, String ethanPos) {
		if(ethanPos.charAt(0) == maxGrid.charAt(0)) {
			return true;
		}
		return false;
	}
	public static boolean isLeft(String ethanPos) {
		if(ethanPos.charAt(2) == '0') {
			return true;
		}
		return false;
	}
	public static boolean isRight(String maxGrid, String ethanPos) {
		if(ethanPos.charAt(2) == maxGrid.charAt(0)) {
			return true;
		}
		return false;
	}
//	public static void solve(String strategy) {
//		String grid = genGrid();
//		String[] gridParsed = grid.split(";");
//		String intialState = gridParsed[0] + gridParsed[1];
//		if(strategy == "DFS") {
//			SearchProblem.DFS(intialState, grid, goalState)
//		}
//	}
	public static void main(String[]args) {
		
		String test = genGrid();
		System.out.println(test);
		
	}
}
