import java.util.Arrays;

public class MatrixUtilities {
    public static void printMatrix(double[][] matrix) {
        for (var row : matrix) {
            for (var value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void fillMatrix(double[][] arr, double value) {
        for (var row : arr) {
            Arrays.fill(row, value);
        }
    }

    public static double[] convertMatrixToArray(double[][] matrix) {
        var rows = matrix.length;
        var cols = matrix[0].length;
        var arr1D = new double[rows * cols];
        var index = 0;

        for (var row : matrix) {
            for (var value : row) {
                arr1D[index++] = value;
            }
        }

        return arr1D;
    }

    public static double[][] convertArrayToMatrix(double[] array, int rows, int cols) {
        var arr2D = new double[rows][cols];
        var index = 0;

        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < cols; j++) {
                arr2D[i][j] = array[index++];
            }
        }

        return arr2D;
    }

    public static void fillMatrixWithOffset(double[][] matrix, double[] array, int offsetByRows) {
        var index = 0;

        for (var i = offsetByRows; i < matrix.length && index < array.length; i++) {
            for (var j = 0; j < matrix[i].length && index < array.length; j++) {
                matrix[i][j] = array[index++];
            }
        }
    }
}