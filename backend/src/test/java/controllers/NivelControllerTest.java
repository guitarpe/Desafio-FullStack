package controllers;

import br.gazin.application.controller.NivelController;
import br.gazin.application.dto.request.NivelDTO;
import br.gazin.application.dto.response.MetaResponse;
import br.gazin.application.dto.response.PagesResponse;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.enuns.Mensagens;
import br.gazin.application.service.NivelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NivelControllerTest {

    @InjectMocks
    private NivelController controller;

    @Mock
    private NivelService service;

    @Autowired
    private MockMvc mockMvc;
    private NivelDTO nivel;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        nivel = new NivelDTO();
        nivel.setId(1L);
        nivel.setNivel("Nivel 1");
    }

    @Test
    void NivelController_GetAllNivel_ReturnResponseDto() throws Exception {
        PagesResponse nvlRespIn = PagesResponse.builder()
                .meta(MetaResponse.builder()
                        .perPage(10).currentPage(1).build())
                .data(Collections.singletonList(nivel)).build();

        ServiceResponse response = ServiceResponse.builder().dados(nvlRespIn)
                .status(true).mensagem(Mensagens.NIVEL_SUCCESS_LIST.value()).build();

        when(service.consultar(1, 10)).thenReturn(response);

        ResultActions result = mockMvc.perform(get("/api/niveis")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10"));

        PagesResponse nvlRespOut = (PagesResponse) response.getDados();
        List<NivelDTO> listNvl = (List<NivelDTO>) nvlRespOut.getData();

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.NIVEL_SUCCESS_LIST.value())))
                .andExpect(jsonPath("$.data.content.size()", CoreMatchers.is(listNvl.size())));
    }

    @Test
    void NivelController_CreateNivel_ReturnCreated() throws Exception {
        ServiceResponse response = ServiceResponse.builder().status(true)
                .mensagem(Mensagens.NIVEL_SUCCESS_SAVE.value()).dados(Collections.singletonList(nivel)).build();

        when(service.cadastrar(nivel)).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String nivelJson = objectMapper.writeValueAsString(nivel);

        ResultActions result = mockMvc.perform(post("/api/niveis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nivelJson));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.NIVEL_SUCCESS_SAVE.value())))
                .andExpect(jsonPath("$.data[0].id", CoreMatchers.is(nivel.getId().intValue())))
                .andExpect(jsonPath("$.data[0].nivel", CoreMatchers.is(nivel.getNivel())));
    }

    @Test
    void NivelController_UpdateNivel_ReturnNivelDto() throws Exception {
        ServiceResponse response = ServiceResponse.builder().status(true)
                .mensagem(Mensagens.NIVEL_SUCCESS_UPDT.value()).dados(Collections.singletonList(nivel)).build();

        when(service.atualizar(nivel, 1L)).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String nivelJson = objectMapper.writeValueAsString(nivel);

        ResultActions result = mockMvc.perform(put("/api/niveis/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nivelJson));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.NIVEL_SUCCESS_UPDT.value())))
                .andExpect(jsonPath("$.data[0].id", CoreMatchers.is(nivel.getId().intValue())))
                .andExpect(jsonPath("$.data[0].nivel", CoreMatchers.is(nivel.getNivel())));
    }

    @Test
    void NivelController_DeleteNivel_ReturnString() throws Exception {
        ServiceResponse response = ServiceResponse.builder().status(true)
                .mensagem(Mensagens.NIVEL_SUCCESS_DEL.value()).dados(null).build();
        when(service.remover(1L)).thenReturn(response);

        ResultActions result = mockMvc.perform(delete("/api/niveis/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.NIVEL_SUCCESS_DEL.value())));
    }
}
