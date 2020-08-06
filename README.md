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
