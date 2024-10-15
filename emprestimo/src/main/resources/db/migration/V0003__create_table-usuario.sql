CREATE TABLE seguranca.usuario(
    id SERIAL NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50)  UNIQUE NOT NULL,
    id_pessoa INTEGER NOT NULL
);

ALTER TABLE seguranca.usuario
    ADD CONSTRAINT fk_usuario_pessoa
        FOREIGN KEY (id_pessoa)
            REFERENCES public.pessoa(id);

ALTER TABLE seguranca.usuario
    ADD PRIMARY KEY (id);