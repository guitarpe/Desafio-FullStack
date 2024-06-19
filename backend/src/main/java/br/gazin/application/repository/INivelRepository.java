package br.gazin.application.repository;

import br.gazin.application.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INivelRepository extends JpaRepository<Nivel, Long> {
    @Query("select n from Nivel n where lower(n.nivel) like lower(concat('%', :nivel, '%'))")
    Optional<Nivel> buscarPorNivel(@Param("nivel") String nivel);
}