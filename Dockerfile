FROM openjdk:8-jdk-alpine
RUN mkdir -p /root/workspace/project
COPY . /root/workspace/project
WORKDIR /root/workspace/project

RUN set -ex && ./gradlew build

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom",\
                    "-Dspring.profiles.active=docker",\
                    "-jar","/root/workspace/project/build/libs/xingchi.jar"]

# 外部启动脚本: docker run --name xingchi -e JAVA_OPTS='-Xmx200m' -p 8081:8081 -v /data/h2/:/data/h2/ -d --restart=unless-stopped registry.cn-hangzhou.aliyuncs.com/scriptshi/xingchi