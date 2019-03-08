package com.xjtushilei.xingchi.fc;

import com.alibaba.fastjson.JSON;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.Row;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.xjtushilei.xingchi.entity.ApiRequest;
import com.xjtushilei.xingchi.entity.ApiResponse;
import com.xjtushilei.xingchi.entity.KV;
import com.xjtushilei.xingchi.entity.Vipcard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xjtushilei.xingchi.service.OtsService.getClient;
import static com.xjtushilei.xingchi.service.OtsService.getRange;

public class SearchFC implements PojoRequestHandler<ApiRequest, ApiResponse> {

    public ApiResponse handleRequest(ApiRequest request, Context context) {
        FunctionComputeLogger logger = context.getLogger();
        Map queryParameters = request.getQueryParameters();
        String q = queryParameters.get("vipID").toString();
        logger.info(q);
        List<Row> list;
        if (q.length() == 11) {
            list = getRange(getClient(),
                    "vipcard",
                    Arrays.asList(new KV("phone", PrimaryKeyValue.fromString(q)), new KV("vipID", PrimaryKeyValue.INF_MIN)),
                    Arrays.asList(new KV("phone", PrimaryKeyValue.fromString(q)), new KV("vipID", PrimaryKeyValue.INF_MAX))
            );
        } else {
            list = getRange(getClient(),
                    "vipcard",
                    Arrays.asList(new KV("phone", PrimaryKeyValue.INF_MIN), new KV("vipID", PrimaryKeyValue.fromString(q))),
                    Arrays.asList(new KV("phone", PrimaryKeyValue.INF_MAX), new KV("vipID", PrimaryKeyValue.fromString(q)))
            );
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        if (list.size() == 0) {
            HashMap<String, Object> bodyhMap = new HashMap<>();
            bodyhMap.put("state", "您输入的会员卡号不存在！请重新输入进行查询~");
            return new ApiResponse(headers, false, 400, JSON.toJSONString(bodyhMap));
        } else {
            Vipcard vipcard = new Vipcard();
            vipcard.setBalance(list.get(0).getLatestColumn("balance").getValue().asString());
            vipcard.setCardType(list.get(0).getLatestColumn("cardType").getValue().asString());
            vipcard.setRemainingPoints(list.get(0).getLatestColumn("remainingPoints").getValue().asString());
            vipcard.setCarNumber(list.get(0).getLatestColumn("carNumber").getValue().asString());
            vipcard.setCarNumber(list.get(0).getPrimaryKey().getPrimaryKeyColumn("phone").getValue().asString());
            vipcard.setVipID(list.get(0).getPrimaryKey().getPrimaryKeyColumn("vipID").getValue().asString());
            return new ApiResponse(headers, false, 200, JSON.toJSONString(vipcard));
        }
    }

}