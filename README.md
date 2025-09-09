# 基隆景點 App

一個簡單的景點查詢系統，後端用 Spring Boot + MongoDB，前端用 React。

## 本地開發

**後端啟動：**
```bash
./gradlew bootRun
```
後端會跑在 http://localhost:9487

**前端啟動：**
```bash
cd frontend
npm install
npm run dev
```
前端會跑在 http://localhost:5173

**API 測試：**
```
GET http://localhost:9487/api/sights?zone=中正
```

**常見問題：**
如果後端啟動失敗顯示 "Port 9487 is already in use"：
```bash
lsof -i :9487        # 查看佔用程序
kill [PID]           # 殺掉該程序
```

## Docker 部署

```bash
docker build -t keelung-app .
docker run -p 9487:9487 keelung-app
```

**註：** MongoDB 已經設定好連到 Atlas，不用另外裝資料庫。