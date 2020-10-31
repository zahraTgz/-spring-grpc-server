package com.example.grpcserver.service;


import com.example.grpcserver.mapper.BasicInfoMapper;
import com.example.grpcserver.model.BasicInfo;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.isc.mcb.rpc.bse.BasicInfoDataOutputList;
import com.isc.mcb.rpc.bse.BasicInfoMessage;
import com.isc.mcb.rpc.bse.BasicInfoServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author z.Taghizadeh
 */

@GRpcService
public class BaseInfoGRpcService extends BasicInfoServiceGrpc.BasicInfoServiceImplBase {
    private final BasicInfoService basicInfoService;
    private final BasicInfoMapper basicInfoMapper;

    @Autowired
    public BaseInfoGRpcService(BasicInfoService basicInfoService, BasicInfoMapper basicInfoMapper) {
        this.basicInfoService = basicInfoService;
        this.basicInfoMapper = basicInfoMapper;
    }

    @Override
    public void getBasicInfoById(Int64Value request, StreamObserver<BasicInfoMessage> responseObserver) {
        BasicInfoMessage response = BasicInfoMessage.newBuilder().build();
        try {
            Long id = request.getValue();
            BasicInfo basicInfoById = basicInfoService.getBasicInfoById(id);

            if (basicInfoById != null) {
                response = basicInfoMapper.fromBasicInfo(basicInfoById);
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("can not found")));
        }
    }

    @Override
    public void insertBasicInfo(BasicInfoMessage request, StreamObserver<Empty> responseObserver) {
        Empty response = Empty.newBuilder().build();
        try {
            basicInfoService.saveBasicInfo(basicInfoMapper.toBasicInfo(request));

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("can not insert")));
        }
    }

    @Override
    public void getAllBasicInfo(Empty request, StreamObserver<BasicInfoDataOutputList> responseObserver) {
        BasicInfoDataOutputList response = BasicInfoDataOutputList.newBuilder().build();
        try {

            List<BasicInfo> basicInfoList = basicInfoService.getAllBasicInfo();

            if (basicInfoList != null && basicInfoList.size() != 0) {
                List<BasicInfoMessage> basicInfoDataOutputs = basicInfoMapper.fromBasicInfoList(basicInfoList);
                response = BasicInfoDataOutputList
                        .newBuilder()
                        .addAllItems(basicInfoDataOutputs)
                        .build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("can not get all")));
        }
    }

}
