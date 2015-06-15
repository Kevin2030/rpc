# 分布式 RPC 框架

## 技术选型

1. 依赖注入：Spring 3.2.12
2. 数据传输：Netty 4.0.24
3. 序列化：Protostuff 1.0.8
4. 服务注册：ZooKeeper 3.4.6

## 使用方法

1. 启动 ZooKeeper 服务器（测试时可在本地启动）。
2. 运行`rpc-sample-server`模块中的`RpcBootstrap`类，启动 RPC 服务器。
3. 运行`rpc-sample-app`模块中的`HelloServiceTest`单元测试，查看测试是否通过。

## 相关配置

1. 服务端配置：打开`rpc-sample-server`模块中的`rpc.properties`文件，可配置`ZooKeeper 地址`与`RPC 服务器地址`。
2. 客户端配置：打开`rpc-sample-app`模块中的`rpc.properties`文件，可配置`ZooKeeper 地址`。