package com.kylinno.legal.document.domain.service;

import com.kylinno.legal.document.domain.exception.UploadDocumentException;
import com.kylinno.legal.document.domain.model.UploadDocumentResponseModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {

    /**
     * Save files to server.
     *
     * @param multipartFile
     * @param userId
     * @param category
     * @return
     * @throws IOException
     * @throws UploadDocumentException
     */
    UploadDocumentResponseModel saveFile(MultipartFile multipartFile, String userId,
                                         String category) throws IOException, UploadDocumentException;

    void fetchAllFilesByUserId(String userId);
}
