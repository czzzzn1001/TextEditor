package main;
/**
 * Explanation: Implementation of text editor
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Oct 7, 2020
 * COSI 21A PA1
 */
public class Stack<T> {

	public T[] stack;
    public int index;
    //index indicates the number of elements in the stack
	@SuppressWarnings("unchecked")
	/**
	 *  constructor of Editor
	 */
	public Stack() {
		// uncomment this line and replace <your initial size goes here> with the initial capacity of your internal array
		this.stack = (T[]) new Object[100];
		this.index=0;
	}
	/**
	 *  push element x to the stack
	 */
	@SuppressWarnings("unchecked")
	public void push(T x) {
       if(stack.length==this.index) {
    	   T[] temp=stack;
    	   stack=(T[]) new Object[temp.length+100];
    	   for(int i=0;i<temp.length;i++) {
    		   stack[i]=temp[i];
    	   }
       }
       stack[index]=x;
       index++;
	}
	/** return and remove the stack top
	 *  @return the top of the stack
	 */
	public T pop() {
		if(index==0) {
			throw new IllegalStateException("Empty stack!");
		}
		index--;
		return stack[index];
	}
	/** return the stack top
	 *  @return the top of the stack
	 */
	public T top() {
		if(index==0) {
			return null;
		}
		return stack[index-1];
	}
	/** return the size of stack
	 *  @return the size of the stack
	 */
	public int size() {
		
		return index;
	}
	/** return whether the stack is empty
	 *  @return whether the size of the stack is 0
	 */
	public boolean isEmpty() {
		
		return index==0;
	}
	/** convert all the elements in stack to string
	 *  @return a string representation of contents of Stack
	 */
	public String toString() {
		String res="";
		for(int i=index-1;i>=0;i--) {
			res+=stack[i]+"\n";
		}
		return res;
	}
	
}
