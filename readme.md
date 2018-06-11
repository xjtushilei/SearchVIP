# 星驰全程 会员查询系统

## 技术
- 数据库：h2
- web框架：spring-boot 2.0.2
- jdk 8


## 关键文件

- `src\main\resources\application.yml` 配置了数据库信息



## 启动和部署

### 编译
gradlew build

### 部署
在 build/libs 目录下

java -jar xingchi.jar

linux下后台部署

nohup java -jar xingchi.jar &

