package com.xjtushilei.xingchi.service;

import com.alibaba.fastjson.JSON;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.xjtushilei.xingchi.entity.KV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtsService {
    public static void main(String file_path[]) {
//        List<KV> pkList = Arrays.asList(new KV("urlName", "lianjie1"));
//        List<KV> columnList = Arrays.asList(new KV("urlString", "1111111urlStringfasfa"));
//        putRow(getClient(), "url", pkList, columnList);
//        pkList = Arrays.asList(new KV("urlName", "lianjie2"));
//        Arrays.asList(new KV("urlString", "百度2222222sfa"));
//        putRow(getClient(), "url", pkList, columnList);
////        deleteRow(getClient(), "url", pkList);
//        System.out.println(getRow(getClient(), "url", pkList).getLatestColumn("urlString").getValue());
//        putRow(getClient(),
//                "url",
//                Collections.singletonList(new KV("urlName", "lianjie1")),
//                Collections.singletonList(new KV("urlString", "111111111")));
//        List<KV> pkList = Arrays.asList(new KV("phone", "sdf232131asff"), new KV("vipID", "312432") );
//        List<KV> columnList = new ArrayList<>();
//        columnList.add(new KV("cardType", "中文"));
//        columnList.add(new KV("balance", "12341231"));
//        columnList.add(new KV("remainingPoints", "32"));
//        columnList.add(new KV("carNumber", "111"));
//        putRow(getClient(), "vipcard", pkList, columnList);
//        getRow(getClient(), "vipcard", Arrays.asList(new KV("phone", "123123123"))).getLatestColumn("urlString").getValue().toString();

        getRange(getClient(),
                "vipcard",
                Arrays.asList(new KV("phone", PrimaryKeyValue.fromString("13703560785")), new KV("vipID", PrimaryKeyValue.INF_MIN)),
                Arrays.asList(new KV("phone", PrimaryKeyValue.fromString("13703560785")), new KV("vipID", PrimaryKeyValue.INF_MAX))
        ).forEach(r -> {
            System.out.println(r.getPrimaryKey().getPrimaryKeyColumn("phone"));
            System.out.println(r.getPrimaryKey().getPrimaryKeyColumn("vipID"));
        });
        System.out.println("==========================");
        getRange(getClient(),
                "vipcard",
                Arrays.asList(new KV("phone", PrimaryKeyValue.INF_MIN), new KV("vipID", PrimaryKeyValue.fromString("会员卡号"))),
                Arrays.asList(new KV("phone", PrimaryKeyValue.INF_MAX), new KV("vipID", PrimaryKeyValue.fromString("会员卡号")))
        ).forEach(r -> {
            System.out.println(r.getPrimaryKey().getPrimaryKeyColumn("phone"));
            System.out.println(r.getPrimaryKey().getPrimaryKeyColumn("vipID"));
        });
    }

    public static SyncClient getClient() {
        final String endPoint = "https://xingchiquancheng.cn-beijing.ots.aliyuncs.com";
        final String accessKeyId = "";
        final String accessKeySecret = "";
        final String instanceName = "xingchiquancheng";
        return new SyncClient(endPoint, accessKeyId, accessKeySecret, instanceName);
    }

    public static PutRowResponse putRow(SyncClient client, String tableName, List<KV> pkList, List<KV> columnList) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        for (KV kv : pkList) {
            primaryKeyBuilder.addPrimaryKeyColumn(kv.k, PrimaryKeyValue.fromString(kv.v.toString()));
        }
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange(tableName, primaryKey);

        // 加入一些属性列
        for (KV kv : columnList) {
            rowPutChange.addColumn(new Column(kv.k, ColumnValue.fromString(kv.v.toString())));
        }
        PutRowResponse response = client.putRow(new PutRowRequest(rowPutChange));
        client.shutdown();
        return response;
    }

    public static DeleteRowResponse deleteRow(SyncClient client, String tableName, List<KV> pkList) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        for (KV kv : pkList) {
            primaryKeyBuilder.addPrimaryKeyColumn(kv.k, PrimaryKeyValue.fromString(kv.v.toString()));
        }
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowDeleteChange rowDeleteChange = new RowDeleteChange(tableName, primaryKey);

        DeleteRowResponse response = client.deleteRow(new DeleteRowRequest(rowDeleteChange));
        client.shutdown();
        return response;
    }

    public static Row getRow(SyncClient client, String tableName, List<KV> pkList) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        for (KV kv : pkList) {
            primaryKeyBuilder.addPrimaryKeyColumn(kv.k, PrimaryKeyValue.fromString(kv.v.toString()));
        }
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        // 读一行
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(tableName, primaryKey);
        // 设置读取最新版本
        criteria.setMaxVersions(1);
        GetRowResponse getRowResponse = client.getRow(new GetRowRequest(criteria));
        client.shutdown();
        return getRowResponse.getRow();
    }

    public static List<Row> getRange(SyncClient client, String tableName, List<KV> startPkList, List<KV> endPkList) {
        RangeRowQueryCriteria rangeRowQueryCriteria = new RangeRowQueryCriteria(tableName);
        // 设置起始主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        for (KV kv : startPkList) {
            primaryKeyBuilder.addPrimaryKeyColumn(kv.k, (PrimaryKeyValue) kv.v);
        }
        rangeRowQueryCriteria.setInclusiveStartPrimaryKey(primaryKeyBuilder.build());
        // 设置结束主键
        primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        for (KV kv : endPkList) {
            primaryKeyBuilder.addPrimaryKeyColumn(kv.k, (PrimaryKeyValue) kv.v);
        }
        rangeRowQueryCriteria.setExclusiveEndPrimaryKey(primaryKeyBuilder.build());
        rangeRowQueryCriteria.setMaxVersions(1);

        List<Row> res = new ArrayList<>();
        while (true) {
            GetRangeResponse getRangeResponse = client.getRange(new GetRangeRequest(rangeRowQueryCriteria));
            res.addAll(getRangeResponse.getRows());
//            for (Row row : getRangeResponse.getRows()) {
//                System.out.println(row);
//            }
            // 若nextStartPrimaryKey不为null, 则继续读取.
            if (getRangeResponse.getNextStartPrimaryKey() != null) {
                rangeRowQueryCriteria.setInclusiveStartPrimaryKey(getRangeResponse.getNextStartPrimaryKey());
            } else {
                break;
            }
        }
        client.shutdown();
        return res;
    }


}
