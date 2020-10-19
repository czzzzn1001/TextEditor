/**
 * Explanation: Implementation of text editor
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Oct 7, 2020
 * COSI 21A PA1
 */
package main;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.io.File;
import java.io.PrintStream;
public class Editor {
	
	public int numChars; /** KEEP THIS PUBLIC : use this to store the number of characters in your Editor */
	public int curPos; /** KEEP THIS PUBLIC : use this to store the current cursor index in [0, numChars] */
	
	public Node cur; /** KEEP THIS PUBLIC : use this to reference the node that is after the visual cursor or null if curPos = numChars */
	public Node head; /** KEEP THIS PUBLIC : use this to reference the first node in the Editor's doubly linked list */
	public Node tail; /** KEEP THIS PUBLIC : use this to reference the last node in the Editor's doubly linked list */
	
	public Stack<String> savedVersions; /** KEEP THIS PUBLIC : use this to store the contents of the editor that are saved by the user */
	/**
	 *  constructor of Editor
	 */
	public Editor() {
		numChars=0;
		curPos=0;
		cur=null;
		head=null;
		tail=null;
		savedVersions=new Stack();
	}
	/**
	 *  second constructor of Editor that constructs an editor with char from text
	 */
	public Editor(String filepath) throws FileNotFoundException {
		numChars=0;
		curPos=0;
		cur=null;
		head=null;
		tail=null;
		savedVersions=new Stack();
		Scanner input=new Scanner(new File(filepath));
		Node pre=null;
		while(input.hasNextLine()) {
			String temp=input.nextLine();
			if(input.hasNextLine()) {
				temp+="\n";
			}
			char[] arr=temp.toCharArray();
			for(int i=0;i<arr.length;i++) {
				numChars++;
				curPos++;
				Node node=new Node(arr[i]);
				node.prev=pre;
				if(pre!=null) {
					pre.next=node;
				}
				else{
					head=node;
				}
				pre=node;
			}
		}
		tail=pre;
	}
	/**
	 *  get the curPos
	 *  @return return curPos
	 */
	public int getCursorPosition() {
		return curPos;
	}
	/**
	 *  get the number of char
	 *  @return return the number of char
	 */
	public int size() {
		return numChars;
	}
	/**
	 *  move the cursor one character right, should be ignored when curPos=numChars
	 */
	public void moveRight() {
		if(curPos<numChars) {
			curPos++;
			cur=cur.next;
		}
	}
	/**
	 *  move the cursor one character left, should be ignored when curPos=numChars0
	 */
	public void moveLeft() {
		if(curPos>0) {
			if(cur!=null) {
				curPos--;
				cur=cur.prev;
			}else {
				curPos--;
				cur=tail;
			}
		}
	}
	/**
	 *  move the cursor to the head
	 */
	public void moveToHead() {
		if(curPos>0) {
			curPos=0;
			cur=head;
		}
	}
	/**
	 *  move the cursor to the tail
	 */
	public void moveToTail() {
		if(numChars>0) {
			curPos=numChars;
			cur=null;
		}
	}
	/**
	 * insert a char in curPos, cur remains while curPos increments
	 * @param a char to be inserted
	 */
	public void insert(char c) {
		Node temp=new Node(c);
		if(numChars==0) {
			numChars++;
			curPos++;
			head=temp;
			tail=temp;
			return;
		}
		numChars++;
		curPos++;
		if(cur!=null) {
			if(cur.prev!=null) {
				cur.prev.next=temp;
				temp.prev=cur.prev;
				temp.next=cur;
				cur.prev=temp;
			}else {
				temp.next=cur;
				cur.prev=temp;
				head=temp;
			}
		}else {
			tail.next=temp;
			temp.prev=tail;
			tail=temp;
		}
	}
	/**
	 *  delete the node that cur points to
	 */
	public void delete() {
		if(cur!=null) {
			if(cur.prev!=null) {
				cur.prev.next=cur.next;
			}else {
				head=cur.next;
			}
			if(cur.next!=null) {
				cur.next.prev=cur.prev;
			}else {
				tail=cur.prev;
			}
			cur=cur.next;
			numChars--;
		}
	}
	/**
	 *  remove the Node before the cur
	 */
	public void backspace() {
		if(cur!=null) {
			if(cur.prev!=null) {
				if(cur.prev.prev!=null) {
					cur.prev.prev.next=cur;
					cur.prev=cur.prev.prev;
				}else {
					cur.prev=null;
					head=cur;
				}
			}else {
				head=head.next;
				if(head!=null)
				 head.prev=null;
			}
		}else if(cur==null&&tail!=null){
			tail=tail.prev;
			if(tail!=null)
			  tail.next=null;
		}
		if(curPos>0) {
			curPos--;
			numChars--;
			if(curPos==0) {
				head=cur;
			}
		}
	}
	/**
	 *  convert the current text editor to a string
	 */
	public String toString() {
		String res="";
		if(head==null) {
			return res;
		}
		Node current=head;
		while(current!=null) {
			res+=""+current.data;
			current=current.next;
		}
		return res;
	}
	/**
	 *  clear the editor
	 */
	public void clear() {
		numChars=0;
		curPos=0;
		cur=null;
		head=null;
		tail=null;
	}
	/**
	 *  export the editor content to particular file with given path
	 */
	public void export(String savepath) throws FileNotFoundException {
		PrintStream ps=new PrintStream(new File(savepath));
		ps.println(this.toString());
	    ps.close();
	}
	/**
	 *  save the current editor if it is different from last saved one
	 */
	public void save() {
		String res=this.toString();
		if(!res.equals(savedVersions.top())) {
			savedVersions.push(res);
		}
	}
	/**
	 *  revert the contents to last saved contents
	 */
	public void undo() {
		if(!savedVersions.isEmpty()) {
			String temp=savedVersions.pop();
			numChars=0;
			curPos=0;
			cur=null;
			head=null;
			tail=null;
			char[] arr=temp.toCharArray();
			for(int i=0;i<arr.length;i++) {
				this.insert(arr[i]);
			}
		}
	}
	
}
