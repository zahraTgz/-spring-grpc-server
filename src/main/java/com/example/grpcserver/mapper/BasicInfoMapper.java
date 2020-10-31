package com.example.grpcserver.mapper;

import com.example.grpcserver.model.BasicInfo;
import com.example.grpcserver.repository.BaseMapper;
import com.isc.mcb.rpc.bse.BasicInfoMessage;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

/**
 * @author z.Taghizadeh
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {BaseMapper.class},
        componentModel = "spring")
public interface BasicInfoMapper {

    BasicInfoMessage fromBasicInfo(BasicInfo basicInfo);

    BasicInfo toBasicInfo(BasicInfoMessage basicInfo);

    List<BasicInfoMessage> fromBasicInfoList(List<BasicInfo> basicInfoList);

}
