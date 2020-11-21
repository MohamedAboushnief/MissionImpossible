import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public abstract class SearchProblem {
//
//	public static String DFS(SearchTreeNode intialState, String grid, String[] goalState) {
//		ArrayList<String[]> ancestors = new ArrayList<String[]>();
//		String output = "";
//		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
//		if (intialState == null) {
//			return output;
//		}
//		searchTreeNodesStack.push(intialState);
//		int counter = 0;
//		while (true) {
//			searchTreeNodesStack.peek().getState()[4] = "0,0";
//			ancestors.add(searchTreeNodesStack.peek().getState());
//			if (searchTreeNodesStack.peek().getOperator() != null) {
//				output += searchTreeNodesStack.peek().getOperator();
//			}
//			for (int k = 0; k < searchTreeNodesStack.peek().getState().length; k++) {
//				System.out.print(searchTreeNodesStack.peek().getState()[k] + ",");
//			}
//			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.pop(), grid);
//			for (int i = 0; i < x.size(); i++) {
//				System.out.println(x.get(i).getOperator() + "operator");
//				searchTreeNodesStack.push(x.get(i));
//			}
//			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
//				SearchTreeNode y = searchTreeNodesStack.peek();
//				System.out.println();
//				boolean found = false;
//				for (int j = 0; j < ancestors.size(); j++) {
//					if (Arrays.equals(y.getState(), ancestors.get(j))) {
//						found = true;
//						break;
//					}
//				}
//				if (found) {
//					searchTreeNodesStack.pop();
//				} else {
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

	public static String BFS(SearchTreeNode initialState, String grid, String[] goal) {
		String result = "";
//		Hashtable<String, Integer> prevStates = new Hashtable<String, Integer>(); 
		Queue<SearchTreeNode> toTraverse = new LinkedList<SearchTreeNode>();
		ArrayList<String> prevStates = new ArrayList<String>();
		SearchTreeNode currentState = initialState;
		int counter = 0;
		String goalString = goal[0] + "," + goal[1] + "," + goal[2] + "," + goal[3];
		String newState = currentState.getState()[0] + "," + currentState.getState()[1] + ","
				+ currentState.getState()[2] + "," + currentState.getState()[3];
		while (!newState.equals(goalString)) {
//			if(counter==0) {
//				currentState = initialState;
//			}
//			else {
//				try {
//				currentState = toTraverse.remove();
//				}
//				catch(Exception e){
//					System.out.println("Queue 5eles");
//					break;
//				}
//			}
			newState = currentState.getState()[0] + "," + currentState.getState()[1] + "," + currentState.getState()[2]
					+ "," + currentState.getState()[3];
//			prevStates.put(newState,1);
			prevStates.add(newState);
			ArrayList<SearchTreeNode> stateSpace = MissionImpossible.stateTransition(currentState, grid);
			;
			for (int i = 0; i < stateSpace.size(); i++) {
//				String tempState = stateSpace.get(i).getState()[0] + "," + stateSpace.get(i).getState()[1] + ","
//						+ stateSpace.get(i).getState()[2] + "," + stateSpace.get(i).getState()[3];
//				System.out.println(tempState);
				toTraverse.add(stateSpace.get(i));
			}
//			for(int i = 0;i<prevStates.size();i++) {
//				String tempState = stateSpace.get(i).getState()[0] + "," + stateSpace.get(i).getState()[1] + ","
//						+ stateSpace.get(i).getState()[2] + "," + stateSpace.get(i).getState()[3];
////				System.out.println(tempState);
//				if(prevStates.get(i)==tempState) {
//					
//				}
//			}
			
			while (!toTraverse.isEmpty()) {
				boolean found = false;
				String tempState = toTraverse.peek().getState()[0] + "," + toTraverse.peek().getState()[1] + ","
						+ toTraverse.peek().getState()[2] + "," + toTraverse.peek().getState()[3];
				for(int i = 0;i<prevStates.size();i++) {
					if((prevStates.get(i)).equals(tempState)) {
						found = true;
						toTraverse.remove();
						break;
					}
				}
				if(!found) {
					currentState = toTraverse.remove();
					break;
				}
			}
//				boolean found = false;
//				String tempState = toTraverse.peek().getState()[0] + "," + toTraverse.peek().getState()[1] + ","
//						+ toTraverse.peek().getState()[2] + "," + toTraverse.peek().getState()[3];
//				if(prevStates.containsKey(tempState)) {
//					toTraverse.remove();
//				}
//				else {
//					currentState = toTraverse.remove();
////					toTraverse.add(stateSpace.get(i));
//				}
//				while(true) {
//					String tempState = "";
//					try {
//					tempState = toTraverse.peek().getState()[0] + "," + toTraverse.peek().getState()[1] + ","
//							+ toTraverse.peek().getState()[2] + "," + toTraverse.peek().getState()[3];
//					}catch(Exception e) {
//						System.out.println(counter);
//					}
//					if(prevStates.containsKey(tempState)) {
//						toTraverse.remove();
//					}
//					else {
//						try {
//							currentState = toTraverse.remove();
//							break;
//						}catch(Exception e) {
//							System.out.println(counter);
//						}
//						
//						toTraverse.add(stateSpace.get(i));
//					}
//					
//				}
//				for (int j = 0; j < prevStates.size(); j++) {
//					if ((prevStates.get(j)).equals(tempState)) {
//						found = true;
////						System.out.println("eltemp state = " + tempState);
//						break;
//					}
//				}
//				if (found) {
//					System.out.println("here");
//				} else {
//					toTraverse.add(stateSpace.get(i));
//				}
//			}
			if (counter % 1000 == 0) {
//				break;
				System.out.println(counter);
			}
			counter++;

		}
		System.out.println(!newState.equals(goalString));
		System.out.println(counter);
		while (currentState.getParentNode() != null) {
			System.out.println(newState);
			System.out.println(goalString);
			System.out.println(newState.equals(goalString));
			System.out.println(currentState.getOperator());
			result = currentState.getOperator() + "," + result;
			currentState = currentState.getParentNode();
			newState = currentState.getState()[0] + "," + currentState.getState()[1] + "," + currentState.getState()[2]
					+ "," + currentState.getState()[3];

		}
//		for(int i = 0;i<prevStates.size();i++) {
//			System.out.println(prevStates.get(i));
//		}
		return result;

	}

	public static void main(String[] args) {
		MissionImpossible m = new MissionImpossible();
//		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String grid = "5,5;1,2;1,5;1,3;30;3";

		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		String[] goal = { submarine.split(",")[0], submarine.split(",")[1], "0", "0", "0,0" };
//		String[] goal = {"1","4","6","0","6,16,76,66,56,46"};

		String[] members = grid.split(";")[3].split(",");
		int membersNum = members.length / 2;
		String[] state = { ethan.split(",")[0], ethan.split(",")[1], "" + membersNum, "0", totalHealth,
				grid.split(";")[3] };
		SearchTreeNode init = new SearchTreeNode(state, null, null, 0, 0);
		System.out.print(BFS(init, grid, goal));
	}

}
