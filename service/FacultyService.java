package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(lastId, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }

    public List<Faculty> getColorFaculty(String color) {
        List<Faculty> fac = new ArrayList<>();
        for (Faculty value : faculties.values()) {
            if (value.getColor().equals(color)) {
                fac.add(value);
            }
        }
        return fac;
    }
}
