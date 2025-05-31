# TestRepository2

## level2

#### 项目说明

该项目基本完成了考核二的全部内容，多线程的服务端与客户端之间的交互操作，服务端可以根据客户端发送的指令进行响应，所有指令类型均已完成，同时实现了数据的持久化（基于properties实现），将指令的内容读入了文件中（hashString.properties，hashString.properties，hashHash.properties），同时也用文件配置了监听端口号和日志（config.properties），实现了日志功能，存储了用户ip地址等等，对于每个指令均有响应值。

#### 文件说明

scr目录下为整个程序的源码，hashString.properties，hashString.properties，hashHash.properties为不同指令中持久化的文件，config.properties为整个程序的配置文件，socker.loog.0是记录日志文件（日志拥有翻滚功能）。

#### 项目框架

本项目包含六层

1. control层
   - control--控制器，用于进行与用户的交互，主要是解析客户端发送的指令。

2. Main层
   - Main--主方法，启动整个程序。

3. managerSystem层
   - managerSystem--负责管理和调度接收请求的接口，用于开启服务端和客户端的交互。

4. service层
   - service--负责处理业务逻辑，从control层获取解析后的用户指令，调用模拟数据库，进行业务处理。

5. socket层
   - client--客户端。
   - serviceSocket--服务端。

6. tool层
   - database--模拟数据库，用于调用文件操作存储整个程序的所有数据。
   - file--文件操作，对整个程序实现文件读取和文件存储。
   - log--日志操作，实现日志记录。

#### 使用说明

项目使用的全为Java原生代码，可以直接克隆到IDEA然后在主方法Main中开启服务端，在socket包中的client开启客户端，即可开启整个程序。
