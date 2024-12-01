Allocation Manager Backend
Este projeto é o backend da aplicação Allocation Manager, desenvolvido com Spring Boot e MySQL, que oferece uma API RESTful para gerenciamento de alocações de recursos.
Objetivo
Gerenciar Recursos: Prover APIs para criação, edição e exclusão de colaboradores e projetos.
Centralizar Dados: Implementar um banco de dados seguro e confiável para armazenar informações críticas.
Garantir Escalabilidade: Suportar múltiplas requisições simultâneas de forma eficiente.
Facilitar Integração: Fornecer endpoints RESTful para integração com diferentes plataformas.
Tecnologias Utilizadas
Spring Boot: Framework para criação de aplicações Java de forma rápida e fácil.
Spring Data JPA: Implementação do JPA para persistência de dados em banco de dados relacional.
MySQL: Banco de dados relacional utilizado para armazenar as informações do sistema.
Springdoc OpenAPI: Geração de documentação da API com a especificação OpenAPI (Swagger UI).
JUnit: Framework para testes automatizados.
Pré-requisitos
Antes de rodar o projeto, você precisará do seguinte:
Java 22 ou versão superior.
Maven: Para gerenciar dependências e build do projeto.
MySQL: Para o banco de dados.
Como Rodar o Projeto
Clone este repositório para sua máquina local:
https://github.com/Madson-Carvalho/Allocation-Manager-Be
1. Configurar o Banco de Dados
Crie um banco de dados no MySQL (ou qualquer outro banco relacional compatível com JPA).
Configure a conexão com o banco no arquivo application.properties ou application.yml (localizado em src/main/resources).
Exemplo de configuração para o MySQL:
spring.datasource.url=jdbc:mysql://localhost:3306/allocations_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

2. Rodar o Projeto
Com o banco de dados configurado e o Java 22 e Maven instalados, execute os seguintes comandos no terminal:
Compilar e rodar o projeto com Maven:
mvn spring-boot:run

Isso iniciará o servidor na porta padrão 8080. Você pode acessar a API em http://localhost:8080.
3. Acessar a Documentação da API
A documentação da API será gerada automaticamente pelo Swagger e pode ser acessada em:
http://localhost:8080/swagger-ui.html

4. Testes
Os testes podem ser executados com o seguinte comando:
mvn test

Isso executará todos os testes automatizados configurados para o projeto.
Estrutura do Projeto
src/main/java/com.allocation.manager/: Código fonte do backend.
src/main/resources/: Arquivos de configuração (como application.properties).
src/test/java/: Testes automatizados.
Contribuição
Faça um fork do projeto.
Crie uma branch para a sua feature (git checkout -b feature/nova-feature).
Faça as alterações e commit (git commit -am 'Adicionando nova feature').
Faça o push para a sua branch (git push origin feature/nova-feature).
Abra um Pull Request.
Licença
Este projeto está licenciado sob a MIT License.
Equipe de Desenvolvimento
Gabriel Monteiro de Souza
Madson Raynon da Silva Carvalho
Mateus Francisco Kons
Mayra Kauane da Silva Carvalho
Rodrigo Teles Dondé
Yuri Grams Broering
