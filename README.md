# vue-element-admin-java-api
vue element admin java api is for [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin) 

## 1.1 Getting Started

## 1.2 Building the Application

```
mvn package
```

## 1.3 Running the Application

```
java -jar vue-element-admin-java-api-0.0.1-SNAPSHOT.jar --server.port=8080
```
## 1.4 Visit the App

http://127.0.0.1:8080/dev-api


## 1.5 QA

### 1.5.1 How to Login Swagger API?

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
### 1.5.2 How to use with vue-element-admin?

change vue-config.js file as below:
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
