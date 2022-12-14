package ru.hogwarts.school.SchoolDataStreams.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.SchoolDataStreams.Models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findFacultiesByNameIgnoreCase(String name);
    Faculty findFacultiesByColorIgnoreCase(String color);
    Faculty findById(long id);
}
