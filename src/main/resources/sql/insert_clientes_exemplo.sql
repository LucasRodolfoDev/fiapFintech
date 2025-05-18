-- Inserindo clientes de exemplo
INSERT INTO FINTECH_CLIENTES (NOME, CPF, EMAIL, TELEFONE, ENDERECO, DATA_NASCIMENTO, STATUS)
VALUES (
    'João Silva',
    '12345678901',
    'joao.silva@email.com',
    '(11) 98765-4321',
    'Rua das Flores, 123 - São Paulo/SP',
    TO_DATE('15/05/1990', 'DD/MM/YYYY'),
    1
);

INSERT INTO FINTECH_CLIENTES (NOME, CPF, EMAIL, TELEFONE, ENDERECO, DATA_NASCIMENTO, STATUS)
VALUES (
    'Maria Oliveira',
    '98765432109',
    'maria.oliveira@email.com',
    '(21) 91234-5678',
    'Av. Principal, 456 - Rio de Janeiro/RJ',
    TO_DATE('22/08/1985', 'DD/MM/YYYY'),
    1
);

INSERT INTO FINTECH_CLIENTES (NOME, CPF, EMAIL, TELEFONE, ENDERECO, DATA_NASCIMENTO, STATUS)
VALUES (
    'Pedro Santos',
    '45678912345',
    'pedro.santos@email.com',
    '(31) 99876-5432',
    'Rua dos Pássaros, 789 - Belo Horizonte/MG',
    TO_DATE('10/12/1995', 'DD/MM/YYYY'),
    1
); 