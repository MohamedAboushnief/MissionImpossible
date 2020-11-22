import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Hashtable;
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
			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.pop(), grid, 0);
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

	public static String Greedy1(SearchTreeNode initialState, String grid, String[] goal) {
		ArrayList<SearchTreeNode> ancestors = new ArrayList<SearchTreeNode>();

		Queue<SearchTreeNode> takeLessHeuristic = new LinkedList<SearchTreeNode>();
		MissionImpossible m = new MissionImpossible();
		List<String> imfMembers = Arrays.asList(grid.split(";")[3].split("(?<!\\G\\d+),"));
		String output = "";

		int heuristicValue = m.heuristicFunction(initialState, imfMembers, grid.split(";")[2],
				initialState.getOperator());
		initialState.setHeuristicValue(heuristicValue);

		takeLessHeuristic.add(initialState);
		ArrayList<SearchTreeNode> tempArray = new ArrayList<SearchTreeNode>();
		int numberOfNodesExpanded = 0;
		while (true) {

			if (takeLessHeuristic.size() > 0) {
				ancestors.add(takeLessHeuristic.peek());
				ArrayList<SearchTreeNode> expandedNodes = MissionImpossible.stateTransition(takeLessHeuristic.remove(),
						grid, 1);
				numberOfNodesExpanded++;
				for (int i = 0; i < expandedNodes.size(); i++) {
					tempArray.add(expandedNodes.get(i));
				}
			}

			Collections.sort(tempArray); // putting the least heuristic node at the front

			takeLessHeuristic.add(tempArray.get(0));
			tempArray.remove(tempArray.get(0));

			boolean found = false;
			SearchTreeNode checkNode = takeLessHeuristic.peek();

			for (int i = 0; i < ancestors.size(); i++) { // Check if the node is expanded before and if so remove it
				String[] check = { checkNode.getState()[0], checkNode.getState()[1], checkNode.getState()[2],
						checkNode.getState()[3], checkNode.getOperator() };
				String[] anc = { ancestors.get(i).getState()[0], ancestors.get(i).getState()[1],
						ancestors.get(i).getState()[2], ancestors.get(i).getState()[3],
						ancestors.get(i).getOperator() };

				if (Arrays.equals(check, anc)) {
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
							output = parentOp.getOperator() + "," + output;
							checkNode = parentOp;
						}
					}
					takeLessHeuristic.peek().setHeuristicValue(0);
					System.out.println("Goal Reached " + "and the goal heuristic value is = "
							+ takeLessHeuristic.peek().getHeuristicValue());
					output += lastOp + ";" + numberOfNodesExpanded;
					return output;
				}

			}

		}

	}

	public static String Greedy2(SearchTreeNode initialState, String grid, String[] goal) {
		ArrayList<SearchTreeNode> ancestors = new ArrayList<SearchTreeNode>();

		Queue<SearchTreeNode> takeLessHeuristic = new LinkedList<SearchTreeNode>();
		MissionImpossible m = new MissionImpossible();
		List<String> imfMembers = Arrays.asList(grid.split(";")[3].split("(?<!\\G\\d+),"));
		String output = "";
		String[] imfHealth = initialState.getState()[4].split(",");
		int heuristicValue = m.heuristicFunction_2(initialState, imfMembers, imfHealth, initialState.getOperator());
		initialState.setHeuristicValue(heuristicValue);

		takeLessHeuristic.add(initialState);
		ArrayList<SearchTreeNode> tempArray = new ArrayList<SearchTreeNode>();
		int numberOfNodesExpanded = 0;
		while (true) {

			if (takeLessHeuristic.size() > 0) {
				ancestors.add(takeLessHeuristic.peek());
				ArrayList<SearchTreeNode> expandedNodes = MissionImpossible.stateTransition(takeLessHeuristic.remove(),
						grid, 2);
				numberOfNodesExpanded++;
				for (int i = 0; i < expandedNodes.size(); i++) {
					tempArray.add(expandedNodes.get(i));

				}
			}

			Collections.sort(tempArray); // putting the least heuristic node at the front

			takeLessHeuristic.add(tempArray.get(0));

			tempArray.remove(tempArray.get(0));

			boolean found = false;
			SearchTreeNode checkNode = takeLessHeuristic.peek();

			for (int i = 0; i < ancestors.size(); i++) { // Check if the node is expanded before and if so remove it
				String[] check = { checkNode.getState()[0], checkNode.getState()[1], checkNode.getState()[2],
						checkNode.getState()[3], checkNode.getOperator() };
				String[] anc = { ancestors.get(i).getState()[0], ancestors.get(i).getState()[1],
						ancestors.get(i).getState()[2], ancestors.get(i).getState()[3],
						ancestors.get(i).getOperator() };

				if (Arrays.equals(check, anc)) {
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
							output = parentOp.getOperator() + "," + output;
							checkNode = parentOp;
						}
					}
					takeLessHeuristic.peek().setHeuristicValue(0);
					System.out.println("Goal Reached " + "and the goal heuristic value is = "
							+ takeLessHeuristic.peek().getHeuristicValue());
					output += lastOp + ";" + numberOfNodesExpanded;
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
			exploredNodes++;
			current = toTraverse.remove();
			if(stateToString(current).equals(arrayToString(goalState))) {
				break;
			}else {
				for(SearchTreeNode newState: MissionImpossible.stateTransition(current,grid,0)) {
					if (!prevStates.containsKey(stateToString(newState))) {
						
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
			

	public static boolean CheckGoal(SearchTreeNode currentNode, String[] goalState) {

		if (currentNode.getState()[0].equals(goalState[0]) && currentNode.getState()[1].equals(goalState[1])
				&& currentNode.getState()[2].equals(goalState[2]) && currentNode.getState()[3].equals(goalState[3])) {
			return true;

		}
		return false;
	}

	// function that returns the path to the goal
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

			String s = Node.getParentNode().getOperator();
			if (s == null) {
				return getPath(Node.getParentNode()) + "";
			} else {
				return getPath(Node.getParentNode()) + s + ",";
			}

		}

	}

	

	public static String UCS(SearchTreeNode intialState, String grid, String[] goalState) {

		String output = "";
		Hashtable<String, Integer> ancestors = new Hashtable<String, Integer>();
		PriorityQueue<SearchTreeNode> searchTreeNodesStack = new PriorityQueue<SearchTreeNode>();

		if (intialState == null) {
			return output;

		}

		// add initial state in the stack
		searchTreeNodesStack.add(intialState);
		int noOfExpansions = 0;
		while (true) {

			// get the first element
			SearchTreeNode currentNode = searchTreeNodesStack.peek();

			// add the first element in the ancestors hashtable
			ancestors.put(stateToString(currentNode), 0);

			if (currentNode.getOperator() != null) {
				output += currentNode.getOperator();
			}

			// check if current node is the goal state
			if (CheckGoal(currentNode, goalState)) {
				System.out.println("Reached Goal State !!!!!!!!");
				String path = getPath(currentNode);
				return path +currentNode.getOperator()+ ";" + noOfExpansions;
			}

			// expand current node
			ArrayList<SearchTreeNode> stateSpaces = MissionImpossible.stateTransition(searchTreeNodesStack.remove(),
					grid,0);
			noOfExpansions++;

			for (int i = 0; i < stateSpaces.size(); i++) {
				// check for repeated states
				if (!ancestors.containsKey(stateToString(stateSpaces.get(i)))) {
					searchTreeNodesStack.add(stateSpaces.get(i));
					ancestors.put(stateToString(stateSpaces.get(i)), 0);

				}

			}

		}

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
//		String grid = "15,15;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String grid ="15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
//		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";

		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0" };
//		String[] goal = {"1","4","6","0","6,16,76,66,56,46"};

		String[] members = grid.split(";")[3].split("(?<!\\G\\d+),");
		String mem = "";
		for (int i = 0; i < members.length; i++) {
			if (i != 0) {
				mem += "," + members[i];
			} else {
				mem += members[i];
			}
		}
		int membersNum = members.length;
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth, mem };
		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0, 0, "UC");
		//System.out.print(Greedy1(init, grid, goal));
		System.out.print(UCS(init, grid, goal));

	}

}
