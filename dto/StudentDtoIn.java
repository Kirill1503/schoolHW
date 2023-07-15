package ru.hogwarts.school.dto;

public class StudentDtoIn {

    private String name;
    private int age;
    private Long facultyId;

    public StudentDtoIn(String name, int age, Long facultyId) {
        this.name = name;
        this.facultyId = facultyId;
        this.age = age;
    }

    public StudentDtoIn() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
}