package com.xjtushilei.xingchi.fc;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.xjtushilei.xingchi.entity.ApiRequest;
import com.xjtushilei.xingchi.entity.ApiResponse;
import com.xjtushilei.xingchi.entity.KV;
import com.xjtushilei.xingchi.entity.Vipcard;
import com.xjtushilei.xingchi.service.ExcelService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static com.xjtushilei.xingchi.service.OtsService.getClient;
import static com.xjtushilei.xingchi.service.OtsService.putRow;

public class SubmitExcelFC implements PojoRequestHandler<ApiRequest, ApiResponse> {

    public ApiResponse handleRequest(ApiRequest request, Context context) {
        FunctionComputeLogger logger = context.getLogger();
//        logger.info(request.toString());

        InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(request.getBody()));
        ArrayList<Vipcard> list = ExcelService.readExcel(input);
        for (Vipcard vipcard : list) {
            List<KV> pkList = Arrays.asList(new KV("phone", vipcard.getPhone()), new KV("vipID", vipcard.getVipID()));
            List<KV> columnList = new ArrayList<>();
            columnList.add(new KV("cardType", vipcard.getCardType()));
            columnList.add(new KV("balance", vipcard.getBalance()));
            columnList.add(new KV("remainingPoints", vipcard.getRemainingPoints()));
            columnList.add(new KV("carNumber", vipcard.getCarNumber()));
            putRow(getClient(), "vipcard", pkList, columnList);
        }

        // ApiResponse example
        Map headers = new HashMap();
        headers.put("Content-Type", "application/json;charset=utf-8");
        HashMap<String, Object> bodyhMap = new HashMap<>();
        bodyhMap.put("successNum", list.size());
        logger.info("request.getIsBase64Encoded():" + request.getIsBase64Encoded());
        return new ApiResponse(headers, false, 200, JSON.toJSONString(bodyhMap));
    }

}