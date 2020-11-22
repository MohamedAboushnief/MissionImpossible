import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Collections;
import java.util.Hashtable;

public abstract class SearchProblem {

	public static String DFS(SearchTreeNode intialState, String grid, String[] goalState) {
		ArrayList<String[]> ancestors = new ArrayList<String[]>();
		String output = "";
		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.push(intialState);
		int counter = 0;
		while (true) {
//			goalState[4] = searchTreeNodesStack.peek().getState()[4];
			counter++;
			System.out.println(counter + "counter kjbouqeffbbjk");
//			if (counter > 100) {
//				return null;
//			}
			searchTreeNodesStack.peek().getState()[4] = "0,0";
			ancestors.add(searchTreeNodesStack.peek().getState());
			if (searchTreeNodesStack.peek().getOperator() != null) {
				output += searchTreeNodesStack.peek().getOperator();
			}
//			for(int i=0 ; i< searchTreeNodesStack.peek().getState().length; i++) {
//				System.out.println(searchTreeNodesStack.peek().getState()[i]);
//			}
			for (int k = 0; k < searchTreeNodesStack.peek().getState().length; k++) {
				System.out.print(searchTreeNodesStack.peek().getState()[k] + ",");
			}
			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.pop(), grid);
			for (int i = 0; i < x.size(); i++) {
				System.out.println(x.get(i).getOperator() + "operator");
				searchTreeNodesStack.push(x.get(i));
			}
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				System.out.println();
				boolean found = false;
				for (int j = 0; j < ancestors.size(); j++) {
					if (Arrays.equals(y.getState(), ancestors.get(j))) {
//						for(int cc=0 ; cc< searchTreeNodesStack.peek().getState().length; cc++) {
//							System.out.println(y.getState()[i]);
//							System.out.println(ancestors.get(j)[i]);
//							
//						}
						found = true;
						break;
					}
				}
				if (found) {
					System.out.println("here");
					searchTreeNodesStack.pop();
				} else {
					System.out.println("not here");
					if (Arrays.equals(y.getState(), goalState)) {
						output += y.getOperator();
						return output;
					} else {
						break;
					}
				}
			}
		}
	}

	public static String BFSolz(SearchTreeNode initialState, String grid, String[] goal) {
		String result = "";
		Queue<SearchTreeNode> toTraverse = new LinkedList<SearchTreeNode>();
		ArrayList<String> prevStates = new ArrayList<String>();
		SearchTreeNode currentState = initialState;
		int counter = 0;
		while (!Arrays.equals(goal, currentState.getState())) {
//			System.out.println();
//			System.out.println();
			String newState = currentState.getState()[0] + "," + currentState.getState()[1] + ","
					+ currentState.getState()[2] + "," + currentState.getState()[3] + "," + currentState.getState()[4];
			prevStates.add(newState);
			System.out.println("current state  " + newState);
			ArrayList<SearchTreeNode> stateSpace = MissionImpossible.stateTransition(currentState, grid);
			for (int i = 0; i < stateSpace.size(); i++) {
				System.out.println(stateSpace.get(i).getOperator() + "  operator");
			}
			for (int i = 0; i < stateSpace.size(); i++) {
				String tempState = stateSpace.get(i).getState()[0] + "," + stateSpace.get(i).getState()[1] + ","
						+ stateSpace.get(i).getState()[2] + "," + stateSpace.get(i).getState()[3] + ","
						+ stateSpace.get(i).getState()[4];
//				System.out.println(tempState);
				boolean found = false;
//				for(int j = 0;j<prevStates.size();j++) {
//					if(prevStates.get(j).equals(tempState)) {
//						found = true;
////						System.out.println("eltemp state = " + tempState);
//						break;
//					}
//				}
//				
//				if(found) {
//					System.out.println("aaaaa");
//				}
//				else {
				toTraverse.add(stateSpace.get(i));
//				}
			}
//			if(counter>100) {
//				break;
//			}
			try {
				System.out.println(currentState.getDepth());
				currentState = toTraverse.remove();
//				System.out.println("el mesh eltemp" +newState);
			} catch (Exception e) {
				System.out.println("El queue 5elset");
				break;
			}
			counter++;

//			for(int i = 0;i<goal.length;i++) {
//				System.out.print(goal[i] + "    " + currentState.getState()[i]);
//				System.out.println();
//			}
//			goal[4] = currentState.getState()[4];
		}

		System.out.println(counter);
		while (currentState.getParentNode() != null) {
			result = currentState.getOperator() + "," + result;
			currentState = currentState.getParentNode();
		}
		return result;

	}

	public static String BFS(SearchTreeNode intialState, String grid, String[] goalState) {
		ArrayList<String> ancestors = new ArrayList<String>();
		String output = "";
		Queue<SearchTreeNode> searchTreeNodesStack = new LinkedList<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.add(intialState);
		int counter = 0;
		while (true) {
			counter++;
			System.out.println(counter + "counter kjbouqeffbbjk");

			searchTreeNodesStack.peek().getState()[4] = "0,0";
			ancestors.add(searchTreeNodesStack.peek().getState()[0] + "," + searchTreeNodesStack.peek().getState()[1]
					+ "," + searchTreeNodesStack.peek().getState()[2] + ","
					+ searchTreeNodesStack.peek().getState()[3]);

			if (searchTreeNodesStack.peek().getOperator() != null) {
				output += searchTreeNodesStack.peek().getOperator();
			}

			for (int k = 0; k < searchTreeNodesStack.peek().getState().length; k++) {
				System.out.print(searchTreeNodesStack.peek().getState()[k] + ",");
			}
			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.remove(), grid);
			for (int i = 0; i < x.size(); i++) {
				System.out.println(x.get(i).getOperator() + "  operator");
				searchTreeNodesStack.add(x.get(i));
			}
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				System.out.println();
				boolean found = false;
				for (int j = 0; j < ancestors.size(); j++) {

					if ((y.getState()[0] + "," + y.getState()[1] + "," + y.getState()[2] + "," + y.getState()[3])
							.equals(ancestors.get(j))) {

						found = true;
						break;
					}
				}
				if (found) {
					System.out.println("here");
					searchTreeNodesStack.remove();
				} else {
					System.out.println("not here");
					if (Arrays.equals(y.getState(), goalState)) {
						output += y.getOperator();
						return output;
					} else {
						break;
					}
				}
			}
		}
	}

	public static boolean CheckGoal(SearchTreeNode currentNode, String[] goalState) {

		if (currentNode.getState()[0].equals(goalState[0]) && currentNode.getState()[1].equals(goalState[1])
				&& currentNode.getState()[2].equals(goalState[2]) && currentNode.getState()[3].equals(goalState[3])) {
			return true;

		}
		return false;
	}

	public static String getPath(SearchTreeNode Node) {
		String path = "";
		System.out.println("======================================");
		System.out.printf("ethan : %s,%s  \n", Node.getState()[0], Node.getState()[1]);
		System.out.printf("remaining imf : %s  \n", Node.getState()[2]);
		System.out.printf("no of carry : %s  \n", Node.getState()[3]);
		System.out.printf("operator : %s  \n", Node.getOperator());
		System.out.println("overall health is : " + Node.getState()[4]);
		System.out.println("cost to root is : " + Node.getCostToRoot());
		System.out.println("imf locations  : " + Node.getState()[5]);

		System.out.println("======================================");

		if (Node.getParentNode() == null) {
			return "";
		} else {
			return getPath(Node.getParentNode()) + Node.getParentNode().getOperator() + ",";
		}

	}

	public static String stateToString(SearchTreeNode state) {
		String tempState = (state).getState()[0] + "," + (state).getState()[1] + "," + (state).getState()[2] + ","
				+ (state).getState()[3];
		return tempState;
	}

	public static String UCS(SearchTreeNode intialState, String grid, String[] goalState) {

		Hashtable<String, Integer> ancestors2 = new Hashtable<String, Integer>();
		String output = "";
		PriorityQueue<SearchTreeNode> searchTreeNodesStack2 = new PriorityQueue<SearchTreeNode>();

		if (intialState == null) {
			return output;

		}

		searchTreeNodesStack2.add(intialState);
		int counter = 0;
		while (true) {

			SearchTreeNode currentNode = searchTreeNodesStack2.peek();

			ancestors2.put(stateToString(currentNode), 0);

			if (currentNode.getOperator() != null) {
				output += currentNode.getOperator();
			}

			if (CheckGoal(currentNode, goalState)) {
				System.out.println("Reached Goal State !!!!!!!!");
				String path = getPath(currentNode);
				return path;
			}

			ArrayList<SearchTreeNode> stateSpaces = MissionImpossible.stateTransition(searchTreeNodesStack2.remove(),
					grid);

			for (int i = 0; i < stateSpaces.size(); i++) {

				if (!ancestors2.containsKey(stateToString(stateSpaces.get(i)))) {
					searchTreeNodesStack2.add(stateSpaces.get(i));
					ancestors2.put(stateToString(stateSpaces.get(i)), 0);

				}

			}

			counter++;
			System.out.println(counter);

		}

	}

	public static void main(String[] args) {
		MissionImpossible m = new MissionImpossible();
//		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
//		String grid = "5,5;4,3;0,2;3,1,1,2,4,2;60,40,80;3";
//		String grid = "11,11;4,2;0,2;1,2,3,3,8,4;60,70,40;3";
		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
//		String grid = "12,12;7,7;10,6;0,4,2,2,1,3,8,2,4,2,9,3;95,4,68,2,94,91;5";
//		String grid = "10,10;6,3;4,8;9,1,2,4,4,0,3,9,6,4,3,4,0,5,1,6,1,9;97,49,25,17,94,3,96,35,98;3";
//		String grid= "9,9;8,7;5,0;0,8,2,6,5,6,1,7,5,5,8,3,2,2,2,5,0,7;11,13,75,50,56,44,26,77,18;2";

//		String grid= m.genGrid();

		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		System.out.println(ethan + "ethan");

		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0" };
//		String[] goal = {"1","4","6","0","6,16,76,66,56,46"};
//		String[] goal = { "0", "2", "1", "1" };

		String[] members = grid.split(";")[3].split("(?<!\\G\\d+),");
		String mem = "";
		String membersCarriedOrNot = "";
		for (int i = 0; i < members.length; i++) {
			if (i != 0) {
				membersCarriedOrNot += "" + 0;
				mem += "," + members[i];
			} else {
				membersCarriedOrNot += "" + 0;
				mem += members[i];
			}
		}

		int membersNum = members.length; // WRONGGGG !!!!
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth, mem,
				membersCarriedOrNot };

		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0, 0);
		System.out.print(UCS(init, grid, goal));
	}

}
