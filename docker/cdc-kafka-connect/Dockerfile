FROM confluentinc/cp-kafka-connect-base:7.4.1

ENV KAFKA_CONNECT_PLUGINS_DIR=/etc/confluent/connect
RUN mkdir -p $KAFKA_CONNECT_PLUGINS_DIR

RUN echo ">>>>>>>>>> Instalando Plugin do mongodb <<<<<<<<<<<"
RUN wget  https://repo1.maven.org/maven2/io/debezium/debezium-connector-mongodb/2.4.0.Final/debezium-connector-mongodb-2.4.0.Final-plugin.tar.gz
RUN tar xvfz debezium-connector-mongodb-2.4.0.Final-plugin.tar.gz --directory $KAFKA_CONNECT_PLUGINS_DIR

RUN echo ">>>>>>>>>> Instalando Plugin do mysql <<<<<<<<<<"
RUN wget  https://repo1.maven.org/maven2/io/debezium/debezium-connector-mysql/2.4.0.Final/debezium-connector-mysql-2.4.0.Final-plugin.tar.gz
RUN tar xvfz debezium-connector-mysql-2.4.0.Final-plugin.tar.gz --directory $KAFKA_CONNECT_PLUGINS_DIR

RUN echo ">>>>>>>>>> Instalando Plugin do postgres <<<<<<<<<<"
RUN wget  https://repo1.maven.org/maven2/io/debezium/debezium-connector-postgres/2.4.0.Final/debezium-connector-postgres-2.4.0.Final-plugin.tar.gz
RUN tar xvfz debezium-connector-postgres-2.4.0.Final-plugin.tar.gz --directory $KAFKA_CONNECT_PLUGINS_DIR

RUN echo ">>>>>>>>>> Instalando Bibliotecas de SMT <<<<<<<<<<"
RUN wget https://repo1.maven.org/maven2/io/debezium/debezium-scripting/2.4.0.Final/debezium-scripting-2.4.0.Final.tar.gz
RUN tar xvfz debezium-scripting-2.4.0.Final.tar.gz --directory $KAFKA_CONNECT_PLUGINS_DIR

#RUN echo ">>>>>>>>>> Instalando uma implementação da JSR-223 <<<<<<<<<<"
#RUN wget https://repo1.maven.org/maven2/org/apache/groovy/groovy-jsr223/4.0.0/groovy-jsr223-4.0.0.jar
#RUN cp groovy-jsr223-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-scripting
#RUN cp groovy-jsr223-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-connector-mysql
#RUN mv groovy-jsr223-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR

#RUN wget https://repo1.maven.org/maven2/org/apache/groovy/groovy/4.0.0/groovy-4.0.0.jar
#RUN cp groovy-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-scripting
#RUN cp groovy-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-connector-mysql
#RUN mv groovy-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR

#RUN wget https://repo1.maven.org/maven2/org/apache/groovy/groovy-json/4.0.0/groovy-json-4.0.0.jar
#RUN cp groovy-json-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-scripting
#RUN cp groovy-json-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-connector-mysql
#RUN mv groovy-json-4.0.0.jar $KAFKA_CONNECT_PLUGINS_DIR

#RUN echo ">>>>>>>>>> Instalando bibliotecas restantes do Debezium <<<<<<<<<<"
#RUN wget https://repo1.maven.org/maven2/io/debezium/debezium-core/2.4.0.Final/debezium-core-2.4.0.Final.jar
#RUN mv debezium-core-2.4.0.Final.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-scripting

#RUN wget https://repo1.maven.org/maven2/io/debezium/debezium-ddl-parser/2.4.0.Final/debezium-ddl-parser-2.4.0.Final.jar
#RUN mv debezium-ddl-parser-2.4.0.Final.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-scripting

#RUN wget https://repo1.maven.org/maven2/org/antlr/antlr4-runtime/4.10.1/antlr4-runtime-4.10.1.jar
#RUN mv antlr4-runtime-4.10.1.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-scripting

#RUN wget https://repo1.maven.org/maven2/io/debezium/debezium-api/2.4.0.Final/debezium-api-2.4.0.Final.jar
#RUN mv debezium-api-2.4.0.Final.jar $KAFKA_CONNECT_PLUGINS_DIR/debezium-scripting

RUN echo ">>>>>>>>>> Removendo arquivos de instalação dos conectores <<<<<<<<<"
RUN rm *.gz

# RUN /etc/confluent/docker/run &