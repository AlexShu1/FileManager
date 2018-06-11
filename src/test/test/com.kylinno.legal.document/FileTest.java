package com.kylinno.legal.document;

import com.kylinno.legal.document.domain.entity.FileEntity;
import com.kylinno.legal.document.domain.repository.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes  = FastDFSApplication.class)
public class FileTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void testUploadFile() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setChinesePath("/新建文件夹/images.jpg");
        fileEntity.setCreatedDate(new Date());
        fileEntity.setModifyDate(new Date());
        fileEntity.setDelete(false);
        fileEntity.setGroupId("Group1");
        fileEntity.setIpAddress("192.168.130.128:8888");
        fileEntity.setSavePath("/M0/00/asdasdasadsadasdsda.jpg");

        fileRepository.insert(fileEntity);
    }

    @Test
    public void testCode() {
        String code = "&#26032;&#24314;&#25991;&#26412;&#25991;&#26723;";
        code = code.replace("&#", "");
        System.out.println(code);
        String[] codes = code.split(";");
        for (int i = 0; i < codes.length; i++) {
            System.out.print((char)Integer.parseInt(codes[i]));
        }
    }

    @Test
    public void testSplitChineseFile() {
        String path ="&#26032;&#24314;&#25991;&#26412;&#25991;&#26723;.txt";
        System.out.println(path.contains("\\."));
        /*String[] splits = path.split("\\.");
        System.out.println(splits.length);
        for (String item : splits) {
            System.out.println(item);
        }*/
    }
}
