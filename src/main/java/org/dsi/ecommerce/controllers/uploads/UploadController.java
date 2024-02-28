package org.dsi.ecommerce.controllers.uploads;

import org.dsi.ecommerce.helper.ImageUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin")
public class UploadController {


    @GetMapping("/uploadImage")
    public String displayUploadForm() {
        return "imageupload/index";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model,
                              @RequestParam("image") MultipartFile file) throws IOException {
        ImageUpload.uploadImage(file);
        return "imageupload/index";
    }
}
