CONTAINER_NAME="criador-topicos"

remove_container() {
  echo "Removendo conteiner $CONTAINER_NAME..."
  docker rm $CONTAINER_NAME
}

criar_conectores(){
  echo "Instalando conectores no kafka connect..."
  cd cdc-kafka-connect
  ./novos-voos-conector.sh
  cd ..
}

echo "Subindo a infra da aplicação..."
docker-compose up -d

while true; do
  if ! docker ps -f "name=$CONTAINER_NAME" --format "{{.Names}}" | grep -q "$CONTAINER_NAME"; then
    remove_container
    sleep 40
    criar_conectores
    break
  fi
  sleep 1
done

