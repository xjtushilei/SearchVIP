# 星驰全程 会员查询系统

## 技术
- 数据库：mysql
- web框架：spring-boot 1.5.3
- jdk 8


## 关键文件

- `src\main\resources\application.properties` 配置了数据库信息
    1. application.properties  选择激活哪一个配置文件
    1. application-dev.properties 开发板配置文件
    1. application-prod.properties 生产版配置文件
- `src\main\resources\static\js\config.js `  配置前台ajax所有url请求的前缀，若是有域名的 ，ip=''。没有域名的，请加相关的路径。比如我们之前用的是"  http://xxxxxxx.com/xingchi/ " ,属于没有域名的，所以在配置时候需要提供'xingchi'这样的前缀


## 启动和部署

### 编译
mvn package

### 部署
在target目录下

java -jar xingchi.jar

后台部署

nohup java -jar xingchi.jar &

