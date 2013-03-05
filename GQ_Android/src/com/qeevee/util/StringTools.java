package com.qeevee.util;

import java.util.Locale;
import edu.bonn.mobilegaming.geoquest.Variables;
import android.util.Log;

public class StringTools {

	public static String caseUp(String original) {
		return original.substring(0, 1).toUpperCase(Locale.ENGLISH)
				+ original.substring(1);
	}
	
	public static String trim(String original) {
	    return original.replaceAll("\\s+",
		    " ").trim();
	}
	
	
	/**
	 * replaces variables in a string with their respective value.
	 * example: "Hello $$(playername)." will be changes to "Hello Hans."
	 */
	public static String replaceVariables(String original){
		String newString = "";
		String oldString = original;
		
		while (true) {
			int i = oldString.indexOf("$$(");
			if(i == -1) break;
			
			newString = newString + oldString.substring(0, i);
			oldString = oldString.substring(i+3);
			
			i = oldString.indexOf(")");
			if(i == -1){
				Log.d("StringTools","Cannot replace variables in String: '"+original+"'. No closing brackets found.");
				return original;
			}
			
			newString = newString + Variables.getValue(oldString.substring(0,i));
			oldString = oldString.substring(i+1);
		}
		newString = newString + oldString;
		
		return newString;
	}

}
