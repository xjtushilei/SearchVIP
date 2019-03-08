package com.xjtushilei.xingchi.fc;

import com.alibaba.fastjson.JSON;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.xjtushilei.xingchi.entity.ApiRequest;
import com.xjtushilei.xingchi.entity.ApiResponse;
import com.xjtushilei.xingchi.entity.KV;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.xjtushilei.xingchi.service.OtsService.getClient;
import static com.xjtushilei.xingchi.service.OtsService.getRow;

public class GetUrlFC implements PojoRequestHandler<ApiRequest, ApiResponse> {

    public ApiResponse handleRequest(ApiRequest request, Context context) {
        FunctionComputeLogger logger = context.getLogger();

        String lianjie1 = getRow(getClient(), "url", Collections.singletonList(new KV("urlName", "lianjie1"))).getLatestColumn("urlString").getValue().toString();
        String lianjie2 = getRow(getClient(), "url", Collections.singletonList(new KV("urlName", "lianjie2"))).getLatestColumn("urlString").getValue().toString();


        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        HashMap<String, Object> bodyhMap = new HashMap<>();
        bodyhMap.put("lianjie1", lianjie1);
        bodyhMap.put("lianjie2", lianjie2);
        logger.info("request.getIsBase64Encoded():" + request.getIsBase64Encoded());
        return new ApiResponse(headers, false, 200, JSON.toJSONString(bodyhMap));
    }

}