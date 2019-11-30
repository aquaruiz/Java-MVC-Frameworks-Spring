package io.bar.beerhub.services.services;

import com.cloudinary.Cloudinary;
import io.bar.beerhub.services.factories.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class ClaudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    public ClaudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        // Koceto kaza che e maj tui, ma ne e noo sigurnooooouuuuyyyyyy======
        return this.cloudinary
                .uploader()
                .upload(file, new HashMap())
                .get("url")
                .toString();
    }
}
