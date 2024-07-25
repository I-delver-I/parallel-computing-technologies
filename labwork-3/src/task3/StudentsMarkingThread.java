package task3;

import java.util.concurrent.ThreadLocalRandom;

public class StudentsMarkingThread extends Thread {
    private static final int MINIMAL_GRADE = 60;
    private static final int MAXIMAL_GRADE = 100;

    private final Journal journal;
    private final int weekIndex;

    public StudentsMarkingThread(Journal journal, int weekIndex) {
        this.journal = journal;
        this.weekIndex = weekIndex;
    }

    @Override
    public void run() {
        var groups = journal.getGroups();

        for (var group : groups) {
            var students = group.getStudents();

            for (var student : students) {
                var randomMark = ThreadLocalRandom.current().nextInt(MINIMAL_GRADE, MAXIMAL_GRADE + 1);

                if (journal.tryAddMark(student, randomMark)) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " added mark " + randomMark +
                            " to student " + student.getName() + " of group " + group.getName() + " for week " + (weekIndex + 1));
                }
            }
        }
    }
}
