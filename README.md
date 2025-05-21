# Fintech - Sistema de Gerenciamento Financeiro

## Introdução

O Fintech é uma aplicação web desenvolvida para gerenciamento financeiro pessoal e empresarial. Ela permite que usuários criem contas, realizem transferências, acompanhem seu histórico de transações e gerenciem suas finanças de forma eficiente e segura.

## Requisitos do Sistema

- Java 17 ou superior
- Apache Tomcat 10 ou superior
- Oracle Database
- Maven 3.8 ou superior
- Navegador web moderno (Chrome, Firefox, Edge, Safari)

## Instalação

1. Clone o repositório:
   ```
   git clone https://github.com/LucasRodolfoDev/fiapFintech.git
   ```

2. Navegue até o diretório do projeto:
   ```
   cd fiapFintech
   ```

3. Configure o banco de dados:
   - Execute o script SQL em `src/main/resources/sql/create_table_fintech_clientes.sql` para criar as tabelas necessárias
   - Opcionalmente, execute `src/main/resources/sql/insert_clientes_exemplo.sql` para inserir dados de exemplo

4. Configure as credenciais do banco de dados no arquivo de configuração apropriado

5. Compile o projeto:
   ```
   mvn clean package
   ```

6. Implante o arquivo WAR gerado em seu servidor Tomcat

## Guia do Usuário

### Tipos de Usuário

O sistema possui dois tipos de usuário:

1. **Cliente**: Usuários comuns que podem gerenciar suas próprias contas e realizar transações
2. **Gerente**: Usuários administrativos que podem gerenciar todos os clientes, contas e visualizar todas as transações

### Funcionalidades para Clientes

#### Cadastro e Login

1. Na página inicial, clique em "Criar uma conta" ou no botão "Cadastrar" no cabeçalho
2. Preencha o formulário com seus dados pessoais, incluindo:
   - Email e senha
   - Nome completo
   - CPF
   - Telefone
   - Endereço
   - Data de nascimento
   - Tipo de conta (Corrente ou Poupança)
3. Após o cadastro, faça login com seu email e senha

#### Gerenciamento de Contas

1. Na página inicial, você verá cards com todas as suas contas
2. Cada card mostra:
   - Número da conta
   - Tipo de conta (Corrente ou Poupança)
   - Saldo atual
   - Status da conta (Ativa ou Inativa)
   - Data de criação

3. Opções disponíveis para cada conta:
   - **Editar**: Permite alterar informações da conta
   - **Extrato**: Visualiza o histórico de transações
   - **Transferir**: Realiza transferências para outras contas

#### Transferências

1. Clique no botão "Transferir" em uma conta ou acesse "Transferência" no menu
2. Selecione a conta de origem
3. Selecione a conta de destino
4. Informe o valor a ser transferido
5. Confirme a transferência

#### Extrato

1. Clique no botão "Extrato" em uma conta ou acesse "Extrato" no menu
2. Visualize todas as transações relacionadas às suas contas
3. Utilize os filtros disponíveis para refinar a busca
4. As transações são codificadas por cores:
   - Verde: Entrada de dinheiro
   - Vermelho: Saída de dinheiro
   - Azul: Transferência entre suas próprias contas

### Funcionalidades para Gerentes

Além de todas as funcionalidades disponíveis para clientes, os gerentes também podem:

#### Gerenciamento de Usuários

1. Acesse "Listar Usuários" no menu
2. Visualize todos os usuários cadastrados no sistema
3. Opções disponíveis:
   - **Novo Usuário**: Cadastra um novo usuário
   - **Editar**: Altera informações de um usuário existente
   - **Excluir**: Remove um usuário do sistema

#### Gerenciamento de Clientes

1. Acesse "Listar Clientes" no menu
2. Visualize todos os clientes cadastrados no sistema
3. Opções disponíveis:
   - **Novo Cliente**: Cadastra um novo cliente
   - **Editar**: Altera informações de um cliente existente
   - **Excluir**: Remove um cliente do sistema

#### Gerenciamento de Contas

1. Acesse "Listar Contas" no menu
2. Visualize todas as contas cadastradas no sistema
3. Opções disponíveis:
   - **Editar**: Altera informações de uma conta existente
   - **Remover**: Remove uma conta do sistema

#### Visualização de Transações

1. Acesse "Extrato" no menu
2. Visualize todas as transações do sistema
3. Utilize os filtros para buscar por cliente ou conta específica

## Estrutura do Banco de Dados

O sistema utiliza quatro tabelas principais:

1. **FINTECH_USUARIOS**: Armazena informações de login dos usuários
   - ID: Identificador único
   - EMAIL: Email do usuário (único)
   - SENHA: Senha do usuário (criptografada)
   - TIPO_USUARIO: Tipo do usuário ('cliente' ou 'gerente')

2. **FINTECH_CLIENTES**: Armazena informações pessoais dos clientes
   - ID: Identificador único
   - NOME: Nome completo do cliente
   - CPF: CPF do cliente (único)
   - EMAIL: Email do cliente (único)
   - TELEFONE: Telefone do cliente
   - ENDERECO: Endereço do cliente
   - DATA_NASCIMENTO: Data de nascimento do cliente
   - STATUS: Status do cliente (ativo ou inativo)
   - DATA_CRIACAO: Data de criação do registro

3. **FINTECH_CONTAS**: Armazena informações das contas bancárias
   - ID: Identificador único
   - CLIENTE_ID: ID do cliente proprietário da conta
   - TIPO_CONTA: Tipo da conta (1 = Corrente, 2 = Poupança)
   - SALDO: Saldo atual da conta
   - STATUS: Status da conta (ativo ou inativo)
   - DATA_CRIACAO: Data de criação da conta

4. **FINTECH_TRANSFERENCIAS**: Armazena informações das transferências realizadas
   - ID: Identificador único
   - CONTA_ORIGEM_ID: ID da conta de origem
   - CONTA_DESTINO_ID: ID da conta de destino
   - VALOR: Valor da transferência
   - DATA_TRANSFERENCIA: Data e hora da transferência

## Informações Técnicas

### Tecnologias Utilizadas

- **Backend**: Java EE (Jakarta EE)
- **Frontend**: JSP, HTML, CSS, JavaScript
- **Frameworks**: Bootstrap 5
- **Banco de Dados**: Oracle
- **Servidor**: Apache Tomcat
- **Build**: Maven

### Arquitetura

O projeto segue o padrão MVC (Model-View-Controller):

- **Model**: Classes de modelo no pacote `br.com.LucasRodolfoDev.fiapfintech.model`
- **View**: Arquivos JSP na pasta `webapp`
- **Controller**: Servlets no pacote `br.com.LucasRodolfoDev.fiapfintech.controller`

Além disso, utiliza:

- **DAO**: Classes de acesso a dados no pacote `br.com.LucasRodolfoDev.fiapfintech.dao`
- **Filter**: Filtros para autenticação e autorização no pacote `br.com.LucasRodolfoDev.fiapfintech.filter`
- **Factory**: Fábricas para criação de objetos no pacote `br.com.LucasRodolfoDev.fiapfintech.factory`
- **Exception**: Classes de exceção personalizadas no pacote `br.com.LucasRodolfoDev.fiapfintech.exception`

## Segurança

O sistema implementa:

- Autenticação de usuários
- Controle de acesso baseado em papéis
- Proteção contra acesso não autorizado a recursos
- Validação de dados de entrada

## Suporte

Para suporte ou dúvidas, entre em contato com o desenvolvedor:

- Email: lrodolfo30@gmail.com
- GitHub: https://github.com/LucasRodolfoDev
