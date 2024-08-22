package task3;

public class Student {
    private final String name;
    private boolean isMarked = false;
    private static int nextStudentNumber = 1;

    public Student() {
        this.name = "Student " + nextStudentNumber++;
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
