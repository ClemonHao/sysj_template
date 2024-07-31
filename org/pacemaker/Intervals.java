package org.pacemaker;

public class Intervals {
	public static final int P = 110;
	public static final int QRS = 100;
	public static final int AVI = 150;
	public static final int VAI = 850;
	public static final int PVARP = 350;
	public static final int MSI = 500;

	public static void printA(){
		System.out.println("======================================================================================");
		System.out.println("Scenario A - shows a situiation in which the pacemaker paces after a standard time \n"+
											 "             interval in both chambers. This is the reaction when no intrinsic heart\n"+
											 "             activity is detected");
		System.out.println("======================================================================================");
	}

	public static void printB(){
		System.out.println("======================================================================================");
		System.out.println("Scenario B - shows a situiation in which the pacemaker paces in the atrial chamber   \n"+
											 "             after a standard interval, while the ventricular pacing is inhibited due\n"+
											 "             to a sensing of intrinsic activity from the ventricle									 ");
		System.out.println("======================================================================================");
	}

	public static void printC(){
		System.out.println("======================================================================================");
		System.out.println("Scenario C - shows a situiation in which intrinsic atria activity is sensed, pacing  \n"+
											 "             inhibited in the atrial chamber but occurs in the ventricular chamber   \n"+
											 "             after AVI (due to a lack of intrinsic ventricular activity)             ");
		System.out.println("======================================================================================");
	}

	public static void printD(){
		System.out.println("======================================================================================");
		System.out.println("Scenario D - represents the case where both pacing activities are inhibited due to a \n"+
											 "             sensing of intrinstic activities in both chambers												 ");
		System.out.println("======================================================================================");
	}

	public static void terminate(){System.exit(1);}
	
}
