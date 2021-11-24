package permutation_sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Badri_Dahal
 *
 */
public class Permutation_Sorting_Solution {
	
	private int maxNumberIndex = 0;
	private int insertComparison = 0;
	private int mergeComparison = 0;
	private int quickComparison = 0;
	private int countPermutation = 0;

	public static void main(String[] args) {
		Permutation_Sorting_Solution main = new Permutation_Sorting_Solution();
		main.runProgram();
	}
	
	
	public void runProgram() {

		Scanner myNum = new Scanner(System.in);
		System.out.println("Enter your permutation number (Number only): ");
		
		// Here, we validate if the input is an integer or not, so pass false as we don't want to limit integer value
		int choiceNumber = validateInput(myNum, false);
		
		System.out.println("Permutation Methods Available: "
						 + "\n1. Recursive nextPermutation Method "
						 + "\n2. Iterative nextPermutationIter Method "
						 + "\nChoose a method to perform permutation(1 or 2): ");
		
		//For this we validate if the input is integer or not, and then limit the integer value to 1 or 2 by passing true
		int secondChoice = validateInput(myNum, true);
		
		myNum.close();
		int[] mainArray = new int[choiceNumber];
		
		// make initial array
		for (int i=0; i<choiceNumber; i++)
			mainArray[i] = i+1;
		
		System.out.println("Initial Array: " + Arrays.toString(mainArray));
		
		this.runPermutationLoop(mainArray, secondChoice);
		
		System.out.println("Final Array: " + Arrays.toString(mainArray));
		System.out.println("Total Permutation: " + this.countPermutation);
		System.out.println("\n***** Insert Comparison *****");
		System.out.println("Total Insert Comparison: " + this.insertComparison);
		System.out.println("Average Insert Comparison: " + this.insertComparison / this.countPermutation);
		System.out.println("\n***** Merge Comparison *****");
		System.out.println("Total Merge Comparison: " + this.mergeComparison);
		System.out.println("Average Merge Comparison: " + this.mergeComparison / this.countPermutation);
		System.out.println("\n***** Quick Comparison *****");
		System.out.println("Total Quick Comparison: " + this.quickComparison);
		System.out.println("Average Quick Comparison: " + this.quickComparison / this.countPermutation);
	}


	/**
	 * @param myNum
	 * @param limitInteger
	 * @return
	 */
	private int validateInput(Scanner myNum, boolean limitInteger) {
		while (!myNum.hasNextInt()) {
			System.out.println("Invalid Character Choice!!");
			System.out.println("Type 1 or 2: ");
			myNum.nextLine();
		}
		
		int choice = myNum.nextInt();
		myNum.nextLine();
	
		if (limitInteger) {
			while (choice < 1 || choice > 2) {
				System.out.println("Invalid Option!!");
				System.out.println("Type 1 or 2: ");
				choice = myNum.nextInt();
				myNum.nextLine();
			}
		}
		return choice;
	}
	
	/**
	 * @param mainArray
	 * @param choice
	 */	
	// Based on user choice, this function runs the permutation function as long as the main array has no more permutation
	private void runPermutationLoop(int[] mainArray, int choice) {
		
		if (choice == 1) System.out.println("\n******************Recursive Call Method****************");
		if (choice == 2) System.out.println("\n******************Iterative Call Method****************");
		
		do {
			this.countPermutation++;
			int[] insertArray = new int[mainArray.length];
			int[] mergeArray = new int[mainArray.length];
			int[] quickArray = new int[mainArray.length];

			for(int i=0; i<mainArray.length; i++) {
				insertArray[i] = mainArray[i];
				mergeArray[i] = mainArray[i];
				quickArray[i] = mainArray[i];
			}
			this.insertionSort(insertArray);
			this.mergeSort(mergeArray);
			this.quickSort(quickArray);

		} 
		while (choice == 1 ? this.nextPermutation(mainArray) : this.nextPermutationIter(mainArray));
		
	}

	/**
	 * @param array
	 * @param f_index
	 * @param l_index
	 */	
	// This function helps to swap items between two indexes
	private void swapItem(int[] array, int f_index, int l_index) {
		int temp = array[f_index];
		array[f_index] = array[l_index];
		array[l_index] = temp;
	}
	
	
	/**
	 * @param array
	 * @return
	 */	
	//This method finds maximal value in a array and sets the index of the max number to global variable
	private int findMax(int[] array) {
		int max = array[0];
		for(int i = 1; i < array.length; i++)
			if (array[i] > max) {
				max = array[i];
				this.maxNumberIndex = i;
			}
		return max;
	}
	
