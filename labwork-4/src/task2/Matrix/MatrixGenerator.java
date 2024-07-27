package task2.Matrix;

public class MatrixGenerator {
    public Matrix generateRandomMatrix(int rowsCount, int columnsCount ) {
        var matrix = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                matrix.set(i, j, (int) (Math.random() * 10));
            }
        }

        return matrix;
    }
}
