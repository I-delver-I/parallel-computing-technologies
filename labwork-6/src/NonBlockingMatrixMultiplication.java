import mpi.MPI;

import java.util.Arrays;

public class NonBlockingMatrixMultiplication {
    public static void run(int matrixSize, int mainRank, int fromMainTag, int fromWorkerTag) {
        var currentRank = MPI.COMM_WORLD.Rank();
        var totalProcesses = MPI.COMM_WORLD.Size();
        var workersCount = totalProcesses - 1;

        if (matrixSize % workersCount != 0) {
            if (currentRank == mainRank) {
                System.out.println("Cannot evenly distribute rows to " + totalProcesses + " processes!");
            }
            return;
        }

        var rowsPerWorker = matrixSize / workersCount;
        var matrixB = new double[matrixSize][matrixSize];

        if (currentRank == mainRank) {
            var matrixA = new double[matrixSize][matrixSize];
            var resultMatrix = new double[matrixSize][matrixSize];
            var startTime = System.currentTimeMillis();

            MatrixUtilities.fillMatrix(matrixA, 8);
            MatrixUtilities.fillMatrix(matrixB, 8);
            var matrixB1D = MatrixUtilities.convertMatrixToArray(matrixB);

            for (var destRank = 1; destRank <= workersCount; destRank++) {
                var offset = (destRank - 1);
                var startRow = offset * rowsPerWorker;
                var endRow = startRow + rowsPerWorker;
                var subMatrixA1D = MatrixUtilities.convertMatrixToArray(Arrays.copyOfRange(matrixA, startRow, endRow));

                MPI.COMM_WORLD.Isend(new int[]{offset}, 0, 1, MPI.INT, destRank, fromMainTag);
                MPI.COMM_WORLD.Isend(subMatrixA1D, 0, rowsPerWorker * matrixSize,
                        MPI.DOUBLE, destRank, fromMainTag + 1);
                MPI.COMM_WORLD.Isend(matrixB1D, 0, matrixSize * matrixSize,
                        MPI.DOUBLE, destRank, fromMainTag + 2);
            }

            for (var sourceRank = 1; sourceRank <= workersCount; sourceRank++) {
                var offset = new int[1];
                var resultSubMatrix1D = new double[rowsPerWorker * matrixSize];

                var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, sourceRank,
                        fromWorkerTag);
                MPI.COMM_WORLD.Recv(resultSubMatrix1D, 0, rowsPerWorker * matrixSize,
                        MPI.DOUBLE, sourceRank, fromWorkerTag + 1);
                offsetRequest.Wait();

                MatrixUtilities.fillMatrixWithOffset(resultMatrix, resultSubMatrix1D,
                        offset[0] * rowsPerWorker);
            }

            System.out.println("Execution time of non-blocking: " + (System.currentTimeMillis() - startTime)
                    + " ms for " + workersCount + " workers");
        } else {
            var offset = new int[1];
            var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, mainRank,
                    fromMainTag);
            var subMatrixA1D = new double[rowsPerWorker * matrixSize];
            var subMatrixRequest = MPI.COMM_WORLD.Irecv(subMatrixA1D, 0,
                    rowsPerWorker * matrixSize, MPI.DOUBLE, mainRank, fromMainTag + 1);
            var matrixB1D = new double[matrixSize * matrixSize];

            MPI.COMM_WORLD.Recv(matrixB1D, 0, matrixSize * matrixSize,
                    MPI.DOUBLE, mainRank, fromMainTag + 2);
            offsetRequest.Wait();
            subMatrixRequest.Wait();

            var subMatrixA = MatrixUtilities.convertArrayToMatrix(subMatrixA1D, rowsPerWorker, matrixSize);
            matrixB = MatrixUtilities.convertArrayToMatrix(matrixB1D, matrixSize, matrixSize);
            var resultSubMatrix = new double[rowsPerWorker][matrixSize];

            for (var i = 0; i < rowsPerWorker; i++) {
                for (var j = 0; j < matrixSize; j++) {
                    for (var k = 0; k < matrixSize; k++) {
                        resultSubMatrix[i][j] += subMatrixA[i][k] * matrixB[k][j];
                    }
                }
            }

            MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, mainRank, fromWorkerTag);
            MPI.COMM_WORLD.Isend(MatrixUtilities.convertMatrixToArray(resultSubMatrix), 0,
                    rowsPerWorker * matrixSize, MPI.DOUBLE, mainRank, fromWorkerTag + 1);
        }
    }
}