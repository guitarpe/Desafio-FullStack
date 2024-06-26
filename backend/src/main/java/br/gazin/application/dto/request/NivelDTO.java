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
public class NivelDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nivel")
    @NotNull(message = "O campo nivel é obrigatório")
    private String nivel;
}
