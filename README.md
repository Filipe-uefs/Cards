Desafio Técnico Backend PL - Java

# Projeto de jogo de cartas para desafio técnico de SpringBoot

## Requirementos

Para buildar e rodar esse projeto será necessário:

- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Banco de dados Postgres](https://maven.apache.org)

As configurações para conexão com database pelo JPA, podem ser encontradas no arquivo de properties do projeto.

## Rodando a aplicação localmente

Na raiz do projeto rode

```shell
mvn clean install
mvn spring-boot:run
```

## Documentação:

Caminho Feliz: Criar uma nova partida -> Gerar todos os players de uma partida -> Gerar todos as cartas para os players -> Chamar endpoint que gera e retorna os ganhadores.



Para criar uma nova partida

```shell
curl --location --request POST 'http://localhost:8080/v1/game'
```

Para Recuperar uma partidade criada

```shell
curl --location 'http://localhost:8080/v1/game/{gameId}'
```

Para Gerar ganhandores de uma partida

```shell
curl --location 'http://localhost:8080/v1/game/winner/{gameId}'
```

Para criar um jogador em uma partida

```shell
curl --location 'http://localhost:8080/v1/player' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Filipe",
    "gameId": "{gameId}"
}'
```

Para criar um Deck para um jogador de acordo com o ID
```shell
curl --location 'http://localhost:8080/v1/player/{playerId}/createCards' \
--header 'Content-Type: application/json' \
--data '{
    
}'
```

Para criar todos os players de uma partida
```shell
curl --location 'http://localhost:8080/v1/player/{gameId}/createGenericAllPlayers' \
--header 'Content-Type: application/json' \
--data '{
    
}'
```

Para criar deck para todos os jogadores de uma partida
```shell
curl --location 'http://localhost:8080/v1/player/{gameId}/createCardsForAllPlayers' \
--header 'Content-Type: application/json' \
--data '{
    
}'
```