docker images -a | grep "cdc-kafka-connect" | awk '{print $3}' | xargs docker rmi -f
docker build -t cdc-kafka-connect .