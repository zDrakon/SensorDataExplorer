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

	public static int[] columns = new int[] { 3, 4, 5 }; // Columns subject to
															// change due to
															// each file being
															// unique

	public static double[][] sampleData;

	public static int fileNum = 2; // choose what datafiles index file a tester
									// will want

	public static CSVData dataset = new CSVData(datafiles[fileNum], 1);

	public static double[] times = dataset.getColumn(0);

	public static double[][] accelerationData = ArrayHelper.extractColumns(sampleData, columns);;

	public static void main(String[] args) {
		sampleData = dataset.getData();

		for (int i = 0; i < 1000; i++) {
			returnStepsWithN(datafiles[fileNum], i);
		}
		returnNaiveAmountOfSteps(datafiles[fileNum]);
	}

	public static void returnStepsWithN(String datafile, int n) {

		double[] counts = StepCounter.countSteps(times, accelerationData, n);

		System.out.println("# of Steps - Range value: " + StepCounter.numSteps(counts) + " - " + n);

	}

	public static void returnNaiveAmountOfSteps(String datafile) {
		CSVData dataset = new CSVData(datafile, 1);
		double[][] sampleData;
		sampleData = dataset.getData();

		double[] times = dataset.getColumn(0);

		double[][] accelerationData = ArrayHelper.extractColumns(sampleData, columns);

		double[] counts = StepCounter.countSteps(times, accelerationData, 0);

		System.out.println("Naive # of Steps: " + StepCounter.numSteps(counts));
	}

}