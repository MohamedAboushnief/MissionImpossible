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
			if (goalFound) {
				break;
			}
		}
		SearchTreeNode current = searchTreeNodesStack.peek();
		int noOfdeaths = getNoOfdeaths(current);
		String health = current.getHealth();

		while (current.getParentNode() != null) {
			output = current.getOperator() + "," + output;
			current = current.getParentNode();
		}
		output = output.substring(0, output.length() - 1);

		return output + ";" + noOfdeaths + ";" + health + ";" + counter;

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

		int noOfdeaths = getNoOfdeaths(current);
		String healthString = current.getHealth();
		while (current.getParentNode() != null) {
			output = current.getOperator() + "," + output;
			current = current.getParentNode();

		}
		output = output.substring(0, output.length() - 1);

		return output + ";" + noOfdeaths + ";" + healthString + ";" + exploredNodes;

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
				thresholdCounter++;
			}
		}
		return "No solution";
	}

	public static String AS1(SearchTreeNode intialState, String grid, String[] goalState) {
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
				String path = getPath(currentNode);

				int noOfdeaths = getNoOfdeaths(currentNode);

				return path + currentNode.getOperator() + ";" + noOfdeaths + ";" + currentNode.getHealth() + ";"
						+ noOfExpansions;
			}

			// expand current node
			ArrayList<SearchTreeNode> stateSpaces = MissionImpossible.stateTransition(searchTreeNodesStack.remove(),
					grid, 1);
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

	public static String AS2(SearchTreeNode intialState, String grid, String[] goalState) {
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
				String path = getPath(currentNode);

				int noOfdeaths = getNoOfdeaths(currentNode);

				return path + currentNode.getOperator() + ";" + noOfdeaths + ";" + currentNode.getHealth() + ";"
						+ noOfExpansions;
			}

			// expand current node
			ArrayList<SearchTreeNode> stateSpaces = MissionImpossible.stateTransition(searchTreeNodesStack.remove(),
					grid, 2);
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
}



