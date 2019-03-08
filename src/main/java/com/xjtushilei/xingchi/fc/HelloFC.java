package com.xjtushilei.xingchi.fc;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.xjtushilei.xingchi.entity.ApiRequest;
import com.xjtushilei.xingchi.entity.ApiResponse;

import java.util.HashMap;
import java.util.Map;

public class HelloFC implements PojoRequestHandler<ApiRequest, ApiResponse> {

    public ApiResponse handleRequest(ApiRequest request, Context context) {
        // Get ApiRequest info
        context.getLogger().info(request.toString());
        String path = request.getPath();
        String httpMethod = request.getHttpMethod();
        String body = request.getBody();
        context.getLogger().info("path：" + path);
        context.getLogger().info("path：" + request.getQueryParameters());
        context.getLogger().info("httpMethod：" + httpMethod);
        context.getLogger().info("body：" + body);

        // Deal with your own logic here

        // ApiResponse example
        Map headers = new HashMap();
        boolean isBase64Encoded = false;
        int statusCode = 200;
        String returnBody = request.getQueryParameters().toString();
        return new ApiResponse(headers, isBase64Encoded, statusCode, returnBody);
    }
}