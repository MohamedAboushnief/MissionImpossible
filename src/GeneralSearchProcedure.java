import java.util.*;
public class GeneralSearchProcedure {

	public static void main(String [] args) {
		ArrayList<Integer> searchTreeNodesStack = new ArrayList<Integer>();
		searchTreeNodesStack.add(0);
		searchTreeNodesStack.add(1);
		searchTreeNodesStack.add(2);
		searchTreeNodesStack.add(3);
		searchTreeNodesStack.add(4);
		
		
		System.out.println(searchTreeNodesStack.size());
		searchTreeNodesStack.remove(0);
		System.out.println(searchTreeNodesStack.size());
		System.out.println(searchTreeNodesStack.get(0));
		
  
    
} 
	}

