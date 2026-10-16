curl -v http://127.0.0.1:8080/
curl -v http://127.0.0.1:8080/items
curl -v -X POST http://127.0.0.1:8080/items \
  -H 'Content-Type: application/json' \
  -d '{"name":"Чай","price":90,"category":"drink"}'
curl -v http://127.0.0.1:8080/items/1
curl -v 'http://127.0.0.1:8080/items?category=drink'
curl -v -X PUT http://127.0.0.1:8080/items/1 \
  -H 'Content-Type: application/json' \
  -d '{"name":"Чай","price":80,"category":"drink"}'
curl -v -X DELETE http://127.0.0.1:8080/items/1
