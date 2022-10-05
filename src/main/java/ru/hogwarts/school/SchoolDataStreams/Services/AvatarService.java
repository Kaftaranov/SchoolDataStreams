package ru.hogwarts.school.SchoolDataStreams.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.SchoolDataStreams.Models.Avatar;
import ru.hogwarts.school.SchoolDataStreams.Models.Student;
import ru.hogwarts.school.SchoolDataStreams.Repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${students.avatars.dir.path}")
    private String avatarDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }
    public void uploadAvatar(Long studentId, MultipartFile file)throws IOException{
        Student student = studentService.findById(studentId);
        Path filePath = Path.of(Path.of(avatarDir, String.valueOf(student)) + "."
                + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath,CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is,1024);
             BufferedOutputStream bos = new BufferedOutputStream(os,1024);
             ){
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatarRepository.save(avatar);
    }
    public Avatar findAvatar(Long student_id){
        if (avatarRepository.findByStudentId(student_id) != null){
        return avatarRepository.findByStudentId(student_id);}
        return new Avatar();
    }

    private String getExtension(String filename) {

        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
