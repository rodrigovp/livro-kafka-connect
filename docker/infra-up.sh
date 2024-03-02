CONTAINER_NAME="criador-topicos"

remove_container() {
  echo "Removendo conteiner $CONTAINER_NAME..."
  docker rm $CONTAINER_NAME
}

criar_atualizar_conectores(){
  echo "Instalando conectores no kafka connect..."

  cd cdc-kafka-connect

  echo "\nInstalando conector da base de voos do ms-companhias-aereas..."
  ./novos-voos-conector.sh
  echo "\nAtualizando conector da base de voos do ms-companhias-aereas..."
  ./novos-voos-conector-atualizar.sh

  echo "\nInstalando conector da base de voos selecionados do ms-pesquisa..."
  ./voo-selecionado-conector.sh
  echo "\nAtualizando conector da base de voos selecionados do ms-pesquisa..."
  ./voo-selecionado-conector-atualizar.sh

  echo "\nInstalando conector da base de hospedagens selecionadas do ms-selecao-hospedagens..."
  ./hospedagem-selecionada-conector.sh  
  echo "\nAtualizando conector da base de hospedagens selecionados do ms-selecao-hospedagens..."
  ./hospedagem-selecionada-conector-atualizar.sh
  
  echo "\nInstalando conector da base de hospedagens do ms-selecao-hospedagens..."
  ./hospedagem-conector.sh
  echo "\nAtualizando conector da base de hospedagens do ms-selecao-hospedagens..."
  ./hospedagem-conector-atualizar.sh

  cd ..
}

echo "Subindo a infra da aplicação..."
docker-compose up -d

while true; do
  if ! docker ps -f "name=$CONTAINER_NAME" --format "{{.Names}}" | grep -q "$CONTAINER_NAME"; then
    remove_container
    sleep 20
    criar_atualizar_conectores
    break
  fi
  sleep 1
done

