package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<Student>{
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public StudentRepository(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @Override
    public Optional<Student> add(Student student) {
        return studentDao.create(student.firstName(), student.lastName(), student.indexNumber());
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
        student.courseSet().forEach(course -> {
            course.studentSet().remove(student); //
            student.courseSet().remove(course);
        });

        studentDao.remove(student);
    }

    public List<Student> findAllByCourseName(String courseName) {
        Optional<Course> course = courseDao.findByName(courseName);
        if(course.isPresent()) {
            return course.get().studentSet().stream().toList();
        }




        return Collections.emptyList();
    }
}
