package com.kylinno.legal.document;

import com.kylinno.legal.document.domain.entity.FileEntity;
import com.kylinno.legal.document.domain.entity.TestFileInfo;
import com.kylinno.legal.document.domain.repository.FileRepository;
import com.kylinno.legal.document.domain.repository.TestFileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastDFSApplication.class)
public class FileTest {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TestFileRepository testFileRepository;

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
            System.out.print((char) Integer.parseInt(codes[i]));
        }
    }

    @Test
    public void testSplitChineseFile() {
        String path = "&#26032;&#24314;&#25991;&#26412;&#25991;&#26723;.txt";
        System.out.println(path.contains("\\."));
        /*String[] splits = path.split("\\.");
        System.out.println(splits.length);
        for (String item : splits) {
            System.out.println(item);
        }*/
    }

    @Test
    public void test() {
        String path = "test1/test2/test.txt";
        //String path = "test1/test1.txt";

        //先判断是否存在相同的目录
        String[] paths = path.split("/");

        TestFileInfo resultFile = null;
        Optional<TestFileInfo> resultFileOptional = Optional.empty();
        int index = 1;
        StringBuilder stringBuilder = new StringBuilder(paths[0]);

        /*for (int i = 0; true; i++) {
            resultFileOptional = Optional.ofNullable(testFileRepository.findByName(stringBuilder.toString()));
        }*/

        // 没有相同的开始目录，直接存入；
        TestFileInfo testFileInfo = testFile2(path);
        System.out.println(testFileInfo);
        testFileRepository.save(testFileInfo);
    }

    public TestFileInfo testFile2(String path) {
        String[] paths = path.split("/");
        int pathLength = paths.length;
        StringBuilder stringBuilder = new StringBuilder();

        if (pathLength == 1) {
            return new TestFileInfo(paths[0], "192.168.10.31:8888/Group1/M0/00/1.txt");
        }

        for (int i = 1; i < pathLength - 1; i++) {
            stringBuilder.append(paths[i]);
            stringBuilder.append("/");
        }

        stringBuilder.append(paths[pathLength - 1]);

        TestFileInfo t = testFile2(stringBuilder.toString());
        TestFileInfo t1 = new TestFileInfo(paths[0]);
        t1.getFiles().add(t);
        return t1;
    }

    public TestFileInfo testFile3(String path, TestFileInfo fileInfo) {
        String[] paths = path.split("/");
        int pathLength = paths.length;
        StringBuilder stringBuilder = new StringBuilder();
        if (fileInfo == null) {
            fileInfo = new TestFileInfo();
            fileInfo.setName(paths[0]);
        }

        if (pathLength == 1) {
            return new TestFileInfo(paths[0], "192.168.10.31:8888/Group1/M0/00/1.txt");
        }

        for (int i = 1; i < pathLength - 1; i++) {
            stringBuilder.append(paths[i]);
            stringBuilder.append("/");
        }

        stringBuilder.append(paths[pathLength - 1]);

        TestFileInfo t = testFile3(stringBuilder.toString(), fileInfo);
        System.out.println("----------------------------------------------------");

        for (TestFileInfo item : fileInfo.getFiles()) {
            System.out.println(item.getName());
        }

        System.out.println("**************************************");
        System.out.println(t.getName());


        if (!(fileInfo.getFiles().contains(t.getName()))) {
            fileInfo.getFiles().add(t);
        }

        return fileInfo;
    }


}
