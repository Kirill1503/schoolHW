package ru.hogwarts.school.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentMapper studentMapper;
    private final FacultyMapper facultyMapper;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository, StudentMapper studentMapper, FacultyMapper facultyMapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.studentMapper = studentMapper;
        this.facultyMapper = facultyMapper;
    }

    public StudentDtoOut createStudent(StudentDtoIn studentDtoIn) {
        return studentMapper.toDto(studentRepository.save(studentMapper.toEntity(studentDtoIn)));
    }

    public StudentDtoOut editStudent(long id, StudentDtoIn studentDtoIn) {
        return studentRepository.findById(id)
                .map(oldStudent -> {
                    oldStudent.setAge(studentDtoIn.getAge());
                    oldStudent.setName(studentDtoIn.getName());
                    Optional.ofNullable(studentDtoIn.getFacultyId())
                            .ifPresent(facultyId -> oldStudent.setFaculty(facultyRepository
                                    .findById(facultyId).orElseThrow(RuntimeException::new)));
                    return studentMapper.toDto(studentRepository.save(oldStudent));
                })
                .orElseThrow(() -> new RuntimeException("Нет студента с идентификатором  " + id));
    }

    public StudentDtoOut deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нет студента с идентификатором  " + id));
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

    public StudentDtoOut findStudent(long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Нет студента с идентификатором  " + id));
    }

    public List<StudentDtoOut> findAgeStudents(@Nullable int age) {
        return Optional.of(age)
                .map(studentRepository::findAllByAge)
                .orElseGet(studentRepository::findAll).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StudentDtoOut> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacultyDtoOut getFaculty(long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .map(facultyMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }
}
