# FROM 基础镜像
FROM livingobjects/jre8:latest
# MAINTAINER 维护者信息
MAINTAINER Gump
# VOLUME 用于指定持久化目录
VOLUME /tmp
# 将本地文件添加到容器中,并改名
ADD ./target/*.jar app.jar
# 配置容器，使其可执行
ENTRYPOINT ["java","-jar","/app.jar"]

# Ubuntu 时区
# RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime