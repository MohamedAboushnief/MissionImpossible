import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import jdk.javadoc.internal.doclets.toolkit.util.Comparators;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class SearchProblem {

	public static String BFS(SearchTreeNode intialState, String grid, String[] goalState, MissionImpossible m) {
		Queue<SearchTreeNode> searchTreeNodesQueue = new LinkedList<SearchTreeNode>();
		ArrayList<String[]> ancestors = new ArrayList<String[]>();
		String output = "";
		if (intialState == null) {
			return output;
		}
		int counter = 0;
		searchTreeNodesQueue.add(intialState);
		while (true) {
			counter++;
			if (counter > 10000) {
				return output;
			}
			searchTreeNodesQueue.peek().getState()[4] = "0,0";
			if (searchTreeNodesQueue.peek().getOperator() != null) {
				output += searchTreeNodesQueue.peek().getOperator();
			}
			ArrayList<SearchTreeNode> x = m.stateTransition(searchTreeNodesQueue.remove(), grid, 0);
			for (int i = 0; i < x.size(); i++) {
				System.out.println(x.get(i).getOperator() + "operator");
				System.out.println(x.get(i).getState()[0] + "," + x.get(i).getState()[1]);
				searchTreeNodesQueue.add(x.get(i));
				ancestors.add(x.get(i).getState());
//				ancestors.add(x.get(i).getState());
			}
			System.out.println(searchTreeNodesQueue.peek().getState()[0] + ","
					+ searchTreeNodesQueue.peek().getState()[1] + "pos");
			for (int j = 0; j < searchTreeNodesQueue.peek().getState().length; j++) {
				System.out.println(searchTreeNodesQueue.peek().getState()[j] + "state");
			}
//			for (int j = 0; j < ancestors.size(); j++) {
//				SearchTreeNode y = searchTreeNodesQueue.peek();
//				for (int i = 0; i < y.getState().length; i++) {
//					if
//					System.out.println("found");
//				}
//
//			}
			for (int i = 0; i < searchTreeNodesQueue.size(); i++) {
				SearchTreeNode y = searchTreeNodesQueue.peek();
				System.out.println();
				boolean found = false;
				System.out.println(ancestors.size() + "ancestors size");
				for (int j = 0; j < ancestors.size(); j++) {
					if (!Arrays.equals(y.getState(), ancestors.get(j))) {
//					if (y.getState()[0] == ancestors.get(i)[0] && y.getState()[1] == ancestors.get(i)[1]
//							&& y.getState()[2] == ancestors.get(i)[2] && y.getState()[3] == ancestors.get(i)[3]
//							&& y.getState()[4] == ancestors.get(i)[4]) {
						found = true;
						System.out.println(found);
						break;
					}
				}
				if (found) {
					searchTreeNodesQueue.remove();
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

	public static String DFS(SearchTreeNode intialState, String grid, String[] goalState, int threshold,
			MissionImpossible m) {
		System.out.println(threshold + "threshold in DFS");
		ArrayList<String[]> ancestors = new ArrayList<String[]>();
		String output = "";
		int counter = 0;
		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.push(intialState);
		while (true) {
//			if(counter > 100) {
//				return output;
//			}
			counter++;
			System.out.println(counter);
			if (threshold <= searchTreeNodesStack.peek().getDepth() && searchTreeNodesStack.peek().getDepth() != 0) {
//				return "No solution";
				break;
			}
//			searchTreeNodesStack.peek().getState()[4] = "0,0";
//			searchTreeNodesStack.peek().getState()[4] = "0,0";
			ancestors.add(searchTreeNodesStack.peek().getState());
			ArrayList<SearchTreeNode> x = m.stateTransition(searchTreeNodesStack.pop(), grid, 0);
			for (int i = 0; i < x.size(); i++) {
				searchTreeNodesStack.push(x.get(i));
			}
			boolean goalFound = false;
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				System.out.println();
				boolean found = false;
				for (int j = 0; j < ancestors.size(); j++) {
					if (y.getState()[0].equals(ancestors.get(j)[0]) && y.getState()[1].equals(ancestors.get(j)[1])
							&& y.getState()[2].equals(ancestors.get(j)[2])
							&& y.getState()[3].equals(ancestors.get(j)[3])) {
						found = true;
						break;
					}
				}
				if (found) {
					searchTreeNodesStack.pop();
				} else {
					if (y.getState()[0].equals(goalState[0]) && y.getState()[1].equals(goalState[1])
							&& y.getState()[2].equals(goalState[2]) && y.getState()[3].equals(goalState[3])) {
						goalFound = true;
						break;
					} else {
						break;
					}
				}
			}
			if(goalFound) {
				break;
			}
		}
		SearchTreeNode current= searchTreeNodesStack.peek();
		while(current.getParentNode() != null) {
			output = current.getOperator() + "," + output;
			current = current.getParentNode();
		}
		output = output.substring(0, output.length() -1);
		return output;
	}
//
//	public static String BFS(SearchTreeNode intialState, String grid, String[] goalState) {
//		ArrayList<String> ancestors = new ArrayList<String>();
//		String output = "";
//		// Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
//		Queue<SearchTreeNode> searchTreeNodesStack = new LinkedList<SearchTreeNode>();
//		if (intialState == null) {
//			return output;
//		}
//		searchTreeNodesStack.add(intialState);
//		int counter = 0;
//		while (true) {
////			goalState[4] = searchTreeNodesStack.peek().getState()[4];
//			counter++;
//			System.out.println(counter + "counter kjbouqeffbbjk");
////			if (counter > 10) {
////				return null;
////			}
//			searchTreeNodesStack.peek().getState()[4] = "0,0";
////			ancestors.add(searchTreeNodesStack.peek().getState());
//			ancestors.add(searchTreeNodesStack.peek().getState()[0] + "," + searchTreeNodesStack.peek().getState()[1]
//					+ "," + searchTreeNodesStack.peek().getState()[2] + ","
//					+ searchTreeNodesStack.peek().getState()[3]);
//
//			if (searchTreeNodesStack.peek().getOperator() != null) {
//				output += searchTreeNodesStack.peek().getOperator();
//			}
////			for(int i=0 ; i< searchTreeNodesStack.peek().getState().length; i++) {
////				System.out.println(searchTreeNodesStack.peek().getState()[i]);
////			}
//			for (int k = 0; k < searchTreeNodesStack.peek().getState().length; k++) {
//				System.out.print(searchTreeNodesStack.peek().getState()[k] + ",");
//			}
//			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.remove(), grid);
//			for (int i = 0; i < x.size(); i++) {
//				System.out.println(x.get(i).getOperator() + "  operator");
//				searchTreeNodesStack.add(x.get(i));
//			}
//			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
//				SearchTreeNode y = searchTreeNodesStack.peek();
//				System.out.println();
//				boolean found = false;
//				for (int j = 0; j < ancestors.size(); j++) {
//
//					if ((y.getState()[0] + "," + y.getState()[1] + "," + y.getState()[2] + "," + y.getState()[3])
//							.equals(ancestors.get(j))) {
////						for(int cc=0 ; cc< searchTreeNodesStack.peek().getState().length; cc++) {
////							System.out.println(y.getState()[i]);
////							System.out.println(ancestors.get(j)[i]);
////							
////						}
//						found = true;
//						break;
//					}
//				}
//				if (found) {
//					System.out.println("here");
//					searchTreeNodesStack.remove();
//				} else {
//					System.out.println("not here");
//					if (Arrays.equals(y.getState(), goalState)) {
//						output += y.getOperator();
//						return output;
//					} else {
//						break;
//					}
//				}
//			}
//		}
//	}

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
//	public static String Greedy(SearchTreeNode intialState,String grid, String[] goalState) {
//		
//	}

	public static String Greedy(SearchTreeNode initialState, String grid, String[] goal) {
		ArrayList<SearchTreeNode> ancestors = new ArrayList<SearchTreeNode>();

		Queue<SearchTreeNode> takeLessHeuristic = new LinkedList<SearchTreeNode>();
		MissionImpossible m = new MissionImpossible();
		List<String> imfMembers = Arrays.asList(grid.split(";")[3].split("(?<!\\G\\d+),"));
		String output = "";

		int heuristicValue = m.heuristicFunction(initialState, imfMembers, grid.split(";")[2],
				initialState.getOperator());
		initialState.setHeuristicValue(heuristicValue);

		takeLessHeuristic.add(initialState);
		System.out.println(initialState.getHeuristicValue() + "heurrists");
		ArrayList<SearchTreeNode> tempArray = new ArrayList<SearchTreeNode>();
		int coun = 0;
		while (true) {

			if (takeLessHeuristic.size() > 0) {
				ancestors.add(takeLessHeuristic.peek());
				System.out.println(ancestors.size() + " sizeeeeeeeeeeeeeeeee");
				ArrayList<SearchTreeNode> expandedNodes = MissionImpossible.stateTransition(takeLessHeuristic.remove(),
						grid, 0);

				for (int i = 0; i < expandedNodes.size(); i++) {
					tempArray.add(expandedNodes.get(i));
					System.err.println(expandedNodes.get(i).getOperator() + expandedNodes.get(i).getHeuristicValue());
				}
			}

			Collections.sort(tempArray);

			takeLessHeuristic.add(tempArray.get(0));
			tempArray.remove(tempArray.get(0));
			System.out.println(takeLessHeuristic.peek().getOperator() + " first "
					+ takeLessHeuristic.peek().getHeuristicValue() + " eth x" + takeLessHeuristic.peek().getState()[0]
					+ " ethy " + takeLessHeuristic.peek().getState()[1] + " remaining IMF "
					+ takeLessHeuristic.peek().getState()[2] + " number of cary "
					+ takeLessHeuristic.peek().getState()[3]);

//			SearchTreeNode peek = takeLessHeuristic.remove();
//			for (int i = 0; i < takeLessHeuristic.size(); i++) {
//				tempArray.add(takeLessHeuristic.remove());
//			}
//			takeLessHeuristic.add(peek);
//			if(coun == 100) {
//				return output;
//			}
			coun++;
			boolean found = false;
			SearchTreeNode checkNode = takeLessHeuristic.peek();

			for (int i = 0; i < ancestors.size(); i++) { // Check if the node is expanded before and if so remove it
				String[] check = { checkNode.getState()[0], checkNode.getState()[1], checkNode.getState()[2],
						checkNode.getState()[3], checkNode.getOperator() };
				String[] anc = { ancestors.get(i).getState()[0], ancestors.get(i).getState()[1],
						ancestors.get(i).getState()[2], ancestors.get(i).getState()[3],
						ancestors.get(i).getOperator() };
//				System.out.println(check[0] + check[1] + check[2] + check[3] + check[4]);
//				System.out.println(anc[0] + anc[1] + anc[2] + anc[3] + anc[4]);
				if (Arrays.equals(check, anc)) {
					System.out.println("da5555555555555555555555555555555");
					found = true;
					break;
				}

			}

			if (found) {

				takeLessHeuristic.remove();

			} else {
				String g = "";
				String n = "";
				for (int k = 0; k < goal.length; k++) {
					g += goal[k];
					n += checkNode.getState()[k];
				}
				if (g.equals(n)) { // check goal test done
					String lastOp = checkNode.getOperator();
					while (true) {
						SearchTreeNode parentOp = checkNode.getParentNode();
						if (parentOp.getOperator() == null) {
							break;
						} else {
							output = parentOp.getOperator() + output;
							checkNode = parentOp;
						}
					}
					System.out.println("Goal Reached ");
					output += lastOp;
					return output;
				}

			}

		}

	}

//	public static String AStar(SearchTreeNode intialState, String grid, String[] goalState, MissionImpossible m) {
//		
//	}

	public static void main(String[] args) {
		MissionImpossible m = new MissionImpossible();
//		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1"; // tarteeb el imf members 3al grid check.....
//		 String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
//		String grid = "5,5;2,1;1,0;1,3,4,2,4,1,3,1;54,31,39,98;2";

		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0" };
		String[] members = grid.split(";")[3].split("(?<!\\G\\d+),");
		String mem = "";
		for (int i = 0; i < members.length; i++) {
			if (i != 0) {
				mem += "," + members[i];
			} else {
				mem += members[i];
			}
		}
		System.out.println(mem + " is the members");
		int membersNum = members.length;
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth, mem };
		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0, 0);
		System.out.print(DFS(init, grid, goal, 10000000, m));
	}

}
