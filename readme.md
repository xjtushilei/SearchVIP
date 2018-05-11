# 星驰全程 会员查询系统

## 技术
- 数据库：mysql
- web框架：spring-boot 1.5.3
- jdk 8


## 关键文件

- `src\main\resources\application.properties` 配置了数据库信息
    1. application.properties  选择激活哪一个配置文件 ,同时配置了启动端口，默认8081
    1. application-dev.properties 开发板配置文件
    1. application-prod.properties 生产版配置文件
    1. application-docker.properties 生产版部署在docker里的配置文件


## 启动和部署

### 编译
gradlew build

### 部署
在 build/libs 目录下

java -jar xingchi.jar

后台部署

nohup java -jar xingchi.jar &

