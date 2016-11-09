public class Tester {

	public static String[] datafiles = { "data/Alejandro/20StepsLeftHand.csv", "data/Alejandro/20StepsRightAnkle.csv",
			"data/Alejandro/20StepsRightBackPocket.csv",
			"data/Evan/anklewalk_11steps_after5seconds_before5seconds_straightpath.csv",
			"data/Evan/anklewalk_31steps_after5seconds_withturns.csv", "data/Evan/armwalk_40steps_after_5_seconds.csv",
			"data/Evan/handwalk_17steps_after_5_seconds.csv", "data/Warren/accel18step11secLeftArm.csv",
			"data/Warren/accel18step11secLeftPocket.csv", "data/Warren/accel18steps11secLeftAnkle.csv",
			"data/Warren/accel18steps11secRightAnkle.csv", "data/Warren/accel18steps11secRightArm.csv",
			"data/Warren/accel18steps11secRightPocket.csv" };

	public static String videofile = "data/walkingSampleData.mp4";

	public static int[] columns = new int[] { 1, 2, 3 };

	public static double[][] sampleData;

	public static int fileNum = 12;

	public static CSVData dataset = new CSVData(datafiles[fileNum], 1);

	public static double[] times = dataset.getColumn(0);

	public static double[][] accelerationData;

	public static void main(String[] args) {

		sampleData = dataset.getData();

		accelerationData = ArrayHelper.extractColumns(sampleData, columns);

		for (int i = 0; i < 100; i++) {
			doITAgain(i);
		}
		original(datafiles[fileNum]);
	}

	public static void doITAgain(int i) {
		doIt(datafiles[fileNum], i);
	}

	// Create data set
	public static void doIt(String datafile, int n) {

		double[] counts = StepCounter.countSteps(times, accelerationData, n);

		System.out.println(StepCounter.numSteps(counts) + " - " + n);

	}

	public static void original(String datafile) {
		CSVData dataset = new CSVData(datafile, 1);
		double[][] sampleData;
		sampleData = dataset.getData();

		double[] times = dataset.getColumn(0);

		double[][] accelerationData = ArrayHelper.extractColumns(sampleData, columns);

		double[] counts = StepCounter.naiveCountSteps(times, accelerationData);

		System.out.println(StepCounter.numSteps(counts));
	}

}