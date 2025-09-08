# 建構階段
FROM gradle:8.13-jdk11 AS build
WORKDIR /app

# 複製 Gradle 配置檔案（利用快取）
COPY build.gradle .
COPY gradle/ gradle/

# 下載依賴（這層會被快取）
RUN gradle dependencies --no-daemon

# 複製源碼並建構
COPY src/ src/
RUN gradle build -x test --no-daemon

# 運行階段  
FROM openjdk:11-jre-slim
WORKDIR /app

# 只複製建構好的 JAR 檔案
COPY --from=build /app/build/libs/app.jar app.jar

# 執行應用程式
EXPOSE 9487
ENTRYPOINT ["java", "-jar", "app.jar"]

