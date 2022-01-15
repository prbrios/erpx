create table md_funcao (
    id serial,
    nome text unique,
    funcao text,
    argumentos text
);