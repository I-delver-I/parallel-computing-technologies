package task3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Journal {
    private final List<Group> groups;
    private final ConcurrentMap<Student, List<Integer>> studentsMarks = new ConcurrentHashMap<>();

    public Journal(List<Group> groups, int weeksCount) {
        if (weeksCount <= 0) {
            throw new IllegalArgumentException("Weeks count must be positive.");
        }

        this.groups = new ArrayList<>(groups);

        for (var group : this.groups) {
            for (var student : group.getStudents()) {
                this.studentsMarks.put(student, Collections.synchronizedList(new ArrayList<>()));
            }
        }
    }

    public boolean tryAddMark(Student student, int mark) {
        var studentMarks = studentsMarks.get(student);

        if (studentMarks == null) {
            return false;
        }

        synchronized (studentMarks) {
            if (student.isMarked()) {
                return false;
            }

            studentMarks.add(mark);
            student.setMarked(true);
        }

        return true;
    }

    public List<Group> getGroups() {
        return new ArrayList<>(groups);
    }

    public void printMarks() {
        var sortedGroups = groups.stream().sorted(Comparator.comparing(Group::getName)).toList();

        for (var group : sortedGroups) {
            System.out.println("Group " + group.getName());
            var sortedStudents = group.getStudents().stream()
                    .sorted(Comparator.comparing(s -> Integer.parseInt(s.getName()
                            .replaceAll("\\D+", "")))).toList();

            for (var student : sortedStudents) {
                System.out.println(student.getName() + " marks: ");
                var marks = studentsMarks.get(student);

                if (marks != null) {
                    marks.forEach(mark -> System.out.print(mark + " "));
                }

                System.out.println();
            }

            System.out.println();
        }
    }
}

