package pl.edu.agh.iisg.to.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pl.edu.agh.iisg.to.executor.QueryExecutor;

public class Student {
    private final int id;

    private final String firstName;

    private final String lastName;

    private final int indexNumber;

    Student(final int id, final String firstName, final String lastName, final int indexNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.indexNumber = indexNumber;
    }

    public static Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        String sql = "INSERT INTO student (first_name, last_name, index_number) VALUES (?, ?, ?)";

        Object[] args = { firstName, lastName, indexNumber };

        try {
            int id = QueryExecutor.createAndObtainId(sql, args);
            return findById(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<Student> findByIndexNumber(final int indexNumber) {
        String sql = "SELECT * FROM student WHERE index_number = (?)";
        Object[] args = { indexNumber };

        try {
            ResultSet result = QueryExecutor.read(sql, args);

            if (result.next()) {
                return Optional.of(new Student(
                        result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("index_number")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<Student> findById(final int id) {
        String sql = "SELECT * FROM student WHERE id = (?)";
        return find(id, sql);
    }

    private static Optional<Student> find(int value, String sql) {
        Object[] args = {value};
        try (ResultSet rs = QueryExecutor.read(sql, args)) {
            if (rs.next()) {
                return Optional.of(new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("index_number")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Map<Course, Float> createReport() {
        String query =
                """
                SELECT course_id, name as course_name, AVG(grade) as avg_grade FROM grade
                JOIN main.course c on grade.course_id = c.id
                GROUP BY course_id
                """;
        Object[] args = { };

        Map<Course, Float> AvgGrades = new HashMap<>();

        try (ResultSet rs = QueryExecutor.read(query, args)) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name")
                );

                AvgGrades.put(course, rs.getFloat("avg_grade"));
            }

            return AvgGrades;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyMap();
    }

    public int id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public int indexNumber() {
        return indexNumber;
    }

    public static class Columns {

        public static final String ID = "id";

        public static final String FIRST_NAME = "first_name";

        public static final String LAST_NAME = "last_name";

        public static final String INDEX_NUMBER = "index_number";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Student student = (Student) o;

        if (id != student.id)
            return false;
        if (indexNumber != student.indexNumber)
            return false;
        if (!firstName.equals(student.firstName))
            return false;
        return lastName.equals(student.lastName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + indexNumber;
        return result;
    }
}
