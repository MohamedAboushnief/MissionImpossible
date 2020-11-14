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
			output += searchTreeNodesStack.peek().getOperator();
			for(int i=0 ; i< searchTreeNodesStack.peek().getState().length; i++) {
				System.out.println(searchTreeNodesStack.peek().getState()[i]);
			}
			ArrayList<SearchTreeNode> x = MissionImpossible.stateTransition(searchTreeNodesStack.pop(), grid);
			for (int i = 0; i < x.size(); i++) {
				searchTreeNodesStack.push(x.get(i));
			}
			for (int i = 0; i < searchTreeNodesStack.size(); i++) {
				SearchTreeNode y = searchTreeNodesStack.peek();
				boolean found = false;
				for(int j = 0; j< ancestors.size(); j++) {
					if(Arrays.equals(y.getState(), ancestors.get(j))) {
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
		ArrayList<SearchTreeNode> prevStates = new ArrayList<SearchTreeNode>();
		SearchTreeNode currentState = initialState;
		System.out.println(goal);
		int counter = 0;
		
		while(!goal.equals(currentState.getState())) {
			for(int i = 0;i<goal.length;i++) {
				System.out.print(goal[i] + "    " + currentState.getState()[i]);
				System.out.println();
			}
			System.out.println();
			System.out.println();
			ArrayList<SearchTreeNode> stateSpace = MissionImpossible.stateTransition(currentState,grid);
			for(int i = 0; i< stateSpace.size();i++) {
				if(!currentState.equals(stateSpace.get(i))) {
					toTraverse.add(stateSpace.get(i));
//					prevStates.add(stateSpace.get(i));
				}
			}
			if(counter>100000) {
				break;
			}
			currentState = toTraverse.remove();
			counter++;
		}
		System.out.println(counter);
		while(currentState.getParentNode()!=null) {
			result = currentState.getOperator() + "," + result;
			currentState = currentState.getParentNode();
		}
		System.out.println("a5erha");
		return result;
		
	}
	public static void main(String[]args) {
		MissionImpossible m = new MissionImpossible();
		String grid = m.genGrid();
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
