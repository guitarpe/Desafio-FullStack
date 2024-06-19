package br.gazin.application.repository;

import br.gazin.application.model.Desenvolvedor;
import br.gazin.application.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;


@Repository
public interface IDesenvolvedorRepository extends JpaRepository<Desenvolvedor, Long> {
    @Query("select d from Desenvolvedor d where lower(d.nome) = lower(:nome) and d.sexo = :sexo " +
            "and lower(d.hobby) like lower(:hobby) and d.dtNascimento = :dtNascimento and d.nivel = :nivel")
    Set<Desenvolvedor> buscarDesenvolvedorExistente(@Param("nome") String nome,
                                                    @Param("sexo") char sexo,
                                                    @Param("hobby") String hobby,
                                                    @Param("dtNascimento") LocalDate dtNascimento,
                                                    @Param("nivel") Nivel nivel);
}