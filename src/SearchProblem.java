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
	
	public static String DFS(SearchTreeNode intialState, String grid, String[] goalState, int threshold,
			MissionImpossible m) {
		ArrayList<String[]> ancestors = new ArrayList<String[]>();
		String output = "";
		int counter = 0;
		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.push(intialState);
		while (true) {
			counter++;
			if (threshold <= searchTreeNodesStack.peek().getDepth() && searchTreeNodesStack.peek().getDepth() != 0) {
				return "No solution";
			}
			ancestors.add(searchTreeNodesStack.peek().getState());
			ArrayList<SearchTreeNode> x = m.stateTransition(searchTreeNodesStack.pop(), grid, 0);
			for (int i = 0; i < x.size(); i++) {
				searchTreeNodesStack.push(x.get(i));
			}
			boolean goalFound = false;
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
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
					int noOfdeaths = getNoOfdeaths(takeLessHeuristic.peek());
					output += lastOp + ";" + noOfdeaths + ";" + takeLessHeuristic.peek().getHealth() + ";"
							+ numberOfNodesExpanded;
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

					int noOfdeaths = getNoOfdeaths(takeLessHeuristic.peek());
					output += lastOp + ";" + noOfdeaths + ";" + takeLessHeuristic.peek().getHealth() + ";"
							+ numberOfNodesExpanded;
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
		while (!toTraverse.isEmpty()) {
			exploredNodes++;
			current = toTraverse.remove();
			if (stateToString(current).equals(arrayToString(goalState))) {
				break;
			} else {
				for (SearchTreeNode newState : MissionImpossible.stateTransition(current, grid, 0)) {
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
		output = output.substring(0, output.length() -1);
		return output;
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

	public static int getNoOfdeaths(SearchTreeNode node) {
		String health = node.getHealth();
		int deaths = 0;
		for (int i = 0; i < health.split(",").length; i++) {
			if (health.split(",")[i].equals("0")) {
				deaths++;
			}
		}
		return deaths;

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

				int noOfdeaths = getNoOfdeaths(currentNode);

				return path + currentNode.getOperator() + ";" + noOfdeaths + ";" + currentNode.getHealth() + ";"
						+ noOfExpansions;
			}

			// expand current node
			ArrayList<SearchTreeNode> stateSpaces = MissionImpossible.stateTransition(searchTreeNodesStack.remove(),
					grid, 0);
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
		String tempState = (state).getState()[0] + "," + (state).getState()[1] + "," + (state).getState()[2] + ","
				+ (state).getState()[3];
		return tempState;
	}

	public static String arrayToString(String[] state) {
		String tempState = state[0] + "," + state[1] + "," + state[2] + "," + state[3];
		return tempState;
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
	
	

//	public static String AStar(SearchTreeNode intialState, String grid, String[] goalState, MissionImpossible m) {
//		
//	}

	public static void main(String[] args) {
		MissionImpossible m = new MissionImpossible();
//		String grid = "15,15;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
//		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";

		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0" };
//		String[] goal = {"1","4","6","0","6,16,76,66,56,46"};
		String CarriedPositions = "";
		String[] members = grid.split(";")[3].split("(?<!\\G\\d+),");
		String mem = "";
		for (int i = 0; i < members.length; i++) {
			if (i != 0) {
				mem += "," + members[i];
			} else {
				mem += members[i];
			}
			CarriedPositions += "0";
		}

		int membersNum = members.length;
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth, mem };
		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0, 0, "GR2", totalHealth, CarriedPositions);
		// System.out.print(Greedy1(init, grid, goal));
		System.out.print(Greedy2(init, grid, goal));

	}

}
