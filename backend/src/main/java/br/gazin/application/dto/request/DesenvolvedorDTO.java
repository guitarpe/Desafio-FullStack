package br.gazin.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesenvolvedorDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("sexo")
    private char sexo;

    @JsonProperty("nivel_id")
    private Long nivelId;

    @JsonProperty("data_nascimento")
    private String dataNascimento;

    @JsonProperty("hobby")
    private String hobby;
}
