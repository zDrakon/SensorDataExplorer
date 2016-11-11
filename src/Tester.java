public class Tester {

	public static String[] datafiles = { "data/Alejandro/20StepsLeftHand.csv", "data/Alejandro/20StepsRightAnkle.csv",
			"data/Alejandro/20StepsRightBackPocket.csv",
			"data/Evan/anklewalk_11steps_after5seconds_before5seconds_straightpath.csv",
			"data/Evan/anklewalk_31steps_after5seconds_withturns.csv", "data/Evan/armwalk_40steps_after_5_seconds.csv",
			"data/Evan/handwalk_17steps_after_5_seconds.csv", "data/Evan/pocketwalk_15steps_after_5_seconds.csv",
			"data/Warren/accel18step11secLeftArm-OUT.csv", "data/Warren/accel18step11secLeftPocket-OUT.csv",
			"data/Warren/accel18steps11secLeftAnkle-OUT.csv", "data/Warren/accel18steps11secRightAnkle-OUT.csv",
			"data/Warren/accel18steps11secRightArm-OUT.csv", "data/Warren/accel18steps11secRightPocket-OUT.csv" };

	public static String videofile = "data/walkingSampleData.mp4";

	public static int[] columns = new int[] { 3, 4, 5 }; // Columns subject to
															// change due to
															// each file being
															// unique

	public static double[][] sampleData;

	public static int fileNum = 2; // choose what datafiles index file a tester
									// will want

	private static final int SECOND_ROW_OF_FILE = 1; // The 2nd row of a file is
														// the 1st row of
														// the double 2D array

	private static final int FIRST_COLUMN_OF_DATA = 0; // The 1st column of a
														// file is under the
														// first column name row
														// is typically the
														// elapsed time column

	public static CSVData dataset = new CSVData(datafiles[fileNum], SECOND_ROW_OF_FILE);

	public static double[] times = dataset.getColumn(FIRST_COLUMN_OF_DATA);

	public static double[][] accelerationData;

	public static final int ACCEPTABLE_ERROR_NUM_STEPS = 3; // In this
															// experiment we
															// considered a
															// failed reading of
															// steps if the # of
															// steps counted
															// exceeded + or - 3
															// steps. (24 would
															// not be counted if
															// the actual steps
															// was 20)

	public static final int ACTUAL_STEPS = 20; // Should be clear by the
												// data_description.txt or the
												// name of the .csv file tested

	// These variables are subject to change depending on how vast the dataset
	// may be to find the optimal range value ('n')
	public static final int START_RANGE_VALUE = 1;

	public static final int END_RANGE_VALUE = 1000;

	public static boolean FAIL = false;

	/***
	 * Rurns both methods to print out.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		sampleData = dataset.getData();
		accelerationData = ArrayHelper.extractColumns(sampleData, columns);

		for (int i = START_RANGE_VALUE; i < END_RANGE_VALUE; i++) {
			returnStepsWithN(datafiles[fileNum], i);
		}
		if (FAIL) {
			System.out.println("FAIL");
		}

		returnNaiveAmountOfSteps(datafiles[fileNum]);
	}

	/***
	 * Prints the number of steps using adaptive threshold given an input range
	 * of 'n'
	 * 
	 * @param datafile
	 * @param n
	 */
	public static void returnStepsWithN(String datafile, int n) {
		double[] counts = StepCounter.countSteps(times, accelerationData, n);
		if (StepCounter.numSteps(counts) <= ACTUAL_STEPS - ACCEPTABLE_ERROR_NUM_STEPS
				&& StepCounter.numSteps(counts) >= ACTUAL_STEPS + ACCEPTABLE_ERROR_NUM_STEPS) {
			System.out.println("# of Steps - Range value: " + StepCounter.numSteps(counts) + " - " + n);
		} else {
			FAIL = true;
		}
	}

	/***
	 * Prints out the naive algorithim's guess of how many steps The '0' in the
	 * method is to indicate that it will use the naive threshold
	 * 
	 * @param datafile
	 */
	public static void returnNaiveAmountOfSteps(String datafile) {

		double[] counts = StepCounter.countSteps(times, accelerationData, 0);

		System.out.println("Naive # of Steps: " + StepCounter.numSteps(counts));
	}

}