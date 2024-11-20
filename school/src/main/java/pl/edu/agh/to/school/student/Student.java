package pl.edu.agh.to.school.student;

import java.time.LocalDate;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String indexNumber;

    public Student(String firstName, String lastName, LocalDate birthDate, String indexNumber) {
        this.indexNumber = indexNumber;
        this.birthDate = birthDate;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }
}
