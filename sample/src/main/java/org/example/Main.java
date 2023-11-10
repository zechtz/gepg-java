package org.example;

import com.abtassociates.sample_library.GepgUtils;

public class Main {
	public static void main(String[] args) {
		String bill = "sampleBill";
		System.out.println("Control Number : " + GepgUtils.requestControlNumber(bill));
	}
}






