package com.kylinno.legal.document.domain.repository;

import com.kylinno.legal.document.domain.entity.TestFileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface TestFileRepository extends MongoRepository<TestFileInfo, Serializable> {

    TestFileInfo findByName(String name);
}
