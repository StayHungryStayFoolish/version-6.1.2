# Monolithic App

## Configuration

- MySQL 5.7+
- Redis 5.0.0
- Java 8+

# API 

## Account

1. 创建账户

        POST  http://localhost:8080/api/register

        请求：
       
        {
            "email": "bonismo@hotmail.com",
            "langKey": "zh-CN",
            ["phone": "+86-138111100000",]
            ["activated": "true",]
            "password": "YWRtaW4="
        }
       
        返回：  
        Response Body: no content
        Response Code: 201
        
        异常：
            1.400 手机已存在
            2.400 邮箱已存在
            3.400 密码长度不符合
        
        注：
            1.如果邮件注册，需要使用激活账户 API。
            2.如果手机注册，需要使用发送短信 API。并将 activated 设为 true。
            3.email 格式：a-b_.+@x-_x.com-_
            4.phone 格式：+86-138111100000
            5.langKey 格式：zh-CN/en-US/ko-KR/ja-JP
            6.password 格式：4-100 位
            
            
    
2. 激活账户

        GET http://localhost:8080/api/activate?key={key}
          
        请求：
        key : String
        
        返回：        
        Response Body: no content
        Response Code: 200
        
        异常：500 输入的 key 不存在。

3. 重置密码

        POST    http://localhost:8080/api/account/reset-password/init
        
        请求：
        ****@gmail.com
        
        返回：
        Response Body: no content
        Response Code: 200
        异常：400 邮箱不存在
        
4. 根据 Key 设置密码

       POST http://localhost:8080/api/account/reset-password/finish
       
       请求：
       
       {
           "key": "45293142206545618602",
           "newPassword": "YWRtaW4="
       }
       
       返回：
       Response Body: no content
       Response Code: 200
       异常：
            1. 400 密码不符合规则
            2. 500 key 不正确，导致用户设置密码失败
       
5. 修改密码

        POST    http://localhost:8080/api/account/change-password
        
        请求：
        
        {
            "currentPassword": "*****",
            "newPassword": "*****"
        }
        
       返回：
       Response Body: no content
       Response Code: 200
       
       异常：
            1. 400 密码不正确
        
       
6. 更新用户资料

        POST    http://localhost:8080/api/account
        
        请求：
        
        {
            "email": "monolithic@gmail.com",
            "firstName": "First",
            "imageUrl": "image",
            "langKey": "en-US",
            "lastName": "Last",
            "phone": "+86-13811110000"
        }        
        
        返回：
        Response Body: no content
        Response Code: 200
        
        异常：
            1.400 手机已存在
            2.400 邮箱已存在
        
        
6. 查看当前用户是否登录

        GET      http://localhost:8080/api/authenticate
        
        返回：
        1. 用户未登录
            Response Body: no content
            Response Code: 200
        2. 用户已登录
            Response Body: app_id_100003
            Response Code: 200
            
7. 登录回调

        GET     http://localhost:8080/api/account-callback
        
        返回：
        
        {
            "id": 100003,
            "email": "admin@localhost.com",
            "phone": "+86-13811110000",
            "appId": "app_id_100003",
            "firstName": "First",
            "lastName": "Last",
            "imageUrl": "image",
            "activated": true,
            "langKey": "zh-CN",
            "createdBy": "system",
            "createdDate": null,
            "lastModifiedBy": "app_id_100003",
            "lastModifiedDate": "2019-07-04T09:40:26Z",
            "authorities": [
                "ROLE_USER",
                "ROLE_ADMIN"
            ]
        }
        
        注：
            1.记录用户 IP 和登录设备。
            2.如果有邮箱，发送登录通知邮件。
            
8. 查看用户信息

                               
        GET     http://localhost:8080/api/account-callback
        
        返回：
        
        {
            "id": 100003,
            "email": "admin@localhost.com",
            "phone": "+86-13811110000",
            "appId": "app_id_100003",
            "firstName": "First",
            "lastName": "Last",
            "imageUrl": "image",
            "activated": true,
            "langKey": "zh-CN",
            "createdBy": "system",
            "createdDate": null,
            "lastModifiedBy": "app_id_100003",
            "lastModifiedDate": "2019-07-04T09:40:26Z",
            "authorities": [
                "ROLE_USER",
                "ROLE_ADMIN"
            ]
        }   
        
## SMS

1. 发送短信

| type | desc | api |
| :--- | :----: | ----: |    
|registryCN       |    注册验证    |   a |         
|welcomeCN        |    注册成功    |   b |
|loginVerifyCN    |    登录校验    |   a |
|loginNoticeCN    |    登录通知    |   b |
|bindPhoneCN      |    绑定手机    |   a |
|changeOldPhoneCN |    解绑原手机  |   a |      
|changeNewPhoneCN |    绑定新手机  |   a | 

        
        注：支持4中语言，后缀分别为 CN/EN/KR/JP    
        
        a.  GET     http://localhost:8080/security/sms/send/{type}/{phone}
        b.  GET     http://localhost:8080/security/sms/send/{type}/{phone}/{text}
        
        请求：
        type : String
        phone : String
        text : String
        
        返回：
        
        {
            "dayLimit": 10,
            "hourLimit": 5,
            "minuteLimit": 3,
            "smsResult": "smsSuccess"
            [ "expire": 3000 ]
        }
        
        返回结果重要参数说明：
        
            dayLimit : Long         24小时内同一模板短信发送限制
            hourLimit : Long        小时内同一模板发送限制
            minuteLimit : Long      分钟内同一模板发送限制
            smsResult : String      返回结果
                smsSuccess          发送成功
                smsFailed           欠费、网络情况等
                smsTry              重新发送（国外服务商网络问题）
                smsMinuteLimit      分钟级别限制
                smsHourLimit        小时级别限制
                smsDayLimit         天级别限制
            expire                  如果是 Limit 限制级别，则该字段存在，有值且单位为秒
            
        异常情况： 400 手机号码不符合规则、短信模板不存在
            
2. 短信校验

    
        GET    http://localhost:7777/security/sms/verify/{type}/{phone}/{authCode}
        
        请求：
        type : String
        phone : String
        authCode : String 
        
        返回：
        checkSuccess            验证成功
        checkFailed             验证失败
        checkNotExists          验证超时
        
        异常情况：400 手机号码不符合规则
                
    
注：Twilio 服务暂时不可用，Twilio JAR 自身引用了 jjwt:04 版本，与该系统的 jjwt:0.10.6 冲突。
    
    
### 阿里云短信模板

- 创建成功

    尊敬的 ${name} 用户，您的账户已创建，如有疑问请随时和我们联系。祝您使用愉快。

- 登录通知

    尊敬的 ${name} 用户：您于 ${time} 已经成功登陆，如若不是本人，请您尽快修改密码，保证账户安全。    

- 验证通知 

    您当前正在重置登陆密码，验证码：${code}。5分钟内有效，请勿告诉他人。                 
        
### 前端加密

- 默认使用 btoa();     

### 项目环境配置

- 以 dev 为准。                                 
                                     
