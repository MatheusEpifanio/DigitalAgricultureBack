# DigitalAgricultureBack

# API - Sistema de Gerenciamento de talhões

## Funcionalidades

- **Cadastro e Autenticação de Usuários**:
  - Registro e login de usuários com JWT.
- **Cadastro de Talhões**:
  - Cadastro de talhões.
- **Registro de Atividades**:
  - Registrar atividades dos talhões.

## Pré-requisitos
- [Java](https://git-scm.com/) (v17, configurar o jdk em variáveis de ambiente)
- [Git](https://git-scm.com/) (opcional, para clonar o repositório)
- [Docker](https://www.docker.com)

## Como Executar o Projeto

### 1. Clone o Repositório ou baixe o zip

```Terminal
git clone https://github.com/MatheusEpifanio/DigitalAgricultureBack.git
```

### 2. Entre na pasta raiz do projeto pelo terminal
cd DigitalAgriculture

### 2.1. execute os seguintes comandos
```irá gerar o jar
./mvnw clean package -DskipTests
```

```irá subir um container com aplicação
docker compose up -d --build
```

```caso necessário rode para derrubnar o container no docker
docker compose down
```
A api estará rodando em `http://localhost:8080`.

- [Collection da api](https://github.com/MatheusEpifanio/DigitalAgricultureBack/blob/main/DigitalAgriculture/DigitalAgriculture.postman_collection.json)
