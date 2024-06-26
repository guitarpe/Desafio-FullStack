package br.gazin.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesenvolvedorDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    @NotNull(message = "O campo nome é obrigatório")
    private String nome;

    @JsonProperty("sexo")
    @NotNull(message = "O campo sexo é obrigatório")
    private char sexo;

    @JsonProperty("nivel_id")
    @NotNull(message = "O campo nivel_id é obrigatório")
    private Long nivelId;

    @JsonProperty("data_nascimento")
    @NotNull(message = "O campo data_nascimento é obrigatório")
    private String dataNascimento;

    @JsonProperty("hobby")
    @NotNull(message = "O campo hobby é obrigatório")
    private String hobby;
}
