# Sistema de Registro de Desenvolvedores

O aplicação consiste num cadastro de Desenvolvedores com definições de perfis para os mesmos. 
Trata-se de um projeto Java Spring Boot com um frontend em ReactJS e banco de dados MariaDB, todos dockerizados.

### Requisitos

- Docker
- Docker Compose

### Como Executar

1) Realize o clone o repositório:

   ```bash
   git clone https://github.com/guitarpe/Desafio-FullStack.git
   cd Desafio-FullStack
   
2) Construa e inicie os contêineres Docker:

    ```bash
   docker-compose up --build
   
3) Acesse a aplicação:

    - Backend: http://localhost:8080
    - Frontend: http://localhost:3000
   
### Endponts disponíveis:

1) Cadastrar Desenvolvedores - **POST**- http://localhost:8080/api/desenvolvedores
2) Atualizar Desenvolvedor - **PUT** - http://localhost:8080/api/desenvolvedores/1
3) Consultar Desenvolvedores - **GET** - http://localhost:8080/api/desenvolvedores
4) Deletar Desenvolvedor - **DELETE** - http://localhost:8080/api/desenvolvedores/1

5) Cadastrar Nível - **POST** - http://localhost:8080/api/niveis
6) Atualizar Nível - **PUT** - http://localhost:8080/api/niveis/1
7) Consultar Nível - **GET** - http://localhost:8080/api/niveis/1
8) Deletar Nível - **DELETE** - http://localhost:8080/api/niveis/1

### Especificações

#### 2.1 - **POST** - Cadastrar Desenvolvedores

Realiza o cadastro do desenvolvedores

http://localhost:8080/api/desenvolvedores

```json
{
  "nivel_id": 1,
  "nome": "Nome do Desenvolvedor",
  "sexo": "M",
  "data_nascimento": "1990-01-01",
  "hobby": "Programação"
}
```

#### 2.2 - **PUT** - Atualizar Desenvolvedor

Atualiza um desenvolvedor já cadastrado

##### API
http://localhost:8080/api/desenvolvedores/1
```json
{
  "nome": "Novo Nome do Desenvolvedor",
  "hobby": "Violão",
  "nivel_id": 2,
  "sexo": "F",
  "data_nascimento": "1990-01-01"
}
```

#### 2.3 - **GET** - Consultar Desenvolvedores

Realiza a consulta paginada dos desenvolvedores cadastrados.

http://localhost:8080/api/desenvolvedores?page=1&size=10

#### 2.4 - **DELETE** - Deletar Desenvolvedor

Exclui um desenvolvedor já cadastrado

http://localhost:8080/api/desenvolvedores/1

#### 2.5 - **POST** - Cadastrar Níveis

Realiza o cadastro de um nível

```json
{
  "nivel": "Nome do Nível"
}
```
#### 2.6 - **PUT** - Atualizar Nível

Atualiza um nível já cadastrado

http://localhost:8080/api/niveis/1

```json
{
  "nivel": "Nome do Nível"
}
```

#### 2.7 - **GET** - Listar Níveis

Lista todos os niveis cadastrados de forma paginada.

http://localhost:8080/api/niveis?page=1&size=10

#### 2.8 - **DELETE** - Deletar Nível

Realiza a exclusão de um nível já cadastrado.

http://localhost:8080/api/niveis/3

### 3 - FRONTEND
O usuário pode acessar o sistema através da url http://localhost:3000/. Nela é possível realizar as consultas, cadastros, edições e remoções dos registros.


### Observações
- O arquivo SQL para criação das tabelas está disponível na pasta inicialdb
- O collection para o postamna está disponível na pasra resource, dentro da estrutura do projeto
