package task3;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final String name;
    private final List<Student> students;

    public Group(String name, int groupSize) {
        if (groupSize <= 0) {
            throw new IllegalArgumentException("Group size and weeks count must be positive.");
        }

        this.name = name;
        this.students = new ArrayList<>(groupSize);
        generateStudents(groupSize);
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    private void generateStudents(int groupSize) {
        for (var i = 0; i < groupSize; i++) {
            students.add(new Student());
        }
    }

    public String getName() {
        return name;
    }
}