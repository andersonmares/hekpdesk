# Help Desk

## Sobre o projeto
O Help Desk é um sistema desenvolvido como parte de um desafio de desenvolvimento de software. Este sistema permite que os usuários façam login para abrir tickets e avaliar o atendimento recebido. Os operadores podem fazer login no sistema para oferecer suporte aos usuários, receber atualizações em tempo real sobre os tickets solicitados e encerrar serviços. Os tickets podem ser listados com paginação e filtrados por assunto ou ID.

## Tecnologias usadas
- **Back-end:** Java 17, Spring Boot 3
- **Frontend:** Angular 17 (autônomo: verdadeiro)
- **Banco de dados:** PostgreSQL 16
- **Autenticação:** OAuth2.0 com autenticação do Google
- **Comunicação em tempo real:** SSE (eventos enviados pelo servidor)
- **Documentação da API:** Swagger

## Estrutura do Projeto
- **Backend:** Localizado no diretório `backend`, utilizando Clean Architecture com as entidades User, Ticket, TicketFeedback, TicketUpdate.
- **Frontend:** No diretório `frontend`, construído com Angular 17 em modo autônomo.
- **Banco de dados:** Utiliza PostgreSQL 16, com tabelas sendo criadas automaticamente quando o serviço é iniciado através do Docker.

## Ambiente DevOps e Kubernetes
O arquivo `diagrama.png` ilustra como a plataforma Help Desk funciona em um ambiente DevOps utilizando Kubernetes. Mostra a estrutura dos sistemas e como eles são implantados e gerenciados para garantir escalabilidade e confiabilidade.

## Executando o projeto com Docker
Este projeto está configurado para ser executado facilmente usando Docker e Docker Compose. Para fazer isso, certifique-se de ter o Docker e o Docker Compose instalados em seu sistema.

1. Clone o repositório em sua máquina local.
2. Navegue até a pasta raiz do projeto.
3. Execute o comando: `docker-compose up --build`.
     - Este comando construirá as imagens necessárias para backend, frontend e banco de dados e iniciará os serviços.
4. Após o início dos serviços, o frontend estará acessível via `http://localhost:4200` e o backend através de `http://localhost:8080`.
5. A documentação da API pode ser acessada via Swagger em `http://localhost:8080/swagger-ui.html`.

## Autenticação
- Para usuários regulares: a autenticação é realizada através do processo padrão Google OAuth2.0.
- Para usuários do tipo gerente: É necessário cadastrar diretamente no banco de dados o mesmo e-mail utilizado com o Google. Isso é necessário para autenticar e acessar funcionalidades específicas do gerente no sistema.

## Etapas do pipeline de CI/CD
O pipeline CI/CD, definido no arquivo `.gitlab-ci.yml`, automatiza o processo de execução de testes e preparação do aplicativo para implantação. O pipeline inclui as seguintes etapas:

1. **Instalação de dependência:**
    - Instala todas as dependências necessárias para as partes backend e frontend do projeto.

2. **Teste de Unidade:**
    - Executa testes unitários para validar a integridade e confiabilidade do código tanto para backend quanto para frontend.
    - Garante que as novas alterações não quebrem as funcionalidades existentes.

3. **Construir:**
    - Compila o código-fonte do backend em um arquivo executável `api.jar`.
    - Prepara o front-end para implantação executando o processo de construção.

4. **Armazenamento de artefatos:**
    - Após uma compilação e execução de teste bem-sucedidas, o arquivo `api.jar` é armazenado como um artefato para download no GitLab.
    - O `api.jar` pode ser baixado da página 'Jobs' do pipeline CI/CD no GitLab. Procure o último trabalho de construção bem-sucedido com artefatos e baixe o `api.jar` de lá.


## Comunicação em tempo real
O backend usa SSE para comunicar atualizações em tempo real ao frontend. Isto é especialmente útil para os operadores acompanharem os tickets em tempo real.

## Contribuições
Contribuições são sempre bem-vindas! Para contribuir, bifurque o repositório, faça suas alterações e envie uma solicitação pull.
