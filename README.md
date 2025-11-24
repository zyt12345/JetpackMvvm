[![Platform][1]][2] [![GitHub license][3]][4]  [![GitHub license][5]][6] 

[1]:https://img.shields.io/badge/platform-Android-blue.svg  
[2]:https://github.com/hegaojian/JetpackMvvm
[3]:https://img.shields.io/github/release/hegaojian/JetpackMvvm.svg
[4]:https://github.com/hegaojian/JetpackMvvm/releases/latest
[5]:https://img.shields.io/badge/license-Apache%202-blue.svg
[6]:https://github.com/hegaojian/JetpackMvvm/blob/master/LICENSE

# 🏗 JetpackMvvm
**JetpackMvvm** 是一个基于 Jetpack 架构组件构建的 Android MVVM 快速开发框架，旨在帮助开发者快速搭建高质量、可维护、可扩展的应用。
---
## ✨ 框架特性

- 🧠 **标准化 MVVM 架构设计**  
  基于 ViewModel、LiveData、Kotlin Flow、Repository 的分层体系，提供清晰、可维护的应用结构。

- ⚡ **协程驱动的响应式数据流**  
  全面采用 Kotlin Coroutines 进行异步管理，天然支持挂起函数与结构化并发。

- 🔄 **灵活的加载与状态管理机制**  
  内置统一的页面状态切换（加载中 / 空 / 错误 / 成功），支持全局配置与自定义样式。

- 🧩 **高度模块化的基类封装**  
  提供 BaseActivity、BaseFragment、BaseViewModel 等基础实现，快速构建页面逻辑。

- 🪶 **无侵入式视图绑定支持**  
  兼容 ViewBinding 与 DataBinding，减少模板代码，让开发更轻量高效。

- 🌐 **网络层可插拔设计**  
  简洁而灵活的封装，可与 Retrofit、OkHttp 或任意网络框架无缝配合使用。

- 💎 **丰富的便捷工具集**  
  内置常用封装：本地自动缓存、Glide 图片加载、Gson 数据解析、日志打印等，开箱即用。
  
- 🌈 **完整示例工程，助你快速上手**   
  基于**玩Android API** 开发了一个`玩安卓示例APP`,注释非常详细，App展示框架在真实项目中的使用方式与开发流程。
---

## 📦 玩Android示例App下载体验
| 示例图 | 扫码下载 | Github下载 | 第三方下载 |
|------|------|------|------|
|<img width="180" height="391" alt="示例app示例图" src="https://github.com/user-attachments/assets/e1670f20-6158-4eae-9a6c-09b34b7124a9"/>|<img src="https://github.com/user-attachments/assets/288a73f1-2e10-404a-8fc5-2acb9b1799ed" width="150" height="150" alt="JetpackMvvm Demo 二维码"/>|[🌍 GitHub 下载](https://github.com/hegaojian/JetpackMvvm/releases/download/2.0.0/app-release.apk)|[🚀 第三方下载](https://www.pgyer.com/jjbeautiful)|

## 🚀 版本依赖
```gradle
dependencies {
    implementation 'com.github.hegaojian:JetpackMvvm:2.0.1'
}
```
``` 
JetpackMvvm.init(application)
```
## 📖 目录导航

| 模块 | 说明 |
|------|------|
| [快速开始Wiki必看](https://github.com/hegaojian/JetpackMvvm/wiki/1.%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B) | 一步步构建第一个 基于JetpackMvvm的应用 |
| [基类介绍](https://github.com/hegaojian/JetpackMvvm/wiki/2.%E5%9F%BA%E7%B1%BB%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D) | 基类功能介绍 |
| [基于LiveData请求](https://github.com/hegaojian/JetpackMvvm/wiki/3.-%E5%9F%BA%E4%BA%8ELiveData%E7%9A%84%E8%AF%B7%E6%B1%82%E5%B0%81%E8%A3%85) | 基于LiveData的网络请求封装 |
| [基于Flow请求](https://github.com/hegaojian/JetpackMvvm/wiki/4.%E5%9F%BA%E4%BA%8EFlow%E7%9A%84%E8%AF%B7%E6%B1%82%E5%B0%81%E8%A3%85) | 基于 Flow 的网络请求封装 |
| [LiveData vs Flow](https://github.com/hegaojian/JetpackMvvm/wiki/5.Livedata%E4%B8%8EFlow%E4%BD%BF%E7%94%A8%E5%AF%B9%E6%AF%94) | LiveData vs Flow 选择指南 |
| [常用功能封装](https://github.com/hegaojian/JetpackMvvm/wiki/6.%E5%B8%B8%E7%94%A8%E5%8A%9F%E8%83%BD%E5%B0%81%E8%A3%85) | 常用功能封装 |
| [常见问题](https://github.com/hegaojian/JetpackMvvm/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98) | 常见使用问题与解决方案 |
| [网络框架加入](https://github.com/hegaojian/JetpackMvvm/wiki/%E7%BD%91%E7%BB%9C%E6%A1%86%E6%9E%B6%E6%8E%A5%E5%85%A5%E6%8C%87%E5%8D%97) | 网络框架加入指南 |
| [架构设计](https://github.com/hegaojian/JetpackMvvm/wiki/%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1) | 框架架构图与核心思路 |

---

## 💬 联系与支持

- 💡 欢迎通过 [Issues](https://github.com/hegaojian/JetpackMvvm/issues) 提交问题或建议
- ❤️ 如果这个框架帮助到你，请帮忙点一个 ⭐ 支持一下  
- 🌍 承接**App外包项目**，有需求可以联系我。帮人介绍的话可以给你**返点**😆

| QQ交流群 | 微信交流群 | 个人微信 | 个人QQ |
|------|------|------|------|
| <img width="150" height="150" src="https://github.com/user-attachments/assets/faf8684d-790a-4b3d-8d21-6221a350d639" alt="QQ群二维码"/> | <img width="150" height="150" alt="微信交流群" src="https://github.com/user-attachments/assets/de48d23e-a118-4e5d-a59a-7ce042cded3f" />  | <img width="150" height="150" src="https://github.com/user-attachments/assets/ef751c37-373e-45c5-b37c-f1d523d3aa67" alt="微信二维码"/>  | <img width="150" height="150" src="https://github.com/user-attachments/assets/faf8684d-790a-4b3d-8d21-6221a350d639" alt="QQ群二维码"/>  |

---

## License
``` license
 Copyright 2019, hegaojian(何高建)       
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

