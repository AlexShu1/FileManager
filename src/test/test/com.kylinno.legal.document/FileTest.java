package com.kylinno.legal.document;

import com.kylinno.legal.document.domain.entity.FileEntity;
import com.kylinno.legal.document.domain.repository.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes  = FastDFSApplication.class)
public class FileTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void testUploadFile() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath("/testFile/a.jpg");
        fileEntity.setRealPath("/M0/01/00/asdadsadasda.jpg");

        System.out.println("Hello World!");
        fileRepository.insert(fileEntity);
    }
}
