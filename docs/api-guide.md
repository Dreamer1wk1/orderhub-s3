# OrderHub API简要说明

| 方法 | 路径 | 用途 |
|---|---|---|
| POST | `/api/orders` | 创建订单 |
| GET | `/api/orders/{orderId}` | 查询订单，需传 `X-User-Id` |
| POST | `/api/orders/{orderId}/cancel` | 取消订单 |
| GET | `/api/orders` | 订单列表 |
| GET | `/api/orders/by-date?start=2026-01-01&end=2026-01-31` | 日期范围查询 |
| GET | `/api/orders/page?page=0&size=20` | 分页查询 |
| GET | `/api/products/search?keyword=book&tags=BOOK` | 商品搜索 |
| POST | `/api/inventory/{productId}/deduct?quantity=1` | 扣减库存 |
| POST | `/api/order-events` | 消费订单事件 |
| GET | `/api/reports/orders?page=0&size=20` | 订单报表 |
| GET | `/api/reports/generated-at` | 报表生成时间 |

创建订单示例：`{"userId":1,"productId":1,"quantity":1,"originalAmount":120.00,"discountRate":0.10}`。折扣比例使用0到1之间的小数表示。