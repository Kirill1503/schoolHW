package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final Path pathToAvatarFolder;

    public AvatarService(AvatarRepository avatarRepository,
                         @Value("%path.to.avatar.folder") String pathToAvatarFolder) {
        this.avatarRepository = avatarRepository;
        this.pathToAvatarFolder = Path.of(pathToAvatarFolder);
    }

    public Avatar create(Student student, MultipartFile multipartFile) {
        try {
            String contentType = multipartFile.getContentType();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            byte[] data = multipartFile.getBytes();
            String fileName = UUID.randomUUID() + " . " + extension;
            Path pathToAvatar = pathToAvatarFolder.resolve(fileName);
            Files.write(pathToAvatar, data);


            Avatar avatar = avatarRepository.findByStudent_Id(student.getId())
                    .orElse(new Avatar());
            avatar.setMediaType(contentType);
            avatar.setFileSize(data.length);
            avatar.setData(data);
            avatar.setStudent(student);
            avatar.setFilePath(pathToAvatar.toString());
            return avatarRepository.save(avatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pair<byte[], String> getFromDb(long id) {
        Avatar avatar = avatarRepository.findByStudent_Id(id)
                .orElseThrow(() -> new RuntimeException("Аватар с таким id - " + id + " не найдет"));
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }

    public Pair<byte[], String> getFromFs(long id) {
        try {
            Avatar avatar = avatarRepository.findByStudent_Id(id)
                    .orElseThrow(() -> new RuntimeException("Аватар с таким id - " + id + " не найдет"));
            return Pair.of(Files.readAllBytes(Path.of(avatar.getFilePath())), avatar.getMediaType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
