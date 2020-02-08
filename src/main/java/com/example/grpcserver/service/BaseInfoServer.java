package com.example.grpcserver.service;


import com.example.grpcserver.model.BasicInfo;
import com.isc.mcb.rpc.bse.*;
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
public class BaseInfoServer extends BasicInfoServiceGrpc.BasicInfoServiceImplBase {

    @Autowired
    private BasicInfoService basicInfoService;

    @Override
    public void getBasicInfoById(BasicInfoInput request, StreamObserver<BasicInfoDataOutput> responseObserver) {
        BasicInfoDataOutput response = BasicInfoDataOutput.newBuilder().build();
        try {

            long id = request.getId();
            BasicInfo basicInfoById = basicInfoService.getBasicInfoById(id);

            if (basicInfoById != null) {

                response = BasicInfoDataOutput.newBuilder()
                        .setId(basicInfoById.getId())
                        .setCode(basicInfoById.getCode())
                        .setName(basicInfoById.getName())
                        .setEnglishName(basicInfoById.getEnglishName())
                        .setIsActive(basicInfoById.getActive())
                        .build();
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

            BasicInfo info = new BasicInfo();
            info.setCode((int) request.getCode());
            info.setName(request.getName());
            info.setEnglishName(request.getEnglishName());
            info.setActive(request.getIsActive());

            BasicInfo basicInfo = basicInfoService.saveBasicInfo(info);
            if (basicInfo != null && basicInfo.getId() != null) {

                response = Output.newBuilder().setErrorCode(basicInfo.getId()).build();
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
                for (BasicInfo basicInfo : basicInfoList) {
                    BasicInfoDataOutput basicInfoDataOutput = BasicInfoDataOutput.newBuilder()
                            .setId(basicInfo.getId())
                            .setName(basicInfo.getName())
                            .setCode(basicInfo.getCode())
                            .setEnglishName(basicInfo.getEnglishName() == null ? "" : basicInfo.getEnglishName())
                            .setIsActive(basicInfo.getActive())
                            .build();

                    response = response.toBuilder()
                            .addItems(basicInfoDataOutput)
                            .build();
                }

            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription(e.getMessage())));
        }

    }
}
