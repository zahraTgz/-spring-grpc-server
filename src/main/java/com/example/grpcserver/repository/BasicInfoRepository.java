package com.example.grpcserver.repository;

import com.example.grpcserver.model.BasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author z.Taghizadeh
 */
@Repository
public interface BasicInfoRepository extends JpaRepository<BasicInfo, Long> {
}
