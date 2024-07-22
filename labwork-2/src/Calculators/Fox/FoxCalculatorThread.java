package Calculators.Fox;

import Calculators.SequentialCalculator;
import Matrix.Matrix;

public class FoxCalculatorThread extends Thread {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int curRowShift;
    private final int curColShift;
    private final int blockSize;
    private final Matrix resultMatrix;

    public FoxCalculatorThread(Matrix matrix1, Matrix matrix2, int curRowShift,
                               int curColShift, int blockSize, Matrix resultMatrix) {
        this.resultMatrix = resultMatrix;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.curRowShift = curRowShift;
        this.curColShift = curColShift;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        var m1RowSize = calculateEffectiveBlockSize(curRowShift, blockSize, matrix1.getRowsSize());
        var m2ColSize = calculateEffectiveBlockSize(curColShift, blockSize, matrix2.getColumnsSize());

        for (var i = 0; i < matrix1.getRowsSize(); i += blockSize) {
            var m1ColSize = calculateEffectiveBlockSize(i, blockSize, matrix1.getColumnsSize());
            var m2RowSize = calculateEffectiveBlockSize(i, blockSize, matrix2.getRowsSize());

            var blockFirst = copyBlock(matrix1, curRowShift, curRowShift + m1RowSize, i, i + m1ColSize);
            var blockSecond = copyBlock(matrix2, i, i + m2RowSize, curColShift, curColShift + m2ColSize);

            var resBlock = new SequentialCalculator().multiplyMatrix(blockFirst, blockSecond);

            mergeResultBlock(resBlock);
        }
    }

    private int calculateEffectiveBlockSize(int start, int blockSize, int matrixSize) {
        return Math.min(blockSize, matrixSize - start);
    }

    private Matrix copyBlock(Matrix src, int rowStart, int rowFinish, int colStart, int colFinish) {
        var rowCount = rowFinish - rowStart;
        var colCount = colFinish - colStart;
        var copyMatrix = new Matrix(rowCount, colCount);

        for (var i = 0; i < rowCount; i++) {
            for (var j = 0; j < colCount; j++) {
                copyMatrix.set(i, j, src.get(i + rowStart, j + colStart));
            }
        }

        return copyMatrix;
    }

    private void mergeResultBlock(Matrix resBlock) {
        for (var i = 0; i < resBlock.getRowsSize(); i++) {
            for (var j = 0; j < resBlock.getColumnsSize(); j++) {
                var currentVal = resultMatrix.get(i + curRowShift, j + curColShift);
                var newVal = resBlock.get(i, j) + currentVal;
                resultMatrix.set(i + curRowShift, j + curColShift, newVal);
            }
        }
    }
}
