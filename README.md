<h1>Arquitetura de Microsserviços: Padrão Saga Orquestrado</h1>

<h3>Este Projeto consiste numa aplicação do padrão de projeto saga orquestrado, utilizando apache kafka para comunicação entre os microsserviços.</h3>

  <h2>Execução do Projeto</h2>


  <p>Há várias maneiras de executar os projetos:</p>
    <ul>
        <li>Executando tudo via docker-compose</li>
        <li>Executando tudo via script de automação que eu disponibilizei (build.py)</li>
        <li>Executando apenas os serviços de bancos de dados e message broker (Kafka) separadamente</li>
        <li>Executando as aplicações manualmente via CLI (java -jar ou gradle bootRun ou via IntelliJ)</li>
    </ul>

  <p>Para rodar as aplicações, será necessário ter instalado:</p>
    <ul>
        <li>Docker</li>
        <li>Java 17</li>
        <li>Gradle 7.6 ou superior</li>
    </ul>

  <h2>desenvolveremos a seguinte aquitetura:</h2>

  ![Arquitetura Proposta](https://github.com/user-attachments/assets/27243c82-f328-4945-be19-0de22fd85595)


   <h3>01 - Execução geral via docker-compose</h3>
   

  <p>Basta executar o comando no diretório raiz do repositório:</p>
    <pre><code>docker-compose up --build -d</code></pre>

   <p><strong>Obs.:</strong> para rodar tudo desta maneira, é necessário realizar o build das 5 aplicações, veja nos passos abaixo sobre como fazer isto.</p>

  <h3>02 - Execução geral via automação com script em Python</h3>


  <p>Basta executar o arquivo <code>build.py</code>. Para isto, é necessário ter o Python 3 instalado.</p>

   <p>Para executar, basta apenas executar o seguinte comando no diretório raiz do repositório:</p>
    <pre><code>python build.py</code></pre>

   <p>Será realizado o build de todas as aplicações, removidos todos os containers e em sequência, será rodado o docker-compose.</p>

   <h3>03 - Executando os serviços de bancos de dados e Message Broker</h3>
   

  <p>Para que seja possível executar os serviços de bancos de dados e Message Broker, como MongoDB, PostgreSQL e Apache Kafka, basta ir no diretório raiz do repositório, onde encontra-se o arquivo <code>docker-compose.yml</code> e executar o comando:</p>
    <pre><code>docker-compose up --build -d order-db kafka product-db payment-db inventory-db</code></pre>

  <p>Como queremos rodar apenas os serviços de bancos de dados e Message Broker, é necessário informá-los no comando do docker-compose, caso contrário, as aplicações irão subir também.</p>

  <p>Para parar todos os containers, basta rodar:</p>
    <pre><code>docker-compose down</code></pre>

  <p>Ou então:</p>
    <pre><code>docker stop $(docker ps -aq) docker container prune -f</code></pre>

   <h3>04 - Executando manualmente via CLI</h3>


  <p>Antes da execução do projeto, realize o build da aplicação indo no diretório raiz e executando o comando:</p>
    <pre><code>gradle build -x test</code></pre>

  <p>Para executar os projetos com Gradle, basta entrar no diretório raiz de cada projeto, e executar o comando:</p>
    <pre><code>gradle bootRun</code></pre>

   <p>Ou então, entrar no diretório: <code>build/libs</code> e executar o comando:</p>
    <pre><code>java -jar nome_do_jar.jar</code></pre>

  <h2>Acessando a aplicação</h2>


   <p>Para acessar as aplicações e realizar um pedido, basta acessar a URL:</p>
    <p><a href="http://localhost:3000/swagger-ui.html">http://localhost:3000/swagger-ui.html</a></p>
