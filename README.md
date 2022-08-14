# vue-element-admin-java-api
vue element admin java api is for [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin) 

spring boot + Spring Security + Token + Swagger Document

## 1.1 Getting Started

### 1.1.1 How to use vue-element-admin-java-api?


- clone the project

```
git clone https://github.com/geekxingyun/vue-element-admin-java-api.git
```

- enter the project directory

```
cd vue-element-admin-java-api
```
- install dependency

```
mvn package
```

-  Running the Application

```
java -jar vue-element-admin-java-api-0.0.1-SNAPSHOT.jar --server.port=8080 --server.servlet.context-path=/dev-api
```

Tips:If you want to run it in the background

Windows：

```
javaw -jar vue-element-admin-java-api-0.0.1-SNAPSHOT.jar --server.port=8080 --server.servlet.context-path=/dev-api
```
Linux or Mac OSX:

```
nohup java -jar vue-element-admin-java-api-0.0.1-SNAPSHOT.jar --server.port=8080 --server.servlet.context-path=/dev-api
```

- Visit the App

http://127.0.0.1:8080/dev-api

### 1.1.2 How to use with vue-element-admin?

- clone the project

```
git clone https://github.com/PanJiaChen/vue-element-admin.git
```

- enter the project directory

```
cd vue-element-admin
```

- install proxy repository for china

```
npm install --registry=https://registry.npm.taobao.org
```

- install dependency

```
npm install
```

- develop

```
npm run dev
```

- change vue-config.js file as below:

```
  devServer: {
    port: port,
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      // change xxx-api/login => mock/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      [process.env.VUE_APP_BASE_API]: {
        target: `http://127.0.0.1:8080/dev-api`,
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      }
    }
    //before: require('./mock/mock-server.js')
  },
```

then run command as below:

```
npm run dev
```

open the website as below:

[http://127.0.0.1:9527](http://127.0.0.1:9527)

## 1.2 QA

### 1.2.1 How to Login Swagger API?

Spring Security Login
```
username:admin
password:666666
```

Tips:

if you want to change the default password,you can run the code as below:

```
String password=new BCryptPasswordEncoder().encode("666666");
```
### 1.2.2 How to change DB?

default is use H2 Database base on Memory,if you want to change to MySQL Database

you just need to change the application.yml as below:
```
spring:
  profiles:
    active: test
```
Tips: it will active application-test.yml configuration

### 1.2.3 save data forever

default it will always destroy data and create again when you run the application.

if you want to save the data forever,

please switch to prod ,
```
spring:
  profiles:
    active: test
```
or 

change the properties value as below:
```
spring:
  jpa:
      hibernate:
        ddl-auto: update
```

