package com.example.grpcserver.service;


import com.example.grpcserver.mapper.BasicInfoMapper;
import com.example.grpcserver.model.BasicInfo;
import com.isc.mcb.rpc.bse.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author z.Taghizadeh
 */
@GRpcService
public class BaseInfoGRpcService extends BasicInfoServiceGrpc.BasicInfoServiceImplBase {

    @Autowired
    private BasicInfoService basicInfoService;

    private BasicInfoMapper basicInfoMapper = Mappers.getMapper(BasicInfoMapper.class);

    @Override
    public void getBasicInfoById(BasicInfoInput request, StreamObserver<BasicInfoDataOutput> responseObserver) {
        BasicInfoDataOutput response = BasicInfoDataOutput.newBuilder().build();
        try {
            long id = request.getId();
            BasicInfo basicInfoById = basicInfoService.getBasicInfoById(id);

            if (basicInfoById != null) {
                response = basicInfoMapper.fromBasicInfo(basicInfoById);
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription(e.getMessage())));
        }
    }

    @Override
    public void insertBasicInfo(BasicInfoInput request, StreamObserver<Output> responseObserver) {
        Output response = Output.newBuilder().build();
        try {
            BasicInfo info = basicInfoMapper.fromBasicInfoInput(request);
            BasicInfo basicInfo = basicInfoService.saveBasicInfo(info);

            if (basicInfo != null && basicInfo.getId() != null) {
                response = Output.newBuilder().setId(basicInfo.getId()).build();
            }

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription(e.getMessage())));
        }
    }

    @Override
    public void getAllBasicInfo(BasicInfoInput request, StreamObserver<BasicInfoDataOutputList> responseObserver) {
        BasicInfoDataOutputList response = BasicInfoDataOutputList.newBuilder().build();
        try {

            List<BasicInfo> basicInfoList = basicInfoService.getAllBasicInfo();

            if (basicInfoList != null && basicInfoList.size() != 0) {
                List<BasicInfoDataOutput> basicInfoDataOutputs = basicInfoMapper.fromBasicInfoList(basicInfoList);
                response = BasicInfoDataOutputList
                        .newBuilder()
                        .addAllItems(basicInfoDataOutputs)
                        .build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription(e.getMessage())));
        }
    }

}
