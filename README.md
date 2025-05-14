# Worst Movie Category API 🏆🎬
#### _From Golden Raspberry Awards_ 

## Pré-requisitos
1. Java 21;
2. Maven 3.6.3 ou superior.

## Instalação e execução

Clonar o repositório:

```
git clone https://github.com/dud4rech/movie-awards-api
cd movie-awards-api
```

Instalar as dependências:
```
mvn clean install
```
Compilar:
```
mvn clean compile
```

Executar:
```
mvn exec:java -Dexec.mainClass="com.razzies.Main"
```

### Opcional: testar a API no Swagger UI

Depois de rodar a aplicação é só acessar a url http://localhost:8080/swagger-ui/index.html :)

### Opcional: consultar a base de dados H2
Você pode conferir a base de dados acessando http://localhost:8080/h2-console e informando os dados abaixo:

```
JDBC URL: jdbc:h2:mem:moviesdb
User name: razzies
Password: [vazio]
```

## Testes de integração

Executar:
```
mvn test -Dtest=MovieControllerTest
```

