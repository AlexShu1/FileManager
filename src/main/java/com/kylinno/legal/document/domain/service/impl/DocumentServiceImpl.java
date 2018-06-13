package com.kylinno.legal.document.domain.service.impl;

import com.kylinno.legal.document.domain.entity.DocumentEntity;
import com.kylinno.legal.document.domain.exception.UploadDocumentException;
import com.kylinno.legal.document.domain.model.FastDFSFile;
import com.kylinno.legal.document.domain.model.UploadDocumentResponseModel;
import com.kylinno.legal.document.domain.repository.DocumentRepository;
import com.kylinno.legal.document.domain.service.FastDFSClient;
import com.kylinno.legal.document.domain.service.DocumentService;
import com.kylinno.legal.document.domain.untils.DocumentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private DocumentRepository documentRepository;


    /**
     * Save file information.
     *
     * @param savePath
     * @param chinesePath
     * @param userId
     * @param category
     * @param fileSize
     * @param unit
     * @return
     */
    private DocumentEntity saveFileInfo(String savePath, String chinesePath, String userId,
                                        String category, float fileSize, String unit) {
        DocumentEntity file = new DocumentEntity();
        file.setUserId(userId);
        file.setCategory(category);
        file.setSavePath(savePath);
        file.setChinesePath(chinesePath);
        file.setDelete(false);
        file.setCreatedDate(new Date());
        file.setModifiedDate(new Date());
        file.setSize(fileSize);
        file.setUnit(unit);

        return documentRepository.insert(file);
    }

    @Override
    public UploadDocumentResponseModel saveFile(MultipartFile multipartFile, String userId,
                                                String category) throws IOException, UploadDocumentException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        UploadDocumentResponseModel model = new UploadDocumentResponseModel();

        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }

        inputStream.close();

        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        String chinesePath = fileName;

        fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs

        if (fileName.contains("&#")) {
            chinesePath = DocumentUtil.fetchShowFilePath(fileName);
        }

        if (fileAbsolutePath == null) {
            logger.error("upload file failed, Server don't response group id and save path.");
            model.setStatus(400);
            throw new UploadDocumentException("upload file failed!");
        }

        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        String savePath = path;
        BigDecimal fileLength = new BigDecimal(multipartFile.getSize());
        String fileSizeUnit = DocumentUtil.changedFileSize(fileLength);
        String[] fileSizeString = fileSizeUnit.split("\\|");
        float fileSize = Float.parseFloat(fileSizeString[0]);


        DocumentEntity  documentEntity = saveFileInfo(savePath, chinesePath, userId,
                category, fileSize, fileSizeString[1]);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String modifyDate = dateFormat.format(documentEntity.getModifiedDate());

        model.setFileName(documentEntity.getChinesePath());
        model.setCategory(documentEntity.getCategory());
        model.setModifiedDate(modifyDate);
        model.setFileId(documentEntity.getId());
        model.setFileSize(documentEntity.getSize());
        model.setFileSizeUnit(documentEntity.getUnit());

        return model;
    }

    @Override
    public void fetchAllFilesByUserId(String userId) {
        List<DocumentEntity> documentEntities = documentRepository.findByUserId(userId);
        System.out.println("Size: " + documentEntities.size());
    }
}
