# 🚀 基隆景點 App - 超簡單 Docker 部署

## 📦 一鍵建構和執行

```bash
# 建構 Docker 映像
docker build -t keelung-app .

# 執行容器
docker run -p 9487:9487 keelung-app
```

## 🌐 訪問應用

- **完整應用**: http://localhost:9487
- **API**: http://localhost:9487/api/sights?zone=中正

## 📁 檔案說明

- `Dockerfile` - Docker 配置文件
- `.dockerignore` - 忽略不必要的文件

## ✅ 特點

- ✅ 前後端一起打包
- ✅ 只需要一個容器
- ✅ 只需要一個端口 (9487)
- ✅ 連接到你的 MongoDB Atlas
- ✅ 超級簡單！

## 🔧 故障排除

如果遇到問題：
```bash
# 查看容器日志
docker logs container_name

# 進入容器調試
docker exec -it container_name /bin/bash
```

**就這麼簡單！** 🎉