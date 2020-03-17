package com.example.grpcserver.mapper;

import com.example.grpcserver.model.BasicInfo;
import com.isc.mcb.rpc.bse.BasicInfoDataOutput;
import com.isc.mcb.rpc.bse.BasicInfoInput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

/**
 * @author z.Taghizadeh
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BasicInfoMapper {

    BasicInfoDataOutput fromBasicInfo(BasicInfo basicInfo);

    List<BasicInfoDataOutput> fromBasicInfoList(List<BasicInfo> basicInfoList);

    BasicInfo fromBasicInfoInput(BasicInfoInput basicInfoInput);

}
