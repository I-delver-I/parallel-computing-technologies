import mpi.MPI;
import mpi.MPIException;

public class Main {
    static final int MATRIX_SIZE = 1500;
    static final int MAIN = 0;
    static final int FROM_MAIN = 1;
    static final int FROM_WORKER = 5;

    public static void main(String[] args) {
        try {
            MPI.Init(args);
            BlockingMatrixMultiplication.run(MATRIX_SIZE, MAIN, FROM_MAIN, FROM_WORKER);
            NonBlockingMatrixMultiplication.run(MATRIX_SIZE, MAIN, FROM_MAIN, FROM_WORKER);
            MPI.Finalize();
        } catch (MPIException e) {
            e.printStackTrace();
        }
    }
}