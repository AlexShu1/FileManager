package com.kylinno.legal.document.resource;

import com.kylinno.legal.document.domain.exception.UploadDocumentException;
import com.kylinno.legal.document.domain.model.UploadDocumentResponseModel;
import com.kylinno.legal.document.domain.service.DocumentService;
import com.kylinno.legal.document.domain.untils.DocumentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/document")
public class UploadController {

    @Autowired
    private DocumentService documentService;
    private List<UploadDocumentResponseModel> responseModels;

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * Upload files: single file or more files.
     *
     * @param files
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public List<UploadDocumentResponseModel> fileUpload(@RequestParam("file") MultipartFile[] files,
                                                        @RequestParam("userId") String userId,
                                                        @RequestParam("category") String category) {

        responseModels = new ArrayList<>();
        int fileLength = files.length;
        final CountDownLatch countDownLatch = new CountDownLatch(fileLength);

        for (int i = 0; i < fileLength; i++) {
            MultipartFile file = files[i];
            UploadDocumentResponseModel model = new UploadDocumentResponseModel();

            Thread thread = new Thread(() -> {
                try {
                    UploadDocumentResponseModel resultModel = documentService.saveFile(file, userId, category);
                    model.setFileName(resultModel.getFileName());
                    model.setFileId(resultModel.getFileId());
                    model.setModifiedDate(resultModel.getModifiedDate());
                    model.setCategory(resultModel.getCategory());
                    model.setFileSize(resultModel.getFileSize());
                    model.setFileSizeUnit(resultModel.getFileSizeUnit());
                    model.setStatus(200);
                } catch (IOException | UploadDocumentException e) {
                    logger.error("upload file failed", e);
                    model.setStatus(400);
                } finally {
                    countDownLatch.countDown();
                    responseModels.add(model);
                }
            });

            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("upload file failed", e);
        }

        return DocumentUtil.fetchSortByModifiedDateDocument(responseModels);
    }


    @GetMapping("/fetch/{userId}")
    public List<UploadDocumentResponseModel> fetchAllFiles(@PathVariable("userId") String userId) {
        documentService.fetchAllFilesByUserId(userId);
        return null;
    }


}