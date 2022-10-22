package com.util.getvalues;

import java.util.Scanner;
import java.util.logging.Logger;

public class InputMethods {

		private Logger logger=Logger.getLogger(InputMethods.class.getName());
		private Scanner input=new Scanner(System.in);
		
		public int isInteger() {
			boolean loop=true;
			int number=0;
			while(loop) {
				if(input.hasNextInt()) {
					number=input.nextInt();
					loop=false;
				}
				else {
					logger.warning("Enter only integer type");
					input.next();
				}
			}
			return number;
		}

		public String getString() {
			String name = null;
			name=input.next();
			input.nextLine();
			return name;
		}
		
		public double isDouble() {

			boolean loop=true;
			double number=0;
			while(loop) {
				if(input.hasNextDouble()) {
					number=input.nextInt();
					loop=false;
				}
				else {
					logger.warning("Enter only double type");
					input.next();
				}
			}
			return number;

		}
		
		public long isLong() {
			boolean loop=true;
			long number=0;
			while(loop) {
				if(input.hasNextLong()) {
					number=input.nextInt();
					loop=false;
				}
				else {
					logger.warning("Enter only long type");
					input.next();
				}
			}
			return number;

		}

		public boolean isBoolean() {
			boolean value = false;
			boolean loop=true;
			while(loop) {
				if(input.hasNextBoolean()) {
					value=input.nextBoolean();
					loop=false;
				}
				else {
					System.out.println("Enter only Boolean type.");
					input.next();
				}
			}
			return value;
		}
		
		public float isFloat() {
			boolean loop=true;
			float number=0;
			while(loop) {
				if(input.hasNextFloat()) {
					number=input.nextFloat();
					loop=false;
				}
				else {
					System.out.println("Enter only Float type.");
					input.next();
				}
			}
			return number;
		}
	
		public void close() {
			input.close();
		}
}
