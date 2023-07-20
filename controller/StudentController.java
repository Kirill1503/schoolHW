package ru.hogwarts.school.controller;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public StudentDtoOut getStudent(@PathVariable("id") Long id) {
        return studentService.findStudent(id);
    }

    @GetMapping("/age/{age}")
    public List<StudentDtoOut> getStudentsByAge(@PathVariable int age) {
        return studentService.findAgeStudents(age);
    }

    @GetMapping("/filter")
    public List<StudentDtoOut> getStudentsByAgeBetween(@RequestParam(required = false) int min, int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/faculty")
    public FacultyDtoOut getFaculty(@PathVariable long id) {
        return studentService.getFaculty(id);
    }

    @PostMapping()
    public StudentDtoOut postStudent(@RequestBody StudentDtoIn studentDtoIn) {
        return studentService.createStudent(studentDtoIn);
    }

    @PutMapping("/{id}")
    public StudentDtoOut putStudent(@PathVariable("id") long id,
                                    @RequestBody StudentDtoIn studentDtoIn) {
        return studentService.editStudent(id, studentDtoIn);
    }

    @DeleteMapping("/{id}")
    public StudentDtoOut deleteStudent(@PathVariable("id") Long id) {
        return studentService.deleteStudent(id);
    }

    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentDtoOut uploadAvatar(@PathVariable long id,
                                      @RequestPart("avatar") MultipartFile multipartFile) {
        return studentService.uploadAvatar(id, multipartFile);
    }
}

