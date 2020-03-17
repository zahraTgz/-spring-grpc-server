package com.example.grpcserver.service;

import com.example.grpcserver.model.BasicInfo;
import com.example.grpcserver.repository.BasicInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author z.Taghizadeh
 */
@Service
@Transactional
public class BasicInfoService {

    private final BasicInfoRepository repository;

    public BasicInfoService(BasicInfoRepository repository) {
        this.repository = repository;
    }


    public BasicInfo getBasicInfoById(long id) {
        return repository.getOne(id);
    }

    public BasicInfo saveBasicInfo(BasicInfo info) {
        return repository.save(info);
    }

    public List<BasicInfo> getAllBasicInfo() {
        return repository.findAll();
    }
}
