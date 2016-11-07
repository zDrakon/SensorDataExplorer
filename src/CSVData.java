import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/****
 * A class to read/write numerical CSV files and allow easy access of data.
 * 
 * @author EvanYap
 *
 */
public class CSVData {
	private double[][] data;
	private String[] columnNames;
	private int numColumns;

	public CSVData(String filepath, String[] columnNames, int startRow) {
		String dataString = readFileAsString(filepath);
		String[] lines = dataString.split("\n");

		// number of data points
		int n = lines.length - startRow;
		// create storage for column names
		this.columnNames = columnNames;

		this.numColumns = columnNames.length;

		// create storage for data
		this.setData(new double[n][numColumns]);

		for (int i = 0; i < lines.length - startRow; i++) {
			String line = lines[startRow + i];
			String[] coords = line.split(",");
			for (int j = 0; j < numColumns; j++) {
				if (coords[j].endsWith("#"))
					coords[j] = coords[j].substring(0, coords[j].length() - 1);
				double val = Double.parseDouble(coords[j]);
				getData()[i][j] = val;
			}
		}

		replaceAbswithElapsed(data);
	}

	public CSVData(String filepath, int startRow) {
		String dataString = readFileAsString(filepath);
		String[] lines = dataString.split("\n");

		// number of data points
		int n = lines.length - startRow;
		// create storage for column names
		this.columnNames = lines[0].split(", ");

		this.numColumns = columnNames.length;

		// create storage for data
		this.setData(new double[n][numColumns]);

		for (int i = 0; i < lines.length - startRow; i++) {
			String line = lines[startRow + i];
			String[] coords = line.split(",");
			for (int j = 0; j < numColumns; j++) {
				if (coords[j].endsWith("\n"))
					coords[j] = coords[j].substring(0, coords[j].length() - 1);
				double val = Double.parseDouble(coords[j]);
				getData()[i][j] = val;
			}
		}

		replaceAbswithElapsed(data);
	}

	private String readFileAsString(String filepath) {
		StringBuilder output = new StringBuilder();
		try (Scanner scanner = new Scanner(new File(filepath))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				output.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	/***
	 * Returns all values of a column
	 * 
	 * @param col
	 *            the column index
	 * @return values of a column
	 */
	public double[] getColumn(int col) {
		double[] colVals = new double[getData().length];
		for (int i = 0; i < getData().length; i++) {
			colVals[i] = getData()[i][col];
		}
		return colVals;
	}

	/***
	 * Returns all values of a column
	 * 
	 * @param columnName
	 *            the name of the column
	 * @return values of a column
	 */
	public double[] getColumn(String columnName) {
		for (int i = 0; i < columnNames.length; i++) {
			if (columnNames[i].equals(columnName)) {
				return getColumn(i);
			}
		}

		return null;
	}

	/***
	 * Returns all values of a row
	 * 
	 * @param row
	 *            the row index
	 * @return values of a row
	 */
	public double[] getRow(int row) {
		return getData()[row];
	}

	/***
	 * Returns values of multiple rows
	 * 
	 * @param startRow
	 *            the starting row
	 * @param endRow
	 *            the ending row which will be included in the return value
	 * @return values of multiple rows
	 */
	public double[][] getRows(int startRow, int endRow) {
		double[][] rows = new double[endRow - startRow][getData()[0].length];

		for (int row = startRow; row < endRow; row++) {
			int index = 0;
			for (int col = 0; col < getData()[startRow].length; col++) {
				rows[index][col] = getData()[row][col];
				index++;
			}
		}

		return rows;
	}

	/***
	 * Returns values of multiple rows
	 * 
	 * @param rowIndexes
	 *            the desired row indexes
	 * @return values of multiple rows
	 */
	public double[][] getRows(int[] rowIndexes) {
		return getRows(rowIndexes[0], rowIndexes[rowIndexes.length - 1]);
	}

	/***
	 * Returns values of multiple cols
	 * 
	 * @param startCol
	 *            the starting col
	 * @param endCol
	 *            the ending col which won't be included in the return value
	 * @return values of multiple cols
	 */
	public double[][] getCols(int startCol, int endCol) {
		double[][] cols = new double[getData().length][endCol - startCol];
		for (int row = 0; row < getData().length; row++) {
			int index = 0;
			for (int col = startCol; col < endCol; col++) {
				cols[row][index] = getData()[row][col];
				index++;
			}
		}
		return cols;
	}

	/***
	 * Returns values of multiple cols
	 * 
	 * @param colIndexes
	 *            the desired cols indexes
	 * @return values of multiple cols
	 */
	public double[][] getCols(int[] colIndexes) {
		double[][] cols = new double[getData().length][colIndexes.length];
		for (int row = 0; row < getData().length; row++) {
			int index = 0;
			for (int col = colIndexes[0]; col < colIndexes[colIndexes.length - 1]; col++) {
				cols[row][index] = col;
			}
		}

		return cols;
	}

	/***
	 * Gets the columns with an input of a string array of the column names
	 * 
	 * @param columnNames
	 * @return 2d array of the columns
	 */
	public double[][] getCols(String[] columnNames) {
		double[][] cols = new double[getData().length][columnNames.length];
		for (int row = 0; row < getData().length; row++) {
			for (int i = 0; i < columnNames.length; i++) {
				double[] vals = getColumn(columnNames[i]);
				cols[row][i] = vals[i];
			}
		}

		return cols;
	}

	/***
	 * Sets an entire column with a set of values
	 * 
	 * @param columnIndex
	 * @param vals
	 */
	public void setColumn(int columnIndex, double[] vals) {
		for (int row = 0; row < getData().length; row++) {
			getData()[row][columnIndex] = vals[row];
		}

	}

	/***
	 * Sets an entire row with a set of values
	 * 
	 * @param rowIndex
	 * @param vals
	 */
	public void setRow(int rowIndex, double[] vals) {
		for (int col = 0; col < getData().length; col++) {
			getData()[rowIndex][col] = vals[col];
		}
	}

	/***
	 * Sets a specific value to a specific index
	 * 
	 * @param col
	 * @param row
	 * @param newValue
	 */
	public void setValue(int col, int row, double newValue) {
		getData()[row][col] = newValue;
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
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

	public static void writeDataToFile(String filePath, String data) {
		File outFile = new File(filePath);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
			writer.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}