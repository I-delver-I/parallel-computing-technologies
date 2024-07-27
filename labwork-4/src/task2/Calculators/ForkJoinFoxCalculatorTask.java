package task2.Calculators;

import java.util.concurrent.RecursiveAction;
import task2.Matrix.Matrix;

public class ForkJoinFoxCalculatorTask extends RecursiveAction {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int currentRowShift;
    private final int currentColumnShift;
    private final int blockSize;
    private final Matrix resultMatrix;

    public ForkJoinFoxCalculatorTask(Matrix Matrix1, Matrix Matrix2, int currentRowShift,
                                     int currentColumnShift, int blockSize, Matrix resultMatrix) {
        this.resultMatrix = resultMatrix;
        this.matrix1 = Matrix1;
        this.matrix2 = Matrix2;
        this.currentRowShift = currentRowShift;
        this.currentColumnShift = currentColumnShift;
        this.blockSize = blockSize;
    }

    @Override
    protected void compute() {
        var m1RowSize = Math.min(blockSize, matrix1.getRowsSize() - currentRowShift);
        var m2ColSize = Math.min(blockSize, matrix2.getColumnsSize() - currentColumnShift);

        for (int k = 0; k < matrix1.getColumnsSize(); k += blockSize) {
            int m1ColSize = Math.min(blockSize, matrix1.getRowsSize() - currentRowShift);
            int m2RowSize = Math.min(blockSize, matrix2.getRowsSize() - currentColumnShift);

            Matrix blockFirst = copyBlock(matrix1, currentRowShift,
                    currentRowShift + m1RowSize, k, k + m1ColSize);
            Matrix blockSecond = copyBlock(matrix2, k, k + m2RowSize,
                    currentColumnShift, currentColumnShift + m2ColSize);
            Matrix resBlock = new SequentialCalculator().multiplyMatrix(blockFirst, blockSecond);

            for (int i = 0; i < resBlock.getRowsSize(); i++) {
                for (int j = 0; j < resBlock.getColumnsSize(); j++) {
                    resultMatrix.set(i + currentRowShift, j + currentColumnShift,
                            resBlock.get(i, j) + resultMatrix.get(i + currentRowShift,
                                    j + currentColumnShift));
                }
            }
        }
    }

    private Matrix copyBlock(Matrix src, int rowStart, int rowFinish, int colStart, int colFinish) {
        var copyMatrix = new Matrix(rowFinish - rowStart, colFinish - colStart);

        for (int i = 0; i < rowFinish - rowStart; i++) {
            for (int j = 0; j < colFinish - colStart; j++) {
                copyMatrix.set(i, j, src.get(i + rowStart, j + colStart));
            }
        }

        return copyMatrix;
    }
}
