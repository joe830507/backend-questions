# backend-questions

### Hello World API
```
GET http://localhost:8080/hello_world
```
### 會員API
```
1. GET    http://localhost:8080/members/{id} 依照ID查詢單一會員
2. GET    http://localhost:8080/members?page={page}&size={size} 查詢會員，返回分頁。
3. POST   http://localhost:8080/members requestBody : {id:1,account:'John',password:'John123'} 會員註冊
4. DELETE http://localhost:8080/members/{id} 依照ID刪除一會員
5. PUT    http://localhost:8080/members requestBody : {id:1,password:'John123'} 更改某一會員的資料
```

### 訂單API
```
1. GET    http://localhost:8080/orders?id={id}&productName={productName}&purchasedDate={purchasedDate} 依照訂單ID或產品名稱或購買日期來查詢
2. GET    http://localhost:8080/orders/{n} 查詢統計訂單數大於n的會員資料
3. POST   http://localhost:8080/orders requestBody : {productId:1,accountId:1,purchasedCount:5} 會員可以訂購產品
```
