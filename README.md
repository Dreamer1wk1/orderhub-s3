# OrderHub — S3统一代码检视测评项目

OrderHub 是一个基于 Java 21 + Spring Boot 3 的轻量级订单协同管理示例项目，用于 S3「已知 MR → AI代码检视 → 意见定位 → 自主修复 → 提交MR → 回填修复结论」测评。

- 干净基线：`main`（标签 `s3-baseline-v1.0`）
- 五个独立测评分支：`mr/MR01` 至 `mr/MR05`
- 技术栈：Java 21、Spring Boot 3.3.5、Spring Data JPA、MySQL 8.0、JUnit 5

## 本地运行

```bash
docker compose up -d mysql
mvn spring-boot:run
```

应用地址：`http://localhost:8080`。基础单元测试：`mvn test`。

## 示例接口

```bash
curl "http://localhost:8080/api/products/search?keyword=Spring"
curl -X POST "http://localhost:8080/api/orders" -H "Content-Type: application/json" -d '{"userId":1,"productId":1,"quantity":1,"originalAmount":120.00,"discountRate":0.10}'
curl "http://localhost:8080/api/orders/1" -H "X-User-Id: 1"
curl "http://localhost:8080/api/orders"
curl "http://localhost:8080/api/reports/orders?page=0&size=20"
```

## 测评说明

五个MR分支均直接从同一 `main` 创建，互相独立。每个平台应使用相同的分支快照和MR描述；每次测评前恢复源分支到原始提交。标准答案及隐藏验收用例由测评组织者单独保管，不得提交到被测代码仓或提供给被测Agent。

本项目仅用于受控测评环境，不应直接部署到生产环境。