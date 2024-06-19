package br.gazin.application.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "TB_NIVEL")
public class Nivel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NIVEL", nullable = false)
    private String nivel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Nivel clientes = (Nivel) o;
        return id != null && Objects.equals(id, clientes.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
