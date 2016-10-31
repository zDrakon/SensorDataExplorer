
public class StepCounter {

	/***
	 * 
	 * @param sensorData
	 *            an array with n rows, and 3 columns. Each row represents a
	 *            different measurement, and each column represents a different
	 *            axis of the sensor.
	 * @return
	 */

	public static double[] calculateMagnitudesFor(double[][] sensorData) {
		double[] magnitudes = new double[sensorData.length];

		for (int i = 0; i < magnitudes.length; i++) {
			magnitudes[i] = calculateMagnitude(sensorData[i][0], sensorData[i][1], sensorData[i][2]);

		}
		return magnitudes;

	}

	/***
	 * takes in three vectors and returns the magnitude
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return a double representing the magnitude from the 3 inputs
	 */
	public static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/***
	 * returns number of steps by finding peaks greater than the standard
	 * deviation
	 * 
	 * @param times
	 * @param sensorData
	 * @return
	 */
	public static double[] countSteps(double[] times, double[][] sensorData) {
		double[] magnitudesOfAccelerations = calculateMagnitudesFor(sensorData);
		double[] count = new double[times.length];

		double threshold = calculateMean(magnitudesOfAccelerations)
				+ (calculateStandardDeviation(magnitudesOfAccelerations));
		double minthreshold = calculateMean(magnitudesOfAccelerations)
				- (calculateStandardDeviation(magnitudesOfAccelerations));
		for (int i = 1; i < magnitudesOfAccelerations.length - 1; i++) {
			if (isPeak(magnitudesOfAccelerations, i) && magnitudesOfAccelerations[i] >= threshold
					&& magnitudesOfAccelerations[i + 1] <= minthreshold) {

				count[i]++;

			}
		}

		return count;
	}

	/***
	 * turns the times in the first column of the array into elapsed time
	 * 
	 * @param sensorData
	 */
	public static void replaceAbswithElapsed(double[][] sensorData) {
		for (int row = 1; row < sensorData.length; row++) {
			sensorData[row][0] -= sensorData[1][0];
		}
		sensorData[1][0] = 0;
	}

	/***
	 * Returns true or false comparing a index to 2 other indexes (one before
	 * and after) to determine if it is a peak
	 * 
	 * @param magnitudesOfAccelerations
	 * @param i
	 * @return true or false, indicate peak.
	 */
	private static boolean isPeak(double[] magnitudesOfAccelerations, int i) {
		if (magnitudesOfAccelerations[i - 1] < magnitudesOfAccelerations[i]
				&& magnitudesOfAccelerations[i + 1] < magnitudesOfAccelerations[i]) {
			return true;
		}
		return false;
	}

	/***
	 * Returns the mean of an array
	 * 
	 * @param arr
	 * @return mean of an array
	 */
	public static double calculateMean(double[] arr) {
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum / (double) arr.length;

	}

	/***
	 * Returns STDEV of an array with a given mean
	 * 
	 * @param arr
	 * @param mean
	 * @return Standard deviation of an array given the mean
	 */
	public static double calculateStandardDeviation(double[] arr, double mean) {
		double sum = 0;
		double stddev = 0;
		for (int i = 0; i < arr.length; i++) {
			stddev = (mean - arr[i]) * (mean - arr[i]);
			sum += stddev;
		}
		stddev = sum / (double) ((arr.length - 1) - 1);
		return Math.sqrt(stddev);

	}

	/***
	 * Cleaner method for calculating STDEV
	 * 
	 * @param arr
	 * @return
	 */
	public static double calculateStandardDeviation(double[] arr) {
		return calculateStandardDeviation(arr, calculateMean(arr));
	}

	/***
	 * 
	 * Returns the # of steps
	 * 
	 * 
	 * 
	 * @param counts
	 * 
	 * @return
	 * 
	 */

	public static int numSteps(double[] counts) {

		int count = 0;

		for (int i = 0; i < counts.length; i++) {

			if (counts[i] == 1) {

				count++;

			}

		}

		return count;

	}

}
