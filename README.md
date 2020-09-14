# Getting Started
## 准备工作
### 用户申请商户配置步骤
#### 申请商户:
 1. 用户联系Settlity运营中心，运营人员会提供商户号、DRS公钥、商户公私钥、以及helloworld源码包、以及Settlity测试网络VPN账号
 2. 用户通过VPN接入测试网络后，需向运营中心提供测试网络分配的IP地址。
#### 用户开发环境准备:
 1. 用户本地环境需要安装JDK8及以上。
 2. 需要mysql5.7+版本的数据库
 3. 创建数据库:helloworld,不需要建表脚本，程序会自动创建，建库脚本如下:
    ```
    CREATE DATABASE `helloworld` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
    ```
## 程序启动
- 准备工作就绪后，导入helloworld工程，修改配置文件`application.yml`对应的配置(数据库连接配置/商户号/DRS公钥/商户持有的公私钥)。
- 本地直接运行`HelloworldApplication`
- 浏览器打开体验[Swagger地址](http://localhost:18080/swagger-ui.html) 可以看到可体验的各种报文(目前支持数字货币发行/转账)
## 报文体验步骤说明
 1. 用户启动程序后，打开Swagger网页找到`DRS非报文查询API`,获取一个地址，并记下。
 2. 用户找到`SMT报文体验入口`,先发行一个数字货币资产。提交后会返回messageId，用户可以记下这个ID，等待DRS通知发行是否成功。
 3. DRS回调通知可能会1~5s间，用户可以使用上一步骤中的messageId搜索helloworld服务日志，查看回调结果，或者可以根据messageId查询数据库表smt_message查看回调结果。
  > 1. select * from smt_message where message_id=${messageId}，检查response_code字段。
  > 2. 查看response_code,如果为空表示，DRS还未回调;如果为success,表示您发送的报文已成功上链，txs字段中可以找到txid(可能多个)，通过区块链浏览器查看该笔交易
  > 3. 否则表示该报文上链失败。可以通过response_message看到失败原因。
## 程序包介绍
  1. **配置文件**:`application.yml`
   - 数据库连接配置，用户可以修改连接地址及用户密码。
   - logback日志配置(推荐使用默认配置)
   - 服务启动端口配置(推荐使用默认配置，如果用户需要修改，修改后的端口号需要向运营中心提供已修改的端口)
   - drs公私钥及商户号
  2. **common**:项目工程通用处理类
  3. **config**:DRS配置;Swagger配置(引入swagger方便用户体验)
  4. **constant**:常量类
  5. **controller**:用户体验入口
  6. **crypto**:DRS通讯加解密工具类
  7. **dao**:数据持久化层
  8. **httpclient**:请求DRS的HTTP通讯客户端
  9. **service**:业务逻辑处理层
  10. **utils**:通用工具类
  11. **vo**:用户请求及响应model
  12. **HelloworldApplication**:程序启动入口