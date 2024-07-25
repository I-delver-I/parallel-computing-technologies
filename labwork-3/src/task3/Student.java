package task3;

import java.util.concurrent.atomic.AtomicInteger;

public class Student {
    private final String name;
    private boolean isMarked = false;
    private static final AtomicInteger nextStudentNumber = new AtomicInteger(1);

    public Student() {
        this.name = "Student " + nextStudentNumber.getAndIncrement();
    }

    public String getName() {
        return name;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
