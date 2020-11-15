import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class SearchProblem {
	
	public static String BFS(SearchTreeNode initialState,String grid,String[] goal) {
		String result = "";
		Queue<SearchTreeNode> toTraverse = new LinkedList<SearchTreeNode>();
		ArrayList<String> prevStates = new ArrayList<String>();
		SearchTreeNode currentState = initialState;
		int counter = 0;
		while(!Arrays.equals(goal,currentState.getState())) {
//			System.out.println();
//			System.out.println();
			String newState = currentState.getState()[0]+","+currentState.getState()[1]+","+currentState.getState()[2]+","+currentState.getState()[3]+","+currentState.getState()[4];
			prevStates.add(newState);
			System.out.println("current state  " +newState);
			ArrayList<SearchTreeNode> stateSpace = MissionImpossible.stateTransition(currentState,grid);
			for(int i=0; i<stateSpace.size(); i++) {
				System.out.println(stateSpace.get(i).getOperator()+ "  operator");
			}
			for(int i = 0; i< stateSpace.size();i++) {
				String tempState = stateSpace.get(i).getState()[0]+","+stateSpace.get(i).getState()[1]+","+stateSpace.get(i).getState()[2]+","+stateSpace.get(i).getState()[3]+","+stateSpace.get(i).getState()[4];
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
			}
			catch(Exception e){
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
		while(currentState.getParentNode()!=null) {
			result = currentState.getOperator() + "," + result;
			currentState = currentState.getParentNode();
		}
		return result;
		
	}
	public static void main(String[]args) {
		MissionImpossible m = new MissionImpossible();
		String grid = "5,5;1,1;1,4;1,2,1,3;0,0;3";
		String totalHealth = grid.split(";")[4];
		String submarine = grid.split(";")[2];
		String ethan = grid.split(";")[1];
		System.out.println(ethan + "ethan");
		String[] goal = {submarine.split(",")[0],submarine.split(",")[1],"0","0","0,0"};
//		String[] goal = {"1","3","6","0","6,16,76,66,56,46"};

		String[] members = grid.split(";")[3].split(",");
		int membersNum = members.length/2;
		String[] state = {ethan.split(",")[0],ethan.split(",")[1],""+membersNum,"0",totalHealth};
		SearchTreeNode init = new SearchTreeNode(state,null,null,0,0);
		System.out.print(BFS(init,grid,goal));
	}
	
	
}
