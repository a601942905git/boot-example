## 1.构建镜像
切到Dockerfile文件所在目录，执行
```shell
docker build -t a601942905/app .
```
## 2.运行镜像
```shell
 docker run --name myboot -p 8080:8080 -d a601942905/app
```

## 3.访问服务
```shell
curl localhost:8080
```