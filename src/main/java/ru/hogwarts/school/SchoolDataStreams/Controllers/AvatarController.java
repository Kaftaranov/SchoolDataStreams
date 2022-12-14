package ru.hogwarts.school.SchoolDataStreams.Controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.SchoolDataStreams.Models.Avatar;
import ru.hogwarts.school.SchoolDataStreams.Services.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/avatar")

public class AvatarController {
    private final AvatarService avatarService;
    public AvatarController(AvatarService avatarService){
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long studentId,@RequestParam MultipartFile avatar)
            throws IOException {
        if (avatar.getSize() > 1024*300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.uploadAvatar(studentId,avatar);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long id){
        Avatar avatar = avatarService.findAvatar(id);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
    headers.setContentLength(avatar.getAvatar().length);
    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getAvatar());
    }

}

