package com.kylinno.legal.document.domain.service.impl;

import com.kylinno.legal.document.domain.entity.FileEntity;
import com.kylinno.legal.document.domain.model.FastDFSFile;
import com.kylinno.legal.document.domain.repository.FileRepository;
import com.kylinno.legal.document.domain.service.FastDFSClient;
import com.kylinno.legal.document.domain.service.FileService;
import com.kylinno.legal.document.domain.untils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileRepository fileRepository;

    /**
     * Save upload file information to DB.
     *
     * @param ipAddress
     * @param groupId
     * @param savePath
     * @param chinesePath
     */
    private void saveFileInfo(String ipAddress, String groupId, String savePath, String chinesePath) {
        FileEntity file = new FileEntity();
        file.setGroupId(groupId);
        file.setSavePath(savePath);
        file.setIpAddress(ipAddress);
        file.setChinesePath(chinesePath);
        file.setDelete(false);
        file.setCreatedDate(new Date());
        file.setModifyDate(new Date());

        fileRepository.insert(file);
    }

    @Override
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();

        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }

        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);

        try {
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            logger.error("upload file Exception!", e);
        }

        if (fileAbsolutePath == null) {
            logger.error("upload file failed,please upload again!");
        }

        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        String chinesePath = fileName;
        String ipAddress = FastDFSClient.getTrackerUrl();
        String groupId = fileAbsolutePath[0];
        String savePath = path;

        if (fileName.contains("&#")) {
            chinesePath = fetchShowFilePath(fileName);
        }

        System.out.println(chinesePath);

        saveFileInfo(ipAddress, groupId, savePath, chinesePath);
        return "Successful upload files!";
    }

    /**
     * Return: Customer upload file name. Because uploaded file name is error code.
     *
     * @param filePath: Original File name.
     * @return
     */
    private String fetchShowFilePath(String filePath) {
        String[] filePaths = filePath.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        int filePathLength = filePaths.length;

        for (int i = 0; i < filePathLength; i++) {
            if (filePaths[i].contains("&#") && filePaths[i].contains(".")) { // Chinese file
                String[] chineseFilePaths = filePaths[i].split("\\.");
                stringBuilder.append(FileUtil.hexStringToChinese(chineseFilePaths[0]));
                stringBuilder.append(".");
                stringBuilder.append(chineseFilePaths[1]);
            } else if (filePaths[i].contains("&#")) { // Chinese directory
                stringBuilder.append(FileUtil.hexStringToChinese(filePaths[i]));
                stringBuilder.append("/");
            } else if (filePaths[i].contains(".")) { // English file
                stringBuilder.append(filePaths[i]);
            } else { // English directory
                stringBuilder.append(filePaths[i]);
                stringBuilder.append("/");
            }
        }

        return stringBuilder.toString();
    }
}
