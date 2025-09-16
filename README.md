# 基隆景點 App

一個簡單的景點查詢系統，後端用 Spring Boot + MongoDB，前端用 React。

## 本地開發

**後端啟動：**

```bash
./gradlew bootRun
```

後端會跑在 <http://localhost:9487>

**前端啟動：**

```bash
cd frontend
npm install
npm run dev
```

前端會跑在 <http://localhost:5173>

**API 測試：**

```
GET http://localhost:9487/api/sights
```

**常見問題：**

如果後端啟動失敗顯示 "Port 9487 is already in use"：

```bash
lsof -i :9487        # 查看佔用程序
kill [PID]           # 殺掉該程序
```

如果出現 "Unsupported class file major version 67" 錯誤：

- 這是Java版本不相容問題
- Spring Boot 2.7.18 不支援 Java 23
- 請使用 Java 21 或更低版本：

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
```

## Docker 部署

```bash
docker build -t keelung-app .
docker run -p 9487:9487 keelung-app
```

**註：** MongoDB 已經設定好連到 Atlas，不用另外裝資料庫。



測試雲端部署純後端  
[https://keelung-sights-app-production.up.railway.app/api/sights?zone=中正](https://keelung-sights-app-production.up.railway.app/api/sights?zone=中正)

測試雲端部署  
[https://keelung-sights-app-production.up.railway.app/](https://keelung-sights-app-production.up.railway.app/)
