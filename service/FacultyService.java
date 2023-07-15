package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final FacultyMapper facultyMapper;
    private final StudentMapper studentMapper;
    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository, FacultyMapper facultyMapper, StudentMapper studentMapper) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.facultyMapper = facultyMapper;
        this.studentMapper = studentMapper;
    }

    public FacultyDtoOut createFaculty(FacultyDtoIn facultyDtoIn) {
        return facultyMapper.toDto(facultyRepository.save(facultyMapper.toEntity(facultyDtoIn)));
    }

    public FacultyDtoOut editFaculty(long id, FacultyDtoIn facultyDtoIn) {
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setColor(facultyDtoIn.getColor());
                    oldFaculty.setName(facultyDtoIn.getName());
                    return facultyMapper.toDto(facultyRepository.save(oldFaculty));
                })
                .orElseThrow(() -> new RuntimeException("Нет факультета с идентификатором  " + id));
    }

    public FacultyDtoOut deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нет факультета с идентификатором  " + id));
        facultyRepository.delete(faculty);
        return facultyMapper.toDto(faculty);
    }

    public FacultyDtoOut findFaculty(long id) {
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Нет факультета с идентификатором  " + id));
    }

    public List<FacultyDtoOut> getColorFaculty(String color) {
        return Optional.ofNullable(color)
                .map(facultyRepository::findAllByColor)
                .orElseGet(facultyRepository::findAll).stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<FacultyDtoOut> getFacultyByColorOrName(String colorOrName) {
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(
                        colorOrName,
                        colorOrName
                ).stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StudentDtoOut> getStudents(long id) {
        return studentRepository.findAllByFaculty_Id(id)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}
