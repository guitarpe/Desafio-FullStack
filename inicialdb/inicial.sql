create table tb_nivel
(
    ID    int auto_increment,
    NIVEL varchar(100) null,
    constraint tb_nivel_ID_uindex
        unique (ID)
);

INSERT INTO tb_nivel (NIVEL) VALUES ('Junior');
INSERT INTO tb_nivel (NIVEL) VALUES ('Pleno');
INSERT INTO tb_nivel (NIVEL) VALUES ('Sênior');

create table tb_desenvolvedores
(
    ID              int auto_increment,
    NIVEL_ID        int          null,
    NOME            varchar(150) not null,
    SEXO            char         not null,
    DATA_NASCIMENTO date         not null,
    HOBBY           varchar(150) null,
    constraint tb_desenvolvedores_ID_uindex
        unique (ID),
    constraint tb_desenvolvedores_tb_nivel_ID_fk
        foreign key (NIVEL_ID) references tb_nivel (ID)
);