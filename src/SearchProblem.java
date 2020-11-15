import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SearchProblem {

	public static String DFS(SearchTreeNode intialState, String grid, String[] goalState, int threshold, MissionImpossible m) {
		System.out.println(threshold + "threshold in DFS");
		ArrayList<String[]> ancestors = new ArrayList<String[]>();
		String output = "";
		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.push(intialState);
		while (true) {
			System.out.println(searchTreeNodesStack.peek().getDepth() + " Depth");
			if (threshold <= searchTreeNodesStack.peek().getDepth() && searchTreeNodesStack.peek().getDepth() != 0) {
				return "No solution";
			}
			searchTreeNodesStack.peek().getState()[4] = "0,0";
			ancestors.add(searchTreeNodesStack.peek().getState());
			if (searchTreeNodesStack.peek().getOperator() != null) {
				output += searchTreeNodesStack.peek().getOperator();
			}
			for (int k = 0; k < searchTreeNodesStack.peek().getState().length; k++) {
				System.out.print(searchTreeNodesStack.peek().getState()[k] + ",");
			}
			ArrayList<SearchTreeNode> x = m.stateTransition(searchTreeNodesStack.pop(), grid);
			for (int i = 0; i < x.size(); i++) {
				searchTreeNodesStack.push(x.get(i));
			}
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				System.out.println();
				boolean found = false;
				for (int j = 0; j < ancestors.size(); j++) {
					if (Arrays.equals(y.getState(), ancestors.get(j))) {
						found = true;
						break;
					}
				}
				if (found) {
					searchTreeNodesStack.pop();
				} else {
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
		// Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		Queue<SearchTreeNode> searchTreeNodesStack = new LinkedList<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.add(intialState);
		int counter = 0;
		while (true) {
//			goalState[4] = searchTreeNodesStack.peek().getState()[4];
			counter++;
			System.out.println(counter + "counter kjbouqeffbbjk");
//			if (counter > 10) {
//				return null;
//			}
			searchTreeNodesStack.peek().getState()[4] = "0,0";
//			ancestors.add(searchTreeNodesStack.peek().getState());
			ancestors.add(searchTreeNodesStack.peek().getState()[0] + "," + searchTreeNodesStack.peek().getState()[1]
					+ "," + searchTreeNodesStack.peek().getState()[2] + ","
					+ searchTreeNodesStack.peek().getState()[3]);

			if (searchTreeNodesStack.peek().getOperator() != null) {
				output += searchTreeNodesStack.peek().getOperator();
			}
//			for(int i=0 ; i< searchTreeNodesStack.peek().getState().length; i++) {
//				System.out.println(searchTreeNodesStack.peek().getState()[i]);
//			}
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

	public static String ID(SearchTreeNode intialState, String grid, String[] goalState, int threshold) {
		int thresholdCounter = 0;
		String gridCopy = grid;
		SearchTreeNode intialCopy = intialState;
		while (thresholdCounter <= threshold) {
			MissionImpossible m = new MissionImpossible();
			String DFSReturn = DFS(intialCopy, gridCopy, goalState, thresholdCounter, m);
			if (!DFSReturn.equals("No solution")) {
				return DFSReturn;
			} else {
				System.out.println(thresholdCounter + "counter");
				thresholdCounter++;
			}
		}
		return "No solution";
	}

	public static void main(String[] args) {
		MissionImpossible m = new MissionImpossible();
//		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String grid = "5,5;2,1;1,0;1,3,4,2,4,1,3,1;54,31,39,98;2";
		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		System.out.println(ethan + "ethan");
		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0", "0,0" };
//		String[] goal = {"1","4","6","0","6,16,76,66,56,46"};

		String[] members = grid.split(";")[3].split(",");
		int membersNum = members.length / 2;
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth };
		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0);
		System.out.print(DFS(init, grid, goal, 1000, m));
	}

}
