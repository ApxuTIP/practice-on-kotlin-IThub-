docker run --name ithub-pg -d \
  -e POSTGRES_PASSWORD=pass \
  -e POSTGRES_DB=shop \
  -p 5432:5432 \
  postgres:16

# потом: docker stop ithub-pg   /   docker start ithub-pg
# не делай docker rm — сотрёшь данные
