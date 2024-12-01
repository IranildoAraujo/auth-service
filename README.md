# auth-service

## Instruções

O objetivo é fornecer uma cópia do projeto para fins de desenvolvimento e teste.

## Pré-requisitos

- Sistema Operacional Ubuntu(Versão em que a API foi testada: 24.04 LTS)
- Configurar seu ambiente para encode UTF8
- Instalar o Java 17.
- Instalar o Spring tool Suite 4
- Instalar a ferramenta para testes de requisições(Insomnia, Postman ou outro).
- Instalar o Lombok
- Instalar o Docker(version 24.0.7)
- Instalar o Maven
- Imagem docker: mysql:5.7
- (Opcional) [Link para instalação do sonarlint](https://marketplace.eclipse.org/content/sonarlint)
- (Opcional) [Link para instalação do Eclemma](https://www.eclemma.org/jacoco/)

## Importar

Importar o projeto como um projeto Maven existente.

## Variáveis de ambiente

- PROFILES_ACTIVE=dev
- AUTH_SERVICE={Adicionar o nome do serviço}
- CONTEXT_PATH={Adicionar o caminho do context do servidor}
- CORS_ORIGIN={Adicione a url terá acesso}
- SECRET_KEY={Adicione a key}
- EXPIRE_LENGTH={Adicione o tempo de expiração}
- DS_DRIVER_CLASS_NAME={Adicione o driver do BD}
- DS_URL={Adicione a url de conexão com o BD}
- DS_USERNAME={Adicione o usuário de conexão com o BD}
- DS_PASSWORD={Adicione o password de conexão com o BD}
- SPRINGDOC_PATHS={Adicione o(s) caminho(s) para a OpenAPI}

## Swagger

Todo projeto por padrão terá o Swagger gerando a documentação da API. O Swagger só estará disponível no profile dev.

Para acessar o link do Swagger:

```
	{context-path}/swagger-ui/index.html
```

## Instalação da docker image do Mysql: 5.7

## Pré-requisitos:
 - Usário administrador(root)
 - Uma ferramenta para testar a conexão(DBeaver, MySQLWorkBench ou outra).

```
	sudo docker pull mysql:5.7
```

 - Após baixar liste a(s) imagem(s) para obter o nome da imagem com a tag, execute no terminal:
```
	sudo docker images
```

 - Conectando em localhost tudo o que chegar na porta 3306(localhost). Faça o Mapeamento da porta com o parâmetro `-p` (Antes de criar um novo container, caso tenha um container antigo, primeiro pare-o e depois remova-o):

```
	docker run -e MYSQL_ROOT_PASSWORD=root --name containerMysql -d -p 3306(localhost):3306(container) mysql:5.7
```

## Parâmetros explicados:

 - Define o login e a senha no BD= -e MYSQL_ROOT_PASSWORD=...
 - Nome do container= --name containerMysql
 - Inicialização detachada para utilização do terminal para outras coisas= -d 
 - Mapeia a porta do localhost e do container= -p 389:389
 - Escolha da imagem a inicializar= mysql:5.7

## Comandos para gerenciar a imagem docker do MySQL:

- Verificar container em execução:

```bash
	sudo docker ps
```

- Parar o container por CONTAINER_ID ou o NAMES:

```bash
	sudo docker stop <CONTAINER_ID ou o NAMES>
```

- Inicializar o container por CONTAINER_ID ou o NAMES:

```bash
	sudo docker start <CONTAINER_ID ou o NAMES>
```

- Remover o container:

```bash
	sudo docker rm <CONTAINER ID ou o NAMES>
```

<span style="color:red"><strong>ATENÇÃO: </strong></span> 
Ter permissões de administrador no sistema operacional testado.
O Docker não vai deixar remover se ele estiver em execução.

## Instruções para executar os testes de cobertura 

- Adicionar a variável de ambiente abaixo no **Run Configuration** da IDE:

```
 PROFILES_ACTIVE=test
```