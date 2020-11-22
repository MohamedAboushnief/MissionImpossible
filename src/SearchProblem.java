import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

//import jdk.javadoc.internal.doclets.toolkit.util.Comparators;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.*;

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
			searchTreeNodesStack.peek().getState()[4] = "0,0";
			ancestors.add(searchTreeNodesStack.peek().getState());
			if (searchTreeNodesStack.peek().getOperator() != null) {
				output += searchTreeNodesStack.peek().getOperator();
			}
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

	
	public static String Greedy(SearchTreeNode initialState, String grid, String[] goal) {
		ArrayList<SearchTreeNode> ancestors = new ArrayList<SearchTreeNode>();

		Queue<SearchTreeNode> takeLessHeuristic = new LinkedList<SearchTreeNode>();
		MissionImpossible m = new MissionImpossible();
		List<String> imfMembers = Arrays.asList(grid.split(";")[3].split("(?<!\\G\\d+),"));
		String output = "";

		int heuristicValue = m.heuristicFunction(initialState, imfMembers, grid.split(";")[2], initialState.getOperator());
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
						grid);

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
			coun ++;
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
	
	public static String BFS(SearchTreeNode intialState, String grid, String[] goalState) {
		String output = "";
		int exploredNodes = 0;
		Queue<SearchTreeNode> toTraverse = new LinkedList<SearchTreeNode>();
		Hashtable<String, Integer> prevStates = new Hashtable<String, Integer>();
		toTraverse.add(intialState);
		prevStates.put(stateToString(intialState), 0);
		SearchTreeNode current = null;
		while(!toTraverse.isEmpty()) {
			current = toTraverse.remove();
			if(stateToString(current).equals(arrayToString(goalState))) {
				break;
			}else {
				for(SearchTreeNode newState: MissionImpossible.stateTransition(current,grid)) {
					if (!prevStates.containsKey(stateToString(newState))) {
						exploredNodes++;
						prevStates.put(stateToString(newState), 0);
						toTraverse.add(newState);
					}
				}
			}
		}
		while (current.getParentNode() != null) {
			output = current.getOperator() + "," + output;
			current = current.getParentNode();

		}
		return output+exploredNodes;
	}
	
	public static String stateToString(SearchTreeNode state) {
		String tempState = (state).getState()[0] + "," + (state).getState()[1] + ","
					+ (state).getState()[2] + "," + (state).getState()[3];
		return tempState;
	}
	public static String arrayToString(String[] state) {
		String tempState = state[0] + "," + state[1] + ","
					+ state[2] + "," + state[3];
		return tempState;
	}
	
	
	

	public static void main(String[] args) {
		MissionImpossible m = new MissionImpossible();
		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0" };
//		String[] goal = {"1","4","6","0","6,16,76,66,56,46"};

		String[] members = grid.split(";")[3].split("(?<!\\G\\d+),");
		String mem = "";
		for(int i = 0; i < members.length; i++) {
			if(i != 0) {
				mem += "," + members[i];
			}
			else {
				mem += members[i];
			}
		}
		int membersNum = members.length; 
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth, mem };
		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0, 0);
		System.out.print(BFS(init, grid, goal));
		
	}

}
