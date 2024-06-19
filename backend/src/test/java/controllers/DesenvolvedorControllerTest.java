package controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.gazin.application.controller.DesenvolvedorController;
import br.gazin.application.dto.request.DesenvolvedorDTO;
import br.gazin.application.dto.response.MetaResponse;
import br.gazin.application.dto.response.PagesResponse;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.enuns.Mensagens;
import br.gazin.application.service.DesenvolvedorService;
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

@ExtendWith(MockitoExtension.class)
class DesenvolvedorControllerTest {

    @InjectMocks
    private DesenvolvedorController controller;

    @Mock
    private DesenvolvedorService service;

    @Autowired
    private MockMvc mockMvc;
    private DesenvolvedorDTO desenvolvedor;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        desenvolvedor = new DesenvolvedorDTO();
        desenvolvedor.setId(1L);
        desenvolvedor.setNome("Dev 1");
        desenvolvedor.setSexo('M');
        desenvolvedor.setNivelId(1L);
        desenvolvedor.setDataNascimento("1981-08-11");
        desenvolvedor.setHobby("filmes");
    }

    @Test
    void DesenvolvedorController_GetAllDesenvolvedor_ReturnResponseDto() throws Exception {
        PagesResponse devRespIn = PagesResponse.builder()
                .meta(MetaResponse.builder()
                        .perPage(10).currentPage(1).build())
                .data(Collections.singletonList(desenvolvedor)).build();

        ServiceResponse response = ServiceResponse.builder().dados(devRespIn)
                .status(true).mensagem(Mensagens.DESENV_SUCCESS_LIST.value()).build();

        when(service.consultar(1, 10)).thenReturn(response);

        ResultActions result = mockMvc.perform(get("/api/desenvolvedores")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10"));

        PagesResponse devRespOut = (PagesResponse) response.getDados();
        List<DesenvolvedorDTO> listDevs = (List<DesenvolvedorDTO>) devRespOut.getData();

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.DESENV_SUCCESS_LIST.value())))
                .andExpect(jsonPath("$.data.content.size()", CoreMatchers.is(listDevs.size())));
    }

    @Test
    void DesenvolvedorController_CreateDesenvolvedor_ReturnCreated() throws Exception {
        ServiceResponse response = ServiceResponse.builder().status(true)
                .mensagem(Mensagens.DESENV_SUCCESS_SAVE.value()).dados(Collections.singletonList(desenvolvedor)).build();

        when(service.cadastrar(desenvolvedor)).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String devJson = objectMapper.writeValueAsString(desenvolvedor);

        ResultActions result = mockMvc.perform(post("/api/desenvolvedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(devJson));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.DESENV_SUCCESS_SAVE.value())))
                .andExpect(jsonPath("$.data[0].id", CoreMatchers.is(desenvolvedor.getId().intValue())))
                .andExpect(jsonPath("$.data[0].nome", CoreMatchers.is(desenvolvedor.getNome())))
                .andExpect(jsonPath("$.data[0].sexo", CoreMatchers.is(desenvolvedor.getSexo())))
                .andExpect(jsonPath("$.data[0].niveId", CoreMatchers.is(desenvolvedor.getNivelId())))
                .andExpect(jsonPath("$.data[0].dataNascimento", CoreMatchers.is(desenvolvedor.getDataNascimento())))
                .andExpect(jsonPath("$.data[0].hobby", CoreMatchers.is(desenvolvedor.getHobby())));
    }

    @Test
    void DesenvolvedorController_UpdateDesenvolvedor_ReturnDesenvolvedorDto() throws Exception {
        ServiceResponse response = ServiceResponse.builder().status(true)
                .mensagem(Mensagens.DESENV_SUCCESS_UPDT.value()).dados(Collections.singletonList(desenvolvedor)).build();

        when(service.atualizar(desenvolvedor, 1L)).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String clienteJson = objectMapper.writeValueAsString(desenvolvedor);

        ResultActions result = mockMvc.perform(put("/api/desenvolvedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.DESENV_SUCCESS_UPDT.value())))
                .andExpect(jsonPath("$.data[0].id", CoreMatchers.is(desenvolvedor.getId().intValue())))
                .andExpect(jsonPath("$.data[0].nome", CoreMatchers.is(desenvolvedor.getNome())))
                .andExpect(jsonPath("$.data[0].sexo", CoreMatchers.is(desenvolvedor.getSexo())))
                .andExpect(jsonPath("$.data[0].niveId", CoreMatchers.is(desenvolvedor.getNivelId())))
                .andExpect(jsonPath("$.data[0].dataNascimento", CoreMatchers.is(desenvolvedor.getDataNascimento())))
                .andExpect(jsonPath("$.data[0].hobby", CoreMatchers.is(desenvolvedor.getHobby())));
    }

    @Test
    void DesenvolvedorController_DeleteDesenvolvedor_ReturnString() throws Exception {
        ServiceResponse response = ServiceResponse.builder().status(true)
                .mensagem(Mensagens.DESENV_SUCCESS_DEL.value()).dados(null).build();
        when(service.remover(1L)).thenReturn(response);

        ResultActions result = mockMvc.perform(delete("/api/desenvolvedores/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", CoreMatchers.is(200)))
                .andExpect(jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Mensagens.DESENV_SUCCESS_DEL.value())));
    }
}
