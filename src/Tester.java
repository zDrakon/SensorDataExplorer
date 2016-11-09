public class Tester {

	public static String[] datafiles = { "data/Alejandro/20StepsLeftHand.csv", "data/Alejandro/20StepsRightAnkle.csv",
			"20StepsRightBackPocket.csv", "data/Evan/anklewalk_11steps_after5seconds_before5seconds_straightpath.csv",
			"data/Evan/anklewalk_31steps_after5seconds_withturns.csv", "data/armwalk_40steps_after_5_seconds.csv",
			"handwalk_17steps_after_5_seconds.csv", "data/Gina/back pocket 32.csv", "data/Gina/left hand 32.csv",
			"right ankle 32.csv", "right arm 32.csv", "right thigh 32.csv" };

	public static String videofile = "data/walkingSampleData.mp4";

	public static void main(String[] args) {
		doITAgain(19);
	}

	public static void doITAgain(int i) {

	}

	// Create data set
	public static void doIt(String datafile, int n) {
		double[][] sampleData;
		CSVData dataset = new CSVData(datafile, 1);

		sampleData = dataset.getData();
		dataset.writeDataToFile(datafile, dataset.getHugeStringOfData(sampleData));

		// Extract columns for time, and x acceleration, y acceleration, z

		// acceleration

		double[] times = dataset.getColumn(0);

		double[][] accelerationData = ArrayHelper.extractColumns(sampleData, new int[] { 1, 2, 3 });
		// for (int i = 0; i < 1000; i++) {
		double[] counts = StepCounter.countSteps(times, accelerationData, n);
		// if (StepCounter.numSteps(counts) == 11) {
		// System.out.println(i);
		//
		// }
		// }

		System.out.println(StepCounter.numSteps(counts));
	}

}