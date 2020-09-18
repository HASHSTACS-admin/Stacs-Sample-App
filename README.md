# Getting Started
## 1. 准备工作
### 1.1. 用户申请商户配置步骤
#### 申请商户:

 1. 用户联系Settlity运营中心，运营人员会提供商户号、DRS公钥、商户公私钥、以及helloworld源码包、以及Settlity测试网络VPN账号
  - 可以在[支付宝开放平台](https://ideservice.alipay.com/ide/getPluginUrl.htm?clientType=assistant&platform=win&channelType=WEB) 下载开发助手，生成公私钥对（RSA2-PKS8）
  - 可以通过openssl命令生成:
  ```shell script
    ## 生成 RSA 私钥（传统格式的）
    openssl genrsa -out rsa_private_key.pem 1024
    ## 将传统格式的私钥转换成 PKCS#8 格式的（JAVA需要使用的私钥需要经过PKCS#8编码）
    openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt
    ## 生成 RSA 公钥
    openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
  ```
 2. 用户通过VPN接入测试网络后，需向运营中心提供测试网络分配的IP地址。
 3. 以上是需要DRS主动回调helloworld,如果不需要回调，用户可以不用接入VPN网络，由用户自己实现主动查询报文结果。可以在Swagger`DRS非报文查询API`中找到`/smt/api/querySmtResult`接口示例。
 
### 1.2. 用户开发环境准备

 1. 用户本地环境需要安装JDK8及以上。
 2. 需要mysql5.7+版本的数据库
 3. 创建数据库:helloworld,不需要建表脚本，程序会自动创建，建库脚本如下:
    ```
    CREATE DATABASE `helloworld` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
    ```
## 2. 程序启动

- 准备工作就绪后，导入helloworld工程，修改配置文件`application.yml`对应的配置(数据库连接配置/商户号/DRS公钥/商户持有的公私钥)。
- 本地直接运行`HelloworldApplication`
- 浏览器打开体验[Swagger地址](http://localhost:18080/swagger-ui.html) 可以看到可体验的各种报文(目前支持数字货币发行/转账)
## 3. 报文体验步骤说明

 1. 用户启动程序后，打开Swagger网页找到`DRS非报文查询API`,获取一个地址，并记下。
 2. 用户找到`SMT报文体验入口`,先发行一个数字货币资产。提交后会返回messageId，用户可以记下这个ID，等待DRS通知发行是否成功。
 3. DRS回调通知可能会1~5s间，用户可以使用上一步骤中的messageId搜索helloworld服务日志，查看回调结果，或者可以根据messageId查询数据库表smt_message查看回调结果。
 4. 开发dapp过程中，报文参数一定要谨慎，请认真参考[报文使用手册](https://confluence.primeledger.cn/pages/viewpage.action?pageId=30999870)
  > 1. select * from smt_message where message_id=${messageId}，检查response_code字段。
  > 2. 查看response_code,如果为空表示，DRS还未回调;如果为success,表示您发送的报文已成功上链，txs字段中可以找到txid(可能多个)，通过区块链浏览器查看该笔交易
  > 3. 否则表示该报文上链失败。可以通过response_message看到失败原因。
## 4. 程序包介绍

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