	/**
	 * @param array
	 * @return
	 */	
	//Recursive nextPermutation method
	//This is a recursive function that returns possible next permutation of an array based on some condition
	private boolean nextPermutation(int[] numArray) {
		if(numArray.length < 2) 
			return false;
		
		int maxNumber = findMax(numArray);
		
		if (maxNumber != numArray[0]) 
		{			
			swapItem(numArray, maxNumberIndex, maxNumberIndex - 1);			
			return true;
 		}
		
		if (maxNumber == numArray[0]) 
		{
			int[] newArray = new int[numArray.length - 1];
			for (int i = 0; i < numArray.length - 1; i++)
				newArray[i] = numArray[i+1];
			
			// recursive call
			if (nextPermutation(newArray)) 
			{
				for (int i = 0; i < numArray.length - 1; i++) {
					numArray[i] = newArray[i];
				}
				numArray[numArray.length - 1] = maxNumber;			
				return true;
				
			} 
			return false;
		}		
		return false;
	}
	
	
	/**
	 * @param array
	 * @return
	 */
	//Iterative nextPermutation Method
	// This function uses loop to find next possible permutation of given array
	private boolean nextPermutationIter(int[] array) {
		if(array.length < 2) 
			return false;
		
		int maxNumber = findMax(array);
		
		if (maxNumber != array[0]) 
		{			
			swapItem(array, maxNumberIndex, maxNumberIndex - 1);			
			return true;
 		}
		
		/*
		 * if maxNumber is at first position,
		 * first, we create a new array to hold max numbers that are at first position
		 * if all the max numbers are in descending order, the new array is full and we return false
		 * else we hold a boundary position to indicate the start point for remaining numbers
		 * then, we use the remaining numbers in the array and use same logic as before to swap the max number and its below number
		 * then, we loop over our main array such that we put the numbers below our boundary to the last of array swapping with the remaining numbers 
		 */
		if (maxNumber == array[0]) 
		{
			int[] maxNumHold = new int[array.length];
			maxNumHold[0] = maxNumber;
			int boundary = 1;
			
			while (boundary < array.length) {
				if (maxNumber-1 == array[boundary]) {
					maxNumber--;
					maxNumHold[boundary] = array[boundary++];
				}
				else 
					break;
			}
			
			if (boundary == array.length) 
				return false;
			
			int newArrayMaxNum = maxNumHold[boundary - 1] - 1;
			for (int i = boundary; i < array.length; i++)
			{
				if (array[i] == newArrayMaxNum)
					swapItem(array, i, i-1);
			}
			
			while (boundary > 0) {
				for (int i = boundary; i < array.length; i++) {
					swapItem(array, i, i-1);
				}
				boundary--;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * @param intArray
	 */	
	//Insertion Sort	
	private void insertionSort(int[] intArray) {
//		System.out.println("/*************Start***************/");
		int count = 0;
		for (int i = 1; i < intArray.length; i++) {
			int current = intArray[i];
			count++;
			int j = i-1;
			while ( j>=0 && current < intArray[j]) {
				intArray[j+1] = intArray[j];
				if (--j >= 0)
					count++;
			}
			intArray[j+1] = current;				
			
		}
//		System.out.println("Comparison: " + count);
		this.insertComparison += count;
	}
	
	/**
	 * @param intArray
	 */
	//Merge Sort	
	private void mergeSort(int[] intArray) {
		if (intArray.length < 2) 
			return;
		int midIndex = intArray.length / 2;
		
		int[] left = new int[midIndex];
		for (int i = 0; i < midIndex; i++) 
			left[i] = intArray[i];
		
		int[] right = new int[intArray.length - midIndex];
		for (int i = midIndex; i < intArray.length; i++)
			right[i - midIndex] = intArray[i];
		
		mergeSort(left);
		mergeSort(right);
		
		this.mergeComparison += merge(left, right, intArray);
	}
	
	/**
	 * @param left
	 * @param right
	 * @param result
	 * @return
	 */
	// This function helps to merge left and right sorted arrays into our initial array
	private int merge(int[] left, int[] right, int[] result) {
		int i = 0, j = 0, k = 0;
		int count = 0;
		
		while (i< left.length && j < right.length) {
			count++;
			
			if(left[i] <= right[j])
				result[k++] = left[i++];		
			else 
				result[k++] = right[j++];
				
		}
		
		while (i < left.length) {
			result[k++] = left[i++];
		}
		
		while (j < right.length ) {
			result[k++] = right[j++];
		}
		
		return count;
	}
	
	
	// Main Quick Sort function
	private void quickSort(int[] intArray) {
		quickSort(intArray, 0, intArray.length - 1);
	}	
	
	//Quick Sort with start and end parameters to call recursively 
	private void quickSort(int[] intArray, int start, int end) {
		if (start >= end) 
			return;
		int boundary = partition(intArray, start, end);
		
		quickSort(intArray, start, boundary - 1);
		quickSort(intArray, boundary + 1, end);
	}	
	
	/**
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	//Here we divide right and left partition based on pivot element
	// Items smaller than pivot element are shifted to left and greater than pivot are shifted to right
	private int partition(int[] array, int start, int end) {
		int count = 0;
		int pivot  = array[end];
		int boundary = start - 1;
		
		for (int i = start; i <= end; i++) {		
			count++;
			if (array[i] <= pivot) {
				swapItem(array, i, ++boundary);
			}
		}
		this.quickComparison += count;
		
		return boundary;
	}	
	
}
