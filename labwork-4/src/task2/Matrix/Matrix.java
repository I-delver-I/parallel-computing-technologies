package task2.Matrix;

public class Matrix {
    private final int[][] matrix;

    public Matrix(int rowsSize, int columnsSize) {
        matrix = new int[rowsSize][columnsSize];
    }

    public int getRowsSize() {
        return matrix.length;
    }

    public int getColumnsSize() {
        return matrix[0].length;
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public void set(int i, int j, int value) {
        matrix[i][j] = value;
    }
}
