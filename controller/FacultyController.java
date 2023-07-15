package ru.hogwarts.school.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.entity.Faculty;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public FacultyDtoOut getFaculty (@PathVariable("id") Long id) {
        return facultyService.findFaculty(id);
    }

    @GetMapping()
    public List<FacultyDtoOut> getFacultyByColor(@RequestParam(required = false) String color) {
        return facultyService.getColorFaculty(color);
    }

    @GetMapping("/filter")
    public List<FacultyDtoOut> getFacultyByColorOrName(@RequestParam String colorOrName) {
        return facultyService.getFacultyByColorOrName(colorOrName);
    }

    @GetMapping("/{id}/students")
    public List<StudentDtoOut> getStudents(@PathVariable long id) {
        return facultyService.getStudents(id);
    }
    @PostMapping()
    public FacultyDtoOut postFaculty (@RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.createFaculty(facultyDtoIn);
    }

    @PutMapping("/{id}")
    public FacultyDtoOut putFaculty(@PathVariable("id") long id,
                                    @RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.editFaculty(id, facultyDtoIn);
    }

    @DeleteMapping("/{id}")
    public FacultyDtoOut deleteFaculty(@PathVariable("id") Long id) {
        return facultyService.deleteFaculty(id);
    }
}
