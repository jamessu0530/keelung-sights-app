# 超簡單的 Docker 配置 - 前後端一起打包
FROM openjdk:11-jdk-slim

# 安裝 Node.js 和 unzip
RUN apt-get update && \
    apt-get install -y curl unzip && \
    curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs && \
    rm -rf /var/lib/apt/lists/*

# 安裝 Gradle
RUN curl -L https://services.gradle.org/distributions/gradle-7.6-bin.zip -o gradle.zip && \
    unzip gradle.zip && \
    mv gradle-7.6 /opt/gradle && \
    rm gradle.zip
ENV PATH=/opt/gradle/bin:$PATH

WORKDIR /app

# 複製所有文件
COPY . .

# 建構前端
WORKDIR /app/frontend
RUN npm install
RUN npm run build

# 把前端建構結果移到 Spring Boot 的靜態資源目錄
RUN mkdir -p /app/src/main/resources/static
RUN cp -r /app/frontend/dist/* /app/src/main/resources/static/

# 回到主目錄建構後端
WORKDIR /app
RUN gradle build -x test

# 執行應用程式
EXPOSE 9487
CMD ["sh", "-c", "java -jar build/libs/*.jar"]
