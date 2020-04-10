<h1 align="center"><a href="https://github.com/xkcoding/simple-http" target="_blank">Simple-HTTP</a></h1>
<p align="center">
<a href="https://travis-ci.com/xkcoding/simple-http" target="_blank"><img alt="Travis-CI" src="https://travis-ci.com/xkcoding/simple-http.svg?branch=master"/></a>
  <a href="https://search.maven.org/artifact/com.xkcoding.http/simple-http" target="_blank"><img alt="MAVEN" src="https://img.shields.io/maven-central/v/com.xkcoding.http/simple-http.svg?color=brightgreen&label=Maven%20Central"></a>
  <a href="https://xkcoding.com" target="_blank"><img alt="author" src="https://img.shields.io/badge/author-Yangkai.Shen-blue.svg"/></a>
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html" target="_blank"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8.0_162-orange.svg"/></a>
  <a href="https://github.com/xkcoding/simple-http/blob/master/LICENSE" target="_blank"><img alt="LICENSE" src="https://img.shields.io/github/license/xkcoding/simple-http.svg"/></a>
</p>

## 简介

> 抽取一个简单 HTTP 的通用接口，底层实现根据具体引入依赖指定。

```xml
<dependency>
  <groupId>com.xkcoding.http</groupId>
  <artifactId>simple-http</artifactId>
  <version>1.0.1</version>
</dependency>
```

## 特点

- 默认会按照下面的优先级自行寻找底层实现，`java 11 HttpClient -> OkHttp3 -> apache HttpClient -> hutool-http`
- 也可以自行实现 `com.xkcoding.http.support.Http` 接口，通过 `HttpUtil.setHttp(new MyHttpImpl())` 设置进来

## TODO

- [x] ~~集成 JDK11 的 HTTPClient~~(感谢[@春哥](https://github.com/ChunMengLu)的 [PR#1](https://github.com/xkcoding/simple-http/pull/1))
