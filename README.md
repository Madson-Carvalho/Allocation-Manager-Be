# Allocation Manager Backend
Este projeto é o backend da aplicação Allocation Manager, desenvolvido com Spring Boot e MySQL, que oferece uma API RESTful para gerenciamento de alocações de recursos.
## Objetivo
Gerenciar Recursos: Prover APIs para criação, edição e exclusão de colaboradores e projetos.<br>
Centralizar Dados: Implementar um banco de dados seguro e confiável para armazenar informações críticas.<br>
Garantir Escalabilidade: Suportar múltiplas requisições simultâneas de forma eficiente.<br>
Facilitar Integração: Fornecer endpoints RESTful para integração com diferentes plataformas.<br>
## Tecnologias Utilizadas
Spring Boot: Framework para criação de aplicações Java de forma rápida e fácil.<br>
Spring Data JPA: Implementação do JPA para persistência de dados em banco de dados relacional.<br>
MySQL: Banco de dados relacional utilizado para armazenar as informações do sistema.<br>
Springdoc OpenAPI: Geração de documentação da API com a especificação OpenAPI (Swagger UI).<br>
JUnit: Framework para testes automatizados.<br>
## Pré-requisitos
Antes de rodar o projeto, você precisará do seguinte:<br>
Java 22 ou versão superior.<br>
Maven: Para gerenciar dependências e build do projeto.<br>
MySQL: Para o banco de dados.<br>
## Como Rodar o Projeto
Clone este repositório para sua máquina local:<br>
https://github.com/Madson-Carvalho/Allocation-Manager-Be<br>
1. Configurar o Banco de Dados<br>
Crie um banco de dados no MySQL (ou qualquer outro banco relacional compatível com JPA).<br>
Configure a conexão com o banco no arquivo application.properties ou application.yml (localizado em src/main/resources).<br>
Exemplo de configuração para o MySQL:<br>
spring.datasource.url=jdbc:mysql://localhost:3306/allocations_db<br>
spring.datasource.username=root<br>
spring.datasource.password=root<br>
spring.jpa.hibernate.ddl-auto=update<br>
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect<br>

2. Rodar o Projeto
Com o banco de dados configurado e o Java 22 e Maven instalados, execute os seguintes comandos no terminal:<br>
Compilar e rodar o projeto com Maven:<br>
mvn spring-boot:run<br>

Isso iniciará o servidor na porta padrão 8080. Você pode acessar a API em http://localhost:8080.<br>
3. Acessar a Documentação da API<br>
A documentação da API será gerada automaticamente pelo Swagger e pode ser acessada em:<br>
http://localhost:8080/swagger-ui.html<br>

4. Testes<br>
Os testes podem ser executados com o seguinte comando:<br>
mvn test<br>

Isso executará todos os testes automatizados configurados para o projeto.<br>
## Estrutura do Projeto
src/main/java/com.allocation.manager/: Código fonte do backend.
src/main/resources/: Arquivos de configuração (como application.properties).
src/test/java/: Testes automatizados.
## Contribuição
Sinta-se à vontade para contribuir! Se você encontrar algum problema ou desejar adicionar melhorias, crie uma issue ou envie um pull request.

## Licença
Este projeto está licenciado sob a MIT License. Veja o arquivo LICENSE para mais detalhes.

## Equipe de Desenvolvimento
Gabriel Monteiro de Souza<br>
Madson Raynon da Silva Carvalho<br>
Mateus Francisco Kons<br>
Mayra Kauane da Silva Carvalho<br>
Rodrigo Teles Dondé<br>
Yuri Grams Broering
