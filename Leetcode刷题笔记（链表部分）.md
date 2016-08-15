# Leetcode刷题笔记（链表部分）

[TOC]

## S.2_Add Two Numbers

原题地址：https://leetcode.com/problems/add-two-numbers/

思路：

​	(2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 0 -> 8。其中，对243而言，2是个位，4是十位，3是百位。对564而言，5是个位，6是十位，4是百位。

​	按照做加法的方式写，对应为相加然后判断是否需要进位。

代码：

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
		ListNode lastResult = result; // 保存链表的头结点，因为对一个链表而言，只要知道头结点，就可以通过next访问到所有元素
		while(l1 != null && l2 != null){
			int val1 = l1.val;
			int val2 = l2.val;
			int tempsum = result.val + val1 + val2;
			result.val = (result.val + val1 + val2) % 10 ;
			if(tempsum >= 10){   //产生进位，向前进1.
				result.next = new ListNode(1);   
			}else{
				if(l1.next != null || l2.next != null){   //判断是否到了最高位，如果不是，则产生
					result.next = new ListNode(0);        //下一位的节点，并且初始化为0.
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
```

## S.92_Reverse Linked List II

原题地址：https://leetcode.com/problems/reverse-linked-list-ii/

思路：

​	本质上是一道链表逆序题目啦！只不过在这道题目中指定了逆序的两端m和n。方法和经典的从头到尾全部逆序的方法是一样的。

​	维护3个指针，startpoint，node1和node2。

​	startpoint永远指向需要开始reverse的点的前一个位置。

​	node1指向正序中第一个需要rever的node，node2指向正序中第二个需要reverse的node。 

​	交换后，node1 在后，node2在前。这样整个链表就逆序好了。

代码：

```java
public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode newHead = new ListNode(-1);      //给链表增加了一个头节点，当只有两个节点时，startpoint指向头结点。
		newHead.next = head;
		
		if(head == null || head.next == null){
			return newHead.next;
		}
		
		//维护以下3个node，startPoint是m的前一个，node1是两个相邻的需要reverse节点的前一个，node2是后一个
		ListNode startPoint = newHead;
		ListNode node1 = null;
		ListNode node2 = null;
		//node1的最后位置是n-1.node2的最后位置是n
		for(int i = 0; i < n; i++){
			if(i < m-1){
				startPoint = startPoint.next;
			}
			else if( i == m-1){
				node1 = startPoint.next;
				node2 = node1.next;
			}
			else{
				node1.next = node2.next;
				node2.next = startPoint.next;
				startPoint.next = node2;
				node2 = node1.next;
			}
		}
        return newHead.next;
    }
```

startPoint、node1、node2之间的变换过程如如图：

![链表逆序过程](pics\链表逆序过程.PNG)

## S.86_Partition List

原题地址：https://leetcode.com/problems/partition-list/

思路：

​	这道题的意思就是说，给定一个值x，小于x的都放到大于等于x的前面。并且不改变链表之间node原始的相对位置。每次看这道题我老是绕晕，纠结为什么4在3的前面。。其实还是得理解题意，4->3->5都是大于等3的数，而且这保持了他们原来的相对位置。

1. new两个新链表，一个用来管理所有大于等于x的数，一个用来管理小于x的数。遍历原来的链表时，当当前node.val大于等于x就接在大链表上，当当前node.val小于x，就接在小链表上。
2. 最后把小链表的最后一个接上大链表的第一个，大链表最后一个元数的next设置为null。

代码：

```java
public ListNode partition(ListNode head, int x) {
        
		//1.先判断异常条件
		if(head == null || head.next == null)
			return head;
		
		//2.再判断正常条件
		ListNode smallNode = new ListNode(-1);
		ListNode bigNode = new ListNode(-1);
		ListNode originLessHead = smallNode;
		ListNode originGreatHead = bigNode;
		while(head != null){
			if(head.val < x){
				smallNode.next = head;
				smallNode = smallNode.next;
			}
			else{
				bigNode.next = head;
				bigNode = bigNode.next; 
			}
			head = head.next;
		}
		//让小的队列链接上大的队列，同时让大的队列末尾是null
		smallNode.next = originGreatHead.next;
		bigNode.next = null;
		return originLessHead.next;
	
    }
```

## S.83_Remove Duplicates from Sorted List

原题地址：https://leetcode.com/problems/remove-duplicates-from-sorted-list/

思路：

​	这道题目吧，本质上就是在一个已经排过序的链表中，对元素进行遍历操作，当发现链表中有重复元素的时候，通过修改链表指针达到删除重复元素的效果。

1. 维护两个指针，pre和cur，cur指向当前遍历到的元素，pre指向前面的一个。
2. 当pre == cur时，cur = cur.next; 

3. 当pre != cur时，pre = pre.next;  cur = cur.next;

代码：

```java
 public ListNode deleteDuplicates(ListNode head) {
        
        ListNode pre = new ListNode(-1);
        ListNode cur = null;
       
        if(head == null || head.next == null)
        	return head;
   
        pre.next = head;
        cur = head;
        while(cur != null){
        	while(cur != null && pre.val == cur.val){
        		cur = cur.next;
        	}
        	if(cur != null){
        		pre.next = cur;
        		pre = pre.next;
        		cur = cur.next;
        	}else
        	{
        		pre.next = cur;
        	}
        }
        return head;
    }
```



