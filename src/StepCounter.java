
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
	public static double[] countSteps(double[] times, double[][] sensorData, int range) {
		double[] magnitudesOfAccelerations = calculateMagnitudesFor(sensorData);
		double[] count = new double[times.length];

		for (int i = 1; i < magnitudesOfAccelerations.length - 1; i++) {
			double threshold = findThreshold(magnitudesOfAccelerations, range, i);
			// double threshold = calculateMean(magnitudesOfAccelerations)
			// + calculateStandardDeviation(magnitudesOfAccelerations);
			if (isPeak(magnitudesOfAccelerations, i)) {
				if (magnitudesOfAccelerations[i] >= threshold) {

					count[i]++;

				}
			}
		}

		return count;
	}

	public static double[] naiveCountSteps(double[] times, double[][] sensorData) {
		double[] magnitudesOfAccelerations = calculateMagnitudesFor(sensorData);
		double[] count = new double[times.length];

		for (int i = 1; i < magnitudesOfAccelerations.length - 1; i++) {

			double threshold = calculateMean(magnitudesOfAccelerations)
					+ calculateStandardDeviation(magnitudesOfAccelerations);
			if (isPeak(magnitudesOfAccelerations, i)) {
				if (magnitudesOfAccelerations[i] >= threshold) {

					count[i]++;

				}
			}
		}
		return count;
	}

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

	public static double findThreshold(double[] data, int range, int index) {
		double output[] = new double[(index + range) - (index - range)];
		int j = 0;
		for (int i = (Math.max(index - range, 0)); i < Math.min(index + range, data.length - 1); i++) {
			output[j] = data[i];
			j++;
		}
		return calculateStandardDeviation(output) + calculateMean(output);

	}
}