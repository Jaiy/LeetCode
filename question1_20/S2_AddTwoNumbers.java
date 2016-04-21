package question1_20;

/**
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 0 -> 8
 * 
 * @author chengcheng
 *
 */
public class S2_AddTwoNumbers {

	public static void main(String[] args) {
		ListNode l1 = new ListNode(9);
		l1.next = new ListNode(1);
		l1.next.next = new ListNode(6);
//		
//		ListNode l2 = new ListNode(5);
//		l2.next = new ListNode(6);
//		l2.next.next = new ListNode(4);
//		ListNode result = addTwoNumbers(l1,l2);
		
//		ListNode l1 = new ListNode(1);
		
		ListNode l2 = new ListNode(0);
//		l2.next = new ListNode(9);
		ListNode result = addTwoNumbers(l1,l2);
		
		while(result != null){
			System.out.println(result.val);
			result = result.next;
		}

	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode result = new ListNode(0);
		ListNode lastResult = result;
		while(l1 != null && l2 != null){
			int val1 = l1.val;
			int val2 = l2.val;
			int tempsum = result.val + val1 + val2;
			result.val = (result.val + val1 + val2) % 10 ;
			if(tempsum >= 10){
				result.next = new ListNode(1);
			}else{
				if(l1.next != null || l2.next != null){
					result.next = new ListNode(0);
				}
			}
			result = result.next;
			l1 = l1.next;
			l2 = l2.next;
		}
		while(l1 != null){
			int val = l1.val;
			int tempsum = result.val + val;
			result.val = (result.val + val) % 10;
			if(tempsum >= 10 ){
				result.next = new ListNode(1);
			}else{
				if(l1.next != null){
					result.next = new ListNode(0);
				}
			}
			l1 = l1.next;
			result = result.next;
		}
		while(l2 != null){
			int val = l2.val;
			int tempsum = result.val + val;
			result.val = (result.val + val) % 10;
			if(tempsum >= 10 ){
				result.next = new ListNode(1);
			}else{
				if(l2.next != null){
					result.next = new ListNode(0);
				}
			}
			l2 = l2.next;
			result = result.next;
		}
		
		return lastResult;
	}

}
