/**
 * Conner Thomas
 * CSE 373
 * 10-1-2013
 * 
 * Implementation of a stack for doubles using an array
 */

import java.util.EmptyStackException;

public class ArrayStack implements DStack {
	
	private double[] arr = new double[10];
	double[] newArr;
	private int elements = 0;
	
	/**
	 * increaseSize is a helper method that effectively doubles the length of the internal array
	 */
	private void increaseSize() {
		newArr = new double[arr.length * 2];
		for (int i=0; i < arr.length; i++){
			newArr[i] = arr[i];
		}
		arr = newArr;
	}
	
	/** 
	 * decreaseSize is a helper method that cuts the array size in half if more than 3/4 of it is empty
	 */
	private void decreaseSize() {
		newArr = new double[arr.length / 2];
		for (int i=0; i < newArr.length;){
			newArr[i] = arr[i];
			i++;
		}
		arr = newArr;
	}
	
	/**
	 * checkSize is a helper method that checks if the array is out of space or 3/4 empty,
	 * and if it is then calls the method to increase or decrease the size
	 */
	private void checkSize() {
		if (elements == arr.length)
			increaseSize();
		else if (elements < ((double) arr.length * .25))
			decreaseSize();
	}
	
	/**
	 * isEmpty checks if the stack is empty or not
     * @return true if stack is empty, false if stack is not
     */
	public boolean isEmpty() {
		return elements == 0;
	} 
	
	/**
     * push adds a value to the top of the stack
     */
	public void push (double d) {
		checkSize();
		arr[elements] = d;
		elements++;
	}
	
	/**
     * pop takes the value at the top of the stack, returns it and deletes it from the stack
     * @return the deleted value
     * @throws EmptyStackException if stack is empty
     */	
	public double pop() {
		checkSize();
		double d;
		if (this.isEmpty())
			throw new EmptyStackException();
		else {
			d = arr[elements - 1];
			elements--;
			return d;
		}
	}
	
	/**
     * peek returns the value at the top of the stack and does not delete it as pop does
     * @return the value at the top of the stack
     * @throws EmptyStackException if stack is empty
     */
	public double peek() {
		if (this.isEmpty())
			throw new EmptyStackException();
		else
			return arr[elements - 1];
	}
}