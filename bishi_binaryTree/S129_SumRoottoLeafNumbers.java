package bishi_binaryTree;

import org.junit.Test;

import question121_140.Node;

/**
 * 
 * @author chengcheng
 * @time 2016年8月30日 下午9:27:54
 *
 */
public class S129_SumRoottoLeafNumbers {
	public int sumNumbers(TreeNode root) {
		return dfs(root,0);
		
	}
	
	public int dfs(TreeNode root, int sum){
		if(root == null)
			return 0;
		
		
		if(root.left == null && root.right == null)
			return sum = sum*10 + root.val;
		
		return dfs(root.left,sum*10+root.val)+dfs(root.right,sum*10+root.val);
	}

	@Test
	public void test() {

	}
}
