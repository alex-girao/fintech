CREATE TABLE public.emprestimo(
    id SERIAL NOT NULL,
    id_pessoa INTEGER NOT NULL,
    valor_emprestimo DECIMAL(18,4) NOT NULL,
    numero_parcelas INTEGER NOT NULL,
    status_pagamento VARCHAR(50) NOT NULL,
    data_criacao DATE NOT NULL
);

ALTER TABLE public.emprestimo
    ADD CONSTRAINT fk_emprestimo_pessoa
        FOREIGN KEY (id_pessoa)
            REFERENCES public.pessoa(id);

ALTER TABLE public.emprestimo
    ADD PRIMARY KEY (id);