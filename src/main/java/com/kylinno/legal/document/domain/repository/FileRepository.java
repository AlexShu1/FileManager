package com.kylinno.legal.document.domain.repository;

import com.kylinno.legal.document.domain.entity.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface FileRepository extends MongoRepository<FileEntity, Serializable> {
}
