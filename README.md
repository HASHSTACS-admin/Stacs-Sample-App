# STACS Blockchain Sample Application
The STACS Blockchain is a Permissioned Blockchain Network built by Hashstacs that exposes HTTP REST API endpoints for applications to connect and interact with the underlying distributed ledger. This sample application contains all essential tools for communicating with a node residing in a STACS network including encryption of HTTP requests and decryption of HTTP responses since encryption in transit is enforced.

STACS enables business applications to sit on top of a STACS Permissioned Blockchain Network and run business processes by invoking smart contract functions.

The provided sample application here has several examples illustrating how business applications can send transaction requests to a STACS Blockchain network and includes sample code for encryption and decryption of HTTP API messages.

Reach out to the Solutions team at Stacs for questions at support@stacs.io

## Table of Contents
* [Getting Started](#Getting-Started) 
  * [Pre-requisites](#Pre-requisites)
    * [Application Requirements](#Application-Requirements)
    * [Blockchain Network Requirements (3 steps)](#Blockchain-Network-Requirements)
* [STACS Blockchain Introduction](#STACS-Blockchain-Introduction)
* [STACS DRS HTTP API Workflow](#STACS-DRS-HTTP-API-Workflow)
  * [API Request Encryption](#API-Request-Encryption) 
  * [API Request Decryption](#API-Request-Decryption)
  * [Transaction Request Flow to and from the Blockchain](#Transaction-Request-Flow-to-and-from-the-Blockchain)
* [STACS DRS HTTP REST APIs](#STACS-DRS-HTTP-REST-APIs)
* [Settility Message Type (SMT)](#Settility-Message-Type-(SMT))

## Getting Started
To begin, please ensure that you have both the Application Requirements and Blockchain Requirements completed before you embark on this journey with the STACS Blockchain.

### Pre-requisites
#### Application Requirements
To run the application, you will need:
- JDK 8 and higher
- MySQL 5.7 and above
- Maven
- Lombok (on your IDE)
- Create an empty database schema (`helloworld` is the schema name in this sample. To change the db connection details, go to `application.yml`)
```
CREATE DATABASE `helloworld` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
```  

#### Blockchain Network Requirements

You will need to register for a merchant account with a STACS blockchain network operator before you can send the sample API calls to the blockchain successfully. The following describes the steps you will need to take in order to start interfacing with the STACS blockchain.

##### 1. Create a RSA asymmetric key pair 
Before you register, you will need to first create an RSA asymmetric key pair.
There are 2 ways to do so described in greater detail below, refer to Option 2 the recommended approach due to its simplicity.

###### Option 1. Generate a RSA private key and public key with openssl commands

* Generate a RSA Private Key:
```
    openssl genrsa -out rsa_private_key.pem 1024    
```
* Convert the traditional format private key into PKCS#8 format (this Java sample application requires the private key to be encoded by PKCS#8)
```
openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt
```
* Generate the RSA Public Key:
```
openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
```
###### Option 2. Run the sample application (provided you meet the Application Requirements) and use the built-in UI to generate the key pair
* Run the application
* Once the application is running successfully, proceed to the built-in provided Frontend UI at `localhost:18080/swagger-ui.html`
* You should see a list of sample APIs using the Swagger 2 format
* Go to the Utility : Utils Controller and click on Expand Operations. 
* Click on the `Try it out!` button and your keypair will be generated in the Response Body shown in the diagram below.
   
![](src/main/resources/assets/option2_rsakeygen.png)

##### 2. Setup an available API endpoint to receive callbacks
The STACS blockchain DRS module (see [STACS Blockchain Introduction](#STACS-Blockchain-Introduction)) supports the asynchronous ability to send back transaction status and details once your transaction requests have been completed at the data consensus layer of the blockchain.
This provides the sample application with a robust, real-time update on transaction requests.

You will need to setup a HTTP API endpoint in order to receive callbacks in real-time. 

##### 3. Request for a merchant account with the node operator
If you are looking to work with the Settility Studios blockchain network, please send your request to the following email address. 

Details required by the node operators are as follows:
* Your RSA Public Key
* API Endpoint for receiving callbacks

Once processed, you should receive the following merchant information:
* DRS API URL
* DRS Public Key
* Merchant Id

In your application's `application.yml`, please update the `helloworld.drs` parameters:

`application.yml` parameter | merchant information
------------------------- | --------------------
|url | DRS API URL|
|publicKey | DRS Public Key|
|myPublicKey | Your RSA Public Key|
|myPrivateKey | Your RSA Private Key|
|myIdentifierId | Merchant Id|

##### 4. Good to Go!
Congratulations! 

You are now good to start working with the APIs within this sample application. 
Once the application is running, you can use the built-in Swagger UI on your browser, which defaults to [localhost:18080/swagger-ui.html](localhost:18080/swagger-ui.html).

![](src/main/resources/assets/sample_swaggerui.png)

## STACS Blockchain Introduction
A node operator will be running blockchain nodes and a Domain Runtime System (DRS) in order to process remote API requests.

![](src/main/resources/assets/drs_architecture.png)

The DRS has a multi-tenant architecture that manages API calls from business applications.
This includes authorization of 3rd party developers and securing API requests to the blockchain by enforcing security-in-transit with encrypted HTTP requests to the blockchain.
The DRS enables hosting of multiple nodes to support High Availability 
 
 ## STACS DRS HTTP API Workflow

The DRS enhances security before the blockchain layer by enforcing encryption in transit of remote API requests from incoming business applications.
These business applications will also need to utilize their registered merchant RSA private key to sign on all API requests which the DRS validates to ensure that incoming API requests originate from the authorized merchant accounts.

The following encryption and decryption methods are already provided in the [DrsClient.java](io.stacs.dapp.helloworld.httpclient.DrsClient) `post(String url, Object request)` method.

### API Request Encryption  
 
Steps:
1. Generate a AES-256 Key on the fly 
2. Encrypt the Message Request Body with the generated AES-256 key 
3. Encrypt the AES-256 key with the DRS public key 
4. Create a signature using your Merchant private key
5. Create the Message Request Header that contains:
  a. Merchant id
  b. encrypted AES-256 key
  c. signature
6. Construct the final API Message with both the header and encrypted body

Code Sample in `DrsClient.java`:
```
byte[] requestBytes = JSON.toJSONString(request).getBytes(DEFAULT_CHARSET);
//Generate AES Key 
String aesKey = AESUtil.generateKey256();
//Encrypt Request body with AES key
byte[] encryptedData = AESUtil.encryptBinary(requestBytes, aesKey);
//Encrypt AES key with DRS public key
String encryptAesKey = RsaEncryptUtil.base64Byte2string(RsaEncryptUtil.encryptByPublicKeyString(aesKey, CONFIG.getPublicKey()));
//Create request signature using Merchant private key
String signature = RsaSignUtil.sign(encryptedData, CONFIG.getMyPrivateKey());

Request.Builder requestBuilder = new Request.Builder();
//1.Request Header Setup
requestBuilder
        .addHeader("identifierId", CONFIG.getMyIdentifierId())
        .addHeader("aesKey", encryptAesKey)
        .addHeader("signature", signature);
//2. Add encrypted Request body
RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, encryptedData);
//3. Create HTTP Request
Request req = requestBuilder
        .url(url)
        .post(body)
        .build();
Response response = CLIENT.newCall(req).execute();
```
### API Response Decryption

1. extract header from the Response and get the 
  a. encrypted AES-256 key
  b. signature
2. Validate the signature with the DRS public key 
3. Decrypt the AES-256 key with your Merchant private key
4. Use the AES-256 key to decrypt the Response Body 

Code Sample in `DrsClient.java`:

```
//Extract header from Response
String respIdentifierId = response.header("identifierId");
String respSignatue = response.header("signature");
String respEncryptAesKey = response.header("aesKey");
ResponseBody responseBody = response.body();
byte[] respEncryptedBody = responseBody.bytes();
//Verify Response Signature with DRS public key

if (!RsaSignUtil.check(respEncryptedBody, respSignatue, CONFIG.getPublicKey())) {
    log.error("DRS Signature verification failed.");
    throw new RuntimeException("signature verification error");
}
//Decrypt AES key using Merchant private key
String decryptAesKey = RsaEncryptUtil.byte2string(RsaEncryptUtil.decryptByPrivateKeyString(respEncryptAesKey, CONFIG.getMyPrivateKey()));
//Decrypt Response Body with decrypted AES key
byte[] decryptBytes = AESUtil.decryptBinary(respEncryptedBody, decryptAesKey);
String jsonStringResp = new String(decryptBytes, DEFAULT_CHARSET);
return JSONObject.parseObject(jsonStringResp);
```

### Transaction Request Flow to and from the Blockchain 

![](src/main/resources/assets/API_overall_flow.png)

The overall architecture and sequence of information from your application to the STACS blockchain follows the following steps:
1. Merchant Account Application sends encrypted API Request to the DRS
2. Merchant Account Application receives a synchronous API Response from the DRS indicating if request was receives successfully
3. DRS decrypts and validates signature corresponding to the merchant account
![](src/main/resources/assets/API_Flow_Step1to3.png)

4. DRS sends the Transaction Request to the blockchain network nodes
5. STACS Blockchain reaches consensus on your Transaction Request which is now a Transaction in a Block on the blockchain
6. DRS receives the outcome of the Transaction Request from the STACS Blockchain
7. DRS encrypts the Transaction outcome as an API Response
![](src/main/resources/assets/API_Flow_Step4to7.png)

8. Merchant Account Application received the encrypted API Response from the callback endpoint
9. Merchant Account Application decrypts and validates signature corresponding to the DRS
10. Merchant Account Application retrieves the outcome of the initial API Request (as a Transaction in a Block on the STACS blockchain)
![](src/main/resources/assets/API_Flow_Step8to10.png)
 
 ## STACS DRS HTTP REST APIs
 
  In the file [DrsApiConstant.java](io.stacs.dapp.helloworld.constant.DrsApiConstant.java), you can see the 4 available API endpoints exposed by the DRS:
  1. Create a Wallet Address: `/smt/address/create`
  2. Invoke Smart Contract Functions (using SMT format): `/endpoint`
  3. Query Balance of assets in a Wallet Address: `/smt/contract/balanceof`
  4. Query Blockchain Transaction Results: `/smt/message/getByIdentifierIdAndUuid`
  
  ## Settility Message Type (SMT) - Smart Contract Function Format
  Settility Message Type (SMT) is an additional layer built to encapsulate smart contract functions and their required parameters to make it easy for business applications to invoke.
     
  Smart contracts uploaded to the blockchain are mapped to the SMT format where smart contract function parameters are standardized.  
  
  Do reach out to the node operator for more information on the SMT format and invoke functions of active smart contracts on the blockchain easily with the SMT format.
    