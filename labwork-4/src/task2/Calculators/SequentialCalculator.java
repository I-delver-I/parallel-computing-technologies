package task2.Calculators;

import task2.Matrix.Matrix;

public class SequentialCalculator {
    public Matrix multiplyMatrix(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsSize() != matrix2.getRowsSize()) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: " +
                    "the number of columns of matrix A is not equal to the number of rows of matrix B.");
        }

        var rowsSize = matrix1.getRowsSize();
        var columnsSize = matrix2.getColumnsSize();
        var commonSize = matrix1.getColumnsSize();

        var result = new Matrix(rowsSize, columnsSize);

        for (var i = 0; i < rowsSize; i++) {
            for (var j = 0; j < columnsSize; j++) {
                var sum = 0;

                for (var k = 0; k < commonSize; k++) {
                    sum += matrix1.get(i, k) * matrix2.get(k, j);
                }

                result.set(i, j, sum);
            }
        }

        return result;
    }
}
