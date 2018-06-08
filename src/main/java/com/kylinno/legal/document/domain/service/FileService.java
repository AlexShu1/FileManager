package com.kylinno.legal.document.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * Save file to server.
     *
     * @param multipartFile
     * @return Files name and status.
     * @throws IOException
     */
    String saveFile(MultipartFile multipartFile) throws IOException;
}
