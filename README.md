# Projeto de Autenticação e Segurança

Este projeto demonstra a implementação de um sistema de autenticação e segurança utilizando Spring 3, Java 21, SQL Server e Spring Security 6.

## Índice

- [Introdução](#introdução)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Instalação](#instalação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Uso](#uso)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Introdução

Este projeto tem como objetivo demonstrar uma implementação básica de um sistema de autenticação e segurança com:

- Spring Framework 3
- Java 21
- SQL Server como banco de dados
- Spring Security 6 para controle de acesso e autenticação

## Pré-requisitos

- Java 21
- Maven
- SQL Server
- IDE de sua escolha (IntelliJ, Eclipse, etc.)

## Configuração do Ambiente

1. **Instalar o Java 21**:
    - Certifique-se de que o Java 21 está instalado e configurado no seu PATH.
    - Para verificar, execute `java -version` no terminal.

2. **Instalar o Maven**:
    - Certifique-se de que o Maven está instalado e configurado no seu PATH.
    - Para verificar, execute `mvn -version` no terminal.

3. **SQL Server**:
    - Instale e configure o SQL Server.
    - Certifique-se de que o SQL Server está em execução e você pode se conectar a ele.

## Configuração do Banco de Dados

1. Crie um banco de dados no SQL Server:

    ```sql
    CREATE DATABASE simple-security;
    ```

2. Crie uma tabela de usuários:

    ```sql
    CREATE TABLE users (
      id bigint NOT NULL,
      firstname varchar(255),
      lastname varchar(255),
      email varchar(255),
      password varchar(255),
      is_account_non_expired bit NOT NULL,
      is_account_non_locked bit NOT NULL,
      is_credentials_non_expired bit NOT NULL,
      is_enabled bit NOT NULL,
      CONSTRAINT pk_users PRIMARY KEY (id)
   )
   GO
    ```

## Instalação

1. Clone o repositório:

    ```bash
    git clone https://github.com/seu-usuario/auth-security-project.git
    cd auth-security-project
    ```

2. Configure o arquivo `application.yml` com as informações do seu banco de dados:

    ```properties
   spring:
     application:
        name: cronos-simple-security
     datasource:
        url: jdbc:sqlserver://localhost:1433;databaseName=simple-security;encrypt=false;trustServerCertificate=true
        username: user
        password: your_password
        driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
    ```

3. Compile e execute o projeto:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Estrutura do Projeto

- **src/main/java**: Contém o código-fonte do projeto.
- **src/main/resources**: Contém os arquivos de configuração.
- **pom.xml**: Arquivo de configuração do Maven.

## Uso

Após iniciar o projeto, você pode acessar o sistema de autenticação na seguinte URL:

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo LICENSE para obter mais informações.
