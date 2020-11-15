import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SearchProblem {

	public static String DFS(SearchTreeNode intialState, String grid, String[] goalState) {
		ArrayList<String[]> ancestors = new ArrayList<String[]>();
		String output = "";
		Stack<SearchTreeNode> searchTreeNodesStack = new Stack<SearchTreeNode>();
		if (intialState == null) {
			return output;
		}
		searchTreeNodesStack.push(intialState);
		while (true) {
			ancestors.add(searchTreeNodesStack.peek().getState());
//			output += searchTreeNodesStack.peek().getOperator();
			System.out.println(output);
//			for(int i=0 ; i< searchTreeNodesStack.peek().getState().length; i++) {
//				System.out.println(searchTreeNodesStack.peek().getState()[i]);
//			}
			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.pop(), grid);
			for (int i = 0; i < x.size(); i++) {
				searchTreeNodesStack.push(x.get(i));
			}
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				boolean found = false;
				for(int j = 0; j< ancestors.size(); j++) {
					if(Arrays.equals(y.getState(), ancestors.get(j))) {
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
					if (y.getState().equals(goalState)) {
						output += y.getOperator();
						return output;
					} else {
						break;
					}
				}
			}
		}
	}

	public static String BFS(SearchTreeNode initialState, String grid,String[] goal) {
		String result = "";
		Queue<SearchTreeNode> toTraverse = new LinkedList<SearchTreeNode>();
		ArrayList<String> prevStates = new ArrayList<String>();
		SearchTreeNode currentState = initialState;
		int counter = 0;
		
		while(!Arrays.equals(goal,currentState.getState())) {
			System.out.println();
			System.out.println();
			ArrayList<SearchTreeNode> stateSpace = MissionImpossible.stateTransition(currentState,grid);
			for(int i = 0; i< stateSpace.size();i++) {
				String tempState = stateSpace.get(i).getState()[0]+","+stateSpace.get(i).getState()[1]+","+stateSpace.get(i).getState()[2]+","+stateSpace.get(i).getState()[3];
				if(!prevStates.contains(tempState)) {
					toTraverse.add(stateSpace.get(i));
					prevStates.add(tempState);
				}
			}
//			if(counter>10000) {
//				break;
//			}
			try {
				currentState = toTraverse.remove();
			}
			catch(Exception e){
				break;
			}
			counter++;
			for(int i = 0;i<goal.length;i++) {
				System.out.print(goal[i] + "    " + currentState.getState()[i]);
				System.out.println();
			}
		}
		System.out.println(counter);
		while(currentState.getParentNode()!=null) {
			result = currentState.getOperator() + "," + result;
			currentState = currentState.getParentNode();
		}
		return result;
		
	}
	public static void main(String[]args) {
		MissionImpossible m = new MissionImpossible();
//		String grid = m.genGrid();
		String grid = "5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3";
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		String[] goal = {submarine.split(",")[0],submarine.split(",")[1],"0",grid.split(";")[5]};
		String[] members = grid.split(";")[3].split(",");
		int membersNum = members.length/2;
		String[] state = {ethan.split(",")[0],ethan.split(",")[1],""+membersNum,grid.split(";")[5]};
		SearchTreeNode init = new SearchTreeNode(state,null,null,0,0);
		System.out.print(DFS(init,grid,goal));
	}
	
	
}
