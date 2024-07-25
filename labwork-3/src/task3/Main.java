package task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int WEEKS_COUNT = 5;
    private static final int THREADS_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
        var groups = new ArrayList<>(Arrays.asList(
                new Group("ІP-11", 10),
                new Group("ІP-15", 15),
                new Group("ІP-12", 5)
        ));

        var journal = new Journal(groups, WEEKS_COUNT);
        var threads = new ArrayList<Thread>();

        for (var week = 0; week < WEEKS_COUNT; week++) {
            for (var t = 0; t < THREADS_COUNT; t++) {
                var thread = new StudentsMarkingThread(journal, week);
                threads.add(thread);
                thread.start();
            }

            for (var thread : threads) {
                thread.join();
            }

            uncheckStudentsMarking(groups);
            System.out.println();
        }

        journal.printMarks();
    }

    private static void uncheckStudentsMarking(List<Group> groups) {
        for (var group : groups) {
            for (var student : group.getStudents()) {
                student.setMarked(false);
            }
        }
    }
}
