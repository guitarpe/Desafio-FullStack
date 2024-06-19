create table tb_nivel
(
    ID    int auto_increment,
    NIVEL varchar(100) null,
    constraint tb_nivel_ID_uindex
        unique (ID)
);