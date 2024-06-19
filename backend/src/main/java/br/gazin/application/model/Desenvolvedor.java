package br.gazin.application.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "TB_DESENVOLVEDORES")
public class Desenvolvedor {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "SEXO", nullable = false)
    private char sexo;

    @OneToOne(targetEntity = Nivel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "NIVEL_ID")
    private Nivel nivel;

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private LocalDate dtNascimento;

    @Column(name = "HOBBY")
    private String hobby;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Desenvolvedor clientes = (Desenvolvedor) o;
        return id != null && Objects.equals(id, clientes.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
