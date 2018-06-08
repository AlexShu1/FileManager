package com.kylinno.legal.document.resource;

import com.kylinno.legal.document.domain.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private FileService fileService;

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    private long startTime;

    /**
     * Upload files: single file or more files.
     *
     * @param files
     * @return
     * @throws IOException
     */
    @PostMapping("/upload") //new annotation since 4.3
    public String fileUpload(@RequestParam("file") MultipartFile[] files) throws IOException {

        int fileLength = files.length;
        startTime = System.currentTimeMillis();

        for(int i = 0; i < fileLength; i++) {
            MultipartFile file = files[i];

            try {
                Thread thread = new Thread(() -> {
                    try {
                        fileService.saveFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                thread.start();

                String path=saveFile(file);
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
                redirectAttributes.addFlashAttribute("path",
                        "file path url '" + path + "'");
            } catch (Exception e) {
                logger.error("upload file failed",e);
            }
        }

        return "redirect:/uploadStatus";
    }



}