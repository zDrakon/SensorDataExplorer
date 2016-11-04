import java.util.Arrays;

public class Tester {

	public static double[][] sampleData;

	public static String datafile = "data/64StepsInHandJogging-out.txt";

	public static String videofile = "data/walkingSampleData.mp4";

	public static void main(String[] args) {

		// Create data set

		CSVData dataset = new CSVData(datafile, 1);

		sampleData = dataset.getData();

		// Extract columns for time, and x acceleration, y acceleration, z

		// acceleration

		double[] times = dataset.getColumn(0);

		double[][] accelerationData = ArrayHelper.extractColumns(sampleData, new int[] { 1, 2, 3 });

		double[] accelerationMagnitudes = StepCounter.calculateMagnitudesFor(accelerationData);

		double[][] displayData = ArrayHelper.combineAsColumns(times, accelerationMagnitudes);

		double[] counts = StepCounter.countSteps(times, accelerationData, 2);

		double[][] correspondStepsToTime = ArrayHelper.combineAsColumns(times, counts);

	//	DataExplorer.runDataExplorer(displayData, new String[] { "time",

//				"mags" }, videofile);

		System.out.println(Arrays.toString(ArrayHelper.extractColumn(correspondStepsToTime, 0)) + "\n"

				+ Arrays.toString(ArrayHelper.extractColumn(correspondStepsToTime, 1)));

		System.out.println(StepCounter.numSteps(counts));

	}

}