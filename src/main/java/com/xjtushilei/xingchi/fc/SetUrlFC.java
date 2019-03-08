package com.xjtushilei.xingchi.fc;

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
import static com.xjtushilei.xingchi.service.OtsService.putRow;

public class SetUrlFC implements PojoRequestHandler<ApiRequest, ApiResponse> {

    public ApiResponse handleRequest(ApiRequest request, Context context) {
        FunctionComputeLogger logger = context.getLogger();
        logger.info(request.toString());
        Map queryParameters = request.getQueryParameters();
        String lianjie1 = queryParameters.get("lianjie1").toString();
        String lianjie2 = queryParameters.get("lianjie2").toString();

        putRow(getClient(),
                "url",
                Collections.singletonList(new KV("urlName", "lianjie1")),
                Collections.singletonList(new KV("urlString", lianjie1)));
        putRow(getClient(),
                "url",
                Collections.singletonList(new KV("urlName", "lianjie2")),
                Collections.singletonList(new KV("urlString", lianjie2)));


        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain;charset=utf-8");
        logger.info("request.getIsBase64Encoded():" + request.getIsBase64Encoded());
        return new ApiResponse(headers, false, 200, "尊敬的管理员！设置链接地址已成功！");
    }

}