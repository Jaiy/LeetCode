# LeetCode刷题笔记（排序部分）

[TOC]

## S.88_Merge Sorted Array

原题地址：https://leetcode.com/problems/merge-sorted-array/

思路：

​	给定nums1和nums2两个数组，其中包含的元素分别是m和和n个。要求把nums2中的树放到num1中，并且让num1是有序的。num1的大小可以容纳下两个数组的所有元素。

​	知识一道easy的题目，因为不允许使用多余的数组空间，就不好对两个数组从前往后进行比较（插入位置比较麻烦）。方法是从后往前比，然后处理剩下的元素。

代码：

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
		
		while(m > 0 && n > 0){
			if(nums1[m-1] > nums2[n-1]){
				nums1[m+n-1] = nums1[m-1];
				m--;
			}
			else{
				nums1[m+n-1] = nums2[n-1];
				n--;
			}
			
		}
		while(n>0){
			nums1[m+n-1] = nums2[n-1];
			n--;
		}
	}
```

### 复习：

对两个数组进行merge 排序：

```java
public int[] mergeList(int[] a, int[] b){
		int[] c = new int[a.length+b.length];
		int i = 0; //visit a
		int j = 0; //visit b
		int k = 0; //visit c
		while(i < a.length && j < b.length){
			if(a[i] < b [j])
				c[k++] = a[i++];
			else
				c[k++] = b[j++];
		}
		while(i < a.length)
			c[k++] = a[i++];
		while(j < b.length)
			c[k++] = b[j++];
		return c;
	}
```

对两个LinkedList进行merge 排序：

```java
public ListNode mergeList(ListNode list1, ListNode list2){
		if(list1 == null)
			return list2;
		if(list2 == null)
			return list1;
		
		ListNode newhead = new ListNode(-1);
		ListNode head = newhead;
		
		while(list1 != null && list2 != null){
			if(list1.val < list2.val){
				newhead.next = list1;
				list1 = list1.next;
				newhead = newhead.next;
			}
			else{
				newhead.next = list2;
				list2 = list2.next;
				newhead = newhead.next;
			}
		}
		
//		while(list1 != null){
//			newhead.next = list1;
//			list1 = list1.next;
//			newhead = newhead.next;
//		}
		//替换上面的while循环
		if(list1 != null){
			newhead.next = list1;
		}
//		while(list2 != null){
//			newhead.next = list2;
//			list2 = list2.next;
//			newhead = newhead.next;
//		}
		//替换上面的while循环
		if(list2 != null){	
			newhead.next = list2;
		}
		//使用if这里就不要再赋予额外的null指针了
//		newhead.next = null;
		return head.next;
	}
```

## S.21_Merge Two Sorted Lists

原题地址：https://leetcode.com/problems/merge-two-sorted-lists/

思路；

​	因为是对有序的链表排序，就从头开始，分别比较两个链表的每个元素。较小元素放前面，然后一次往后走。

代码：

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        
        if(l1 == null)
        	return l2;
        if(l2 == null)
        	return l1;
        
        ListNode newhead = new ListNode(-1);
        ListNode head = newhead;
        
        while(l1 != null && l2 != null){
        	if(l1.val < l2.val){
        		newhead.next = l1;
        		l1 = l1.next;
        		newhead = newhead.next;
        	}
        	else{
        		newhead.next = l2;
        		l2 = l2.next;
        		newhead = newhead.next;
        	}
        }
        if(l1 != null)
        	newhead.next = l1;
	
        if(l2 != null)
        	newhead.next = l2;
	
        return head.next;
	
    }
```

## S.23_Merge k Sorted Lists

原题地址：https://leetcode.com/problems/merge-k-sorted-lists/

思路：

​	这道题目是让对K个sorted list进行排序。可以在对两个sorted list排序的基础上进行扩展，比如说每次都调用两两排序的方法，但是这样做，时间复杂度会很高。

超时的代码及复杂度分析：

​	假设每条链表平均有n个元素，此种时间复杂度是O(2n+3n+…+kn), 为O(nk²)。因此会超时。	

```java
public ListNode mergeKLists(ListNode[] lists) {

		int length = lists.length;
		if (length == 0)
			return null;

		ListNode head = lists[0];
		for (int i = 1; i < length; i++) {
			head = mergeList(head, lists[i]);
		}

		return head;
	}
```

### 回顾一下什么是归并排序：

​	归并排序是对一个无序的数组进行排序。最差时间复杂度是O（nlgn）。

参考链接：http://www.java2novice.com/java-sorting-algorithms/merge-sort/

> 1) Divide the unsorted array into n partitions, each partition contains 1 element. Here the one element is considered as sorted. 
>
> 2) Repeatedly merge partitioned units to produce new sublists until there is only 1 sublist remaining. This will be the sorted list at the end.

 ![MergeSort](pics\MergeSort.PNG)

代码：

```java
public class Merge {
	private static int[] aux;
	
	public static void sort(int[] a){
		aux = new int[a.length];
		sort(a, 0, a.length-1);
	}
	private static void sort(int[] a, int lo, int hi){
		if(hi <= lo)
			return ;
		int mid = (hi+lo)/2;
		sort(a, lo, mid);
		sort(a, mid+1,hi);
		merge(a, lo, mid, hi);
	}
	
	public static void merge(int[] a, int lo, int mid, int hi){
		int i = lo, j = mid+1;
		for(int k = lo; k <= hi; k++)
			aux[k] = a[k];
		
		for(int k = lo; k <= hi; k++)
			if(i > mid)  //mid左边的全部排序结束，右边的接着放就行
				a[k] = aux[j++];
			else if(j > hi)   //mid右边的全部排序结束，左边的直接放即可
				a[k] = aux[i++];
			else if(aux[j] < aux[i])  //mid两边逐个比较，
				a[k] = aux[j++];
			else 
				a[k] = aux[i++];
		
	}
	
	public static void main(String[] args) {
//		int[] a = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		int[] a = {16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
		for(int ele : a)
			System.out.print(ele+" ");
		sort(a);
		System.out.println();
		for(int ele : a)
			System.out.print(ele+" ");
		
	}
	
}
```

正确思路：

​	借鉴归并排序的思想，针对这个lists进行排序。不同于传统归并排序的地方在于：lists数组中的元素不是基础类型，而是自定义类型的ListNode。假如lists中有4个元素，先分别归并前两个和后两个，然后再并归并后的结果再次进行归并，得到结果。

代码：（这道题目困了我一天，难点在于不知道如何写递归）

```java
public ListNode mergeList(ListNode l1, ListNode l2) {
		if (l1 == null)

			return l2;
		if (l2 == null)
			return l1;

		ListNode newhead = new ListNode(-1);
		ListNode head = newhead;

		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				newhead.next = l1;
				l1 = l1.next;
				newhead = newhead.next;
			} else {
				newhead.next = l2;
				l2 = l2.next;
				newhead = newhead.next;
			}
		}
		if (l1 != null)
			newhead.next = l1;
		if (l2 != null)
			newhead.next = l2;

		return head.next;
	}
	//把这里的边界条件控制好
	public ListNode mergeKLists(ListNode[] lists, int startIndex, int endIndex){
			
		if(startIndex < endIndex){
			int mid = (startIndex + endIndex)/2;
			ListNode l1 = mergeKLists(lists, startIndex, mid);
			ListNode l2 = mergeKLists(lists, mid+1, endIndex);
			return mergeList(l1,l2);
		}
		else{
			return lists[startIndex];
		}
	}

	public ListNode mergeKLists(ListNode[] lists) {
		if(lists == null || lists.length == 0)
			return null;
		
		if(lists.length == 1)
			return lists[0];
		
		int length = lists.length;
		return mergeKLists(lists, 0, length-1);
	}

	//下面是测试用例：
	public void test() {
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(5);
		l1.next.next = new ListNode(10);
		
		ListNode l2 = new ListNode(2);
		l2.next = new ListNode(4);
		l2.next.next = new ListNode(8);
		
		ListNode l3 = new ListNode(3);
		l3.next = new ListNode(7);
		l3.next.next = new ListNode(11);
		
		ListNode l4 = new ListNode(6);
		l4.next = new ListNode(12);
		l4.next.next = new ListNode(15);
		
		ListNode[] lists = {l1,l2,l3,l4};
		ListNode newlist = mergeKLists(lists);
		while(newlist != null){
			System.out.println(newlist.val);
			newlist = newlist.next;
		}
 	}
```

## S.147_Insertion Sort List

原题地址：https://leetcode.com/problems/insertion-sort-list/

思路：

​	思路很简单，就是把一个一个元素往已排好序的list中插入的过程。

​	初始时，sorted list是空，把一个元素插入sorted list中。然后，在每一次插入过程中，都是找到最合适位置进行插入。

​	 因为是链表的插入操作，需要维护pre，cur和next3个指针。

