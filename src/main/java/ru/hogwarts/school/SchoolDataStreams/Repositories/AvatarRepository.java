package ru.hogwarts.school.SchoolDataStreams.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.SchoolDataStreams.Models.Avatar;

public interface AvatarRepository extends JpaRepository <Avatar, Long> {
    Avatar findByStudentId(Long student_id);
}
