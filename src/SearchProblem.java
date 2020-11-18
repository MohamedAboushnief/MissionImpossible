import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;

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

	public static boolean CheckGoal(SearchTreeNode currentNode, String[] goalState) {

		if (currentNode.getState()[0].equals(goalState[0]) && currentNode.getState()[1].equals(goalState[1])
				&& currentNode.getState()[2].equals(goalState[2]) && currentNode.getState()[3].equals(goalState[3])) {
			return true;

		}
		return false;
	}

	
	public static String getPath(SearchTreeNode Node) {
		String path= "";
		
		if(Node.getParentNode()==null) {
			return "";
		}
		else {
			return getPath(Node.getParentNode())+Node.getParentNode().getOperator();
		}
				
		
	}
	
	public static String UCS(SearchTreeNode intialState, String grid, String[] goalState) {

		ArrayList<SearchTreeNode> ancestors = new ArrayList<SearchTreeNode>();
		String output = "";
//		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		ArrayList<SearchTreeNode> searchTreeNodesStack = new ArrayList<SearchTreeNode>();

		if (intialState == null) {
			return output;

		}

		searchTreeNodesStack.add(intialState);
		int counter = 0;
		while (true) {

			SearchTreeNode currentNode = searchTreeNodesStack.get(0);
			ancestors.add(currentNode);

			if (currentNode.getOperator() != null) {
				output += currentNode.getOperator();
			}

//			if (Arrays.equals(currentNode.getState(), goalState)) {
//				System.out.println("Reached Goal State !!!!!!!!");
////				output += currentNode.getOperator();
//				return output;
//			}
			
			if (CheckGoal(currentNode, goalState)) {
				System.out.println("Reached Goal State !!!!!!!!");
				String path= getPath(currentNode);
//				output += currentNode.getOperator();
				return path;
			}

			// pop from stack and pass to transition function
			ArrayList<SearchTreeNode> stateSpaces = MissionImpossible.stateTransition(searchTreeNodesStack.remove(0), grid);

			System.out.println("###### size of state space : " + stateSpaces.size());

//			System.out.println(output);

//			if (counter == 52) {
//				break;
//			}

//			newState[0] = ethX 
//			newState[1] = ethY
//			newState[2] = remainingIMF
//			newState[3] = noOfcarry
//			newState[4] = newHealth

			// push in stack all the new sorted states if not a repeated state
			for (int i = stateSpaces.size() - 1; i >= 0; i--) {
				boolean found = false;
				for (int j = 0; j < ancestors.size(); j++) {

					String[] check = { stateSpaces.get(i).getState()[0], stateSpaces.get(i).getState()[1], stateSpaces.get(i).getState()[2],
							stateSpaces.get(i).getState()[3], stateSpaces.get(i).getOperator() };
					String[] anc = { ancestors.get(j).getState()[0], ancestors.get(j).getState()[1],
							ancestors.get(j).getState()[2], ancestors.get(j).getState()[3],
							ancestors.get(j).getOperator() };
					
					
					
					if (Arrays.equals(check, anc)) {

//						System.out.println("--------------------------------------");
//						System.out.printf("ethan : %s,%s  \n", stateSpaces.get(i).getState()[0],
//								stateSpaces.get(i).getState()[1]);
//						System.out.printf("remaining imf : %s  \n", stateSpaces.get(i).getState()[2]);
//						System.out.printf("no of carry : %s  \n", stateSpaces.get(i).getState()[3]);
//						System.out.printf("operator : %s  \n", stateSpaces.get(i).getOperator());
//						System.out.println("*************************************");
//						System.out.println("there is identical ancestor---------");
//						System.out.println("repeated state");
//						System.out.printf("ethan : %s,%s  \n", ancestors.get(j).getState()[0], ancestors.get(j).getState()[1]);
//						System.out.printf("remaining imf : %s  \n",ancestors.get(j).getState()[2]);
//						System.out.printf("no of carry : %s  \n", ancestors.get(j).getState()[3]);
//						System.out.println("--------------------------------------");
//						System.out.println();
						found = true;
						break;

					}
				}
				if (found == false) {

					System.out.println("======================================");
					System.out.printf("ethan : %s,%s  \n", stateSpaces.get(i).getState()[0],
							stateSpaces.get(i).getState()[1]);
					System.out.printf("remaining imf : %s  \n", stateSpaces.get(i).getState()[2]);
					System.out.printf("no of carry : %s  \n", stateSpaces.get(i).getState()[3]);
					System.out.printf("operator : %s  \n", stateSpaces.get(i).getOperator());
					System.out.println("overall health is : " + stateSpaces.get(i).getState()[4]);
					System.out.println("cost to root is : " + stateSpaces.get(i).getCostToRoot());
					System.out.println("======================================");

					searchTreeNodesStack.add(stateSpaces.get(i));
				}

			}
			
			
//			ArrayList<SearchTreeNode> temp = new ArrayList<SearchTreeNode>();
//			while(!searchTreeNodesStack.isEmpty()) {
//				temp.add(searchTreeNodesStack.pop());	
//			}
//			
			Collections.sort(searchTreeNodesStack);
			
//			for(int i=temp.size() - 1;i>=0;i--) {
//				searchTreeNodesStack.push(temp.get(i));
//			}
			
			
			
//
			System.out.println(" $$$$$$  stack size is : " + searchTreeNodesStack.size());
			System.out.println("///////////////////////////////////////////////////////////////");
			System.out.println();
			counter++;
			System.out.println(counter);
		}
//		return output;

	}

	public static void main(String[] args) {
		MissionImpossible m = new MissionImpossible();
//		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
//		String grid = "10,10;4,8;0,2;1,2,8,9,3,7,6,1,2,4;60,20,70,100,55;3";
		String grid = "5,5;4,2;0,2;1,2,3,3;60,70;3";


		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		System.out.println(ethan + "ethan");

		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0" };
//		String[] goal = {"1","4","6","0","6,16,76,66,56,46"};
//		String[] goal = { "0", "2", "1", "1" };

//		String[] members = grid.split(";")[3].split(",");
//		
//		int membersNum = members.length / 2;
//		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth };

		String[] members = grid.split(";")[3].split("(?<!\\G\\d+),");
		String mem = "";
		for (int i = 0; i < members.length; i++) {
			if (i != 0) {
				mem += "," + members[i];
			} else {
				mem += members[i];
			}
		}

		int membersNum = members.length; // WRONGGGG !!!!
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth, mem };

		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0, 0);
		System.out.print(UCS(init, grid, goal));
	}

}