​	 pre始终指向sorted list的fakehead，cur指向当前需要被插入的元素，next指向下一个需要被插入的元素。

​	 当sortedlist为空以及pre.next所指向的元素比cur指向的元素值要大时，需要把cur元素插入到pre.next所指向元素之前。否则，pre指针后移。最后返回fakehead的next即可。

 ![链表实现插入排序](C:\Users\chengcheng\Desktop\链表实现插入排序.jpg)

​	

代码：

```java
public ListNode insertionSortList(ListNode head) {
		if(head == null || head.next == null)
			return head;
		
		ListNode sortedListHead = new ListNode(0);//fakenode
		ListNode cur = head;
		while(cur != null){
			ListNode next = cur.next;
			ListNode pre = sortedListHead;
			while(pre.next != null && pre.next.val < cur.val){
				pre = pre.next;
			}
			//下面看不懂
			cur.next = pre.next;
			pre.next = cur;
			cur = next;
		}
        return sortedListHead;
    }
```

## S.148_Sort List

原题地址：https://leetcode.com/problems/sort-list/

思路：

​	对这个LinkedList进行归并排序，时间复杂度是O（nlgn）。此前实现的归并排序都是基于数组这样的数据结构的，现在做归并排序要使用list这样的数据结构。其核心是如何找到这个链表的中间位置，对其进行二分吧。

代码：

```java
public ListNode sortList(ListNode head) {
        if(head == null || head.next == null)
        	 return head;
         
         return recursiveSort(head);
    }
	//不断对list进行二分部分
   public ListNode recursiveSort(ListNode head){
		
		ListNode slower = head;
        ListNode faster = head;
     //在找中点的时候使用到快慢指针的技巧
        while(faster.next != null && faster.next.next != null){
        	
       	 	slower = slower.next;
       	 	faster = faster.next.next;
        }
        if(head != faster && head.next != faster){
        	ListNode part2 = slower.next;
        	slower.next = null;
        	ListNode first = recursiveSort(head);
        	ListNode second = recursiveSort(part2);
        	return mergeListNode(first, second);
        }
        else{
        	ListNode part2 = head.next;
        	head.next = null;
        	return mergeListNode(head,part2);
        }  
	}
	//真正做归并的部分
	public ListNode mergeListNode(ListNode first, ListNode second){
		
		ListNode node = new ListNode(-1);
		ListNode result = node;
		while(first != null && second != null){
			if(first.val < second.val){		
				node.next = first;
				first = first.next;
				node = node.next;
			}
			else{
				node.next = second;
				second = second.next;
				node = node.next;
			}
		}
		if(first != null){
			node.next = first;
		}
		if(second != null){
			node.next = second;	
		}
		return result.next;
	}
```

## S.41_First Missing Positive（妈啊，我都看不懂这个题目的意思）

原题地址：https://leetcode.com/problems/first-missing-positive/

思路：

​	在一个未排序的数组中，返回第一个本应该出现，但是却没有出现的正整数。

>Given [1,2,0] return 3, 分析：0，1，2之后应该出现3.
>
>and [3,4,-1,1] return 2. 分析：-1，1，3，4。1之后应该出现2的。但是没有出现。

代码：

```java
public int firstMissingPositive(int[] A) {
        
		if(A==null || A.length==0)  
              return 1;  
              
          for(int i=0;i<A.length;i++){  
              if(A[i]<=A.length && A[i]>0 && A[A[i]-1]!=A[i]){  
                  int temp = A[A[i]-1];  
                  A[A[i]-1] = A[i];  
                  A[i] = temp;  
                 i--;  
             }  
         }  
         
         for(int i=0;i<A.length;i++){  
             if(A[i]!=i+1)  
                 return i+1;  
         } 
         return A.length+1;  
    }
```

## S.75_Sort Colors

原题地址：https://leetcode.com/problems/sort-colors/

思路：

​	这道题目抽象出来后的意思就是：数组中有0，1，2共3种不同的数字，他们之间是乱序的，然后对这个数组进行排序，使得所有相同的数字是相邻在一起的。并且顺序按照0，1，2。

​	思路一：

​	直接调用系统的方法：Arrays.sort(nums)就可以对数组进行排序。

代码：

```java
	public void sortColors(int[] nums) {
		if(nums.length == 0 || nums.length == 1)
			return ;
		
		Arrays.sort(nums);
		
    }
```

​	思路二：

​	由于题目明确要求不能使用库函数，所以要使用其他方法。

​	