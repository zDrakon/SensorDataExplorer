import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	public static double[][] sampleData;
	public static String[] datafiles = { "data/Alejandro/20StepsLeftHand.csv", "data/Alejandro/20StepsRightAnkle.csv",
			"data/Alejandro/20StepsRightBackPocket.csv",
			"data/Evan/anklewalk_11steps_after5seconds_before5seconds_straightpath.csv",
			"data/Evan/anklewalk_31steps_after5seconds_withturns.csv", "data/Evan/armwalk_40steps_after_5_seconds.csv",
			"data/Evan/handwalk_17steps_after_5_seconds.csv", "data/Warren/accel18step11secLeftArm.csv",
			"data/Warren/accel18step11secLeftPocket.csv", "data/Warren/accel18steps11secLeftAnkle.csv",
			"data/Warren/accel18steps11secRightAnkle.csv", "data/Warren/accel18steps11secRightArm.csv",
			"data/Warren/accel18steps11secRightPocket.csv" };

	public static String videofile = "data/walkingSampleData.mp4";

	public static int[] columns = new int[] { 3, 4, 5 };

	public static int fileNum = 2;

	public static CSVData dataset = new CSVData(datafiles[fileNum], 1);

	public static double[] times = dataset.getColumn(0);

	public static double[][] accelerationData;

	public static void main(String[] args) {
		// Create data set
		CSVData dataset = new CSVData(datafiles[fileNum], 1);

		// Get 2d array of all data
		sampleData = dataset.getData();

		double[][] accel = ArrayHelper.extractColumns(sampleData, columns);
		double[] mags = StepCounter.calculateMagnitudesFor(accel);

		Plot2DPanel plot = new Plot2DPanel();

		// add a line plot to the PlotPanel
		plot.addLinePlot("Magnitudes", mags);

		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

}
