package pl.edu.agh.iisg.to.service;

import jakarta.transaction.Transaction;
import org.hibernate.Session;
import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;
import java.util.stream.Collectors;

public class SchoolService {

    private final TransactionService transactionService;

    private final StudentDao studentDao;

    private final CourseDao courseDao;

    private final GradeDao gradeDao;

    public SchoolService(TransactionService transactionService, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.transactionService = transactionService;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    public boolean enrollStudent(final Course course, final Student student) {
        return transactionService.doAsTransaction(() -> {
            Set<Student> courseStudentSet = course.studentSet();
            if (courseStudentSet.contains(student)) {
                return false;
            }

            courseStudentSet.add(student);
            student.courseSet().add(course);
            return true;
        }).orElseThrow();
    }

    public boolean removeStudent(int indexNumber) {
        return transactionService.doAsTransaction(() -> {
            Optional<Student> studentOpt = studentDao.findByIndexNumber(indexNumber);

            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();

                student.courseSet().forEach(course -> {
                    course.studentSet().remove(student); //
                    student.courseSet().remove(course);
                });

                studentDao.remove(student);
                return true;
            }

            return false;
        }).orElseThrow();
    }

    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        return transactionService.doAsTransaction(() -> {
            Grade grade = new Grade(student, course, gradeValue);
            gradeDao.save(grade);
            student.gradeSet().add(grade);
            course.gradeSet().add(grade);
            return true;
        }).orElseThrow();
    }

    public Map<String, List<Float>> getStudentGrades(String courseName) {
        Course course = courseDao.findByName(courseName).orElseThrow();

        Map<String, List<Float>> result = new LinkedHashMap<>();

        for (Student student : course.studentSet()) {
            String studentName = student.fullName();
            List<Float> grades = course.gradeSet().stream()
                    .filter(grade -> grade.student().equals(student))
                    .map(Grade::grade)
                    .sorted()
                    .collect(Collectors.toList());
            result.put(studentName, grades);
        }

        return result;
    }
}
