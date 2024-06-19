package services;

import br.gazin.application.dto.request.NivelDTO;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.exception.NotFoundException;
import br.gazin.application.model.Nivel;
import br.gazin.application.repository.INivelRepository;
import br.gazin.application.service.NivelService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NivelServiceTest {

    @Mock
    private INivelRepository repository;

    @InjectMocks
    private NivelService service;

    private NivelDTO nivelDTO;
    private Nivel nivel;

    @BeforeEach
    void setUp() {
        nivelDTO = NivelDTO.builder()
                .nivel("Nivel 1").build();

        nivel = new Nivel();
        nivel.setId(1L);
        nivel.setNivel("Nivel 1");
    }

    @Test
    void NivelService_CreateNivel_ReturnsResponseDto() {
        when(repository.save(Mockito.any(Nivel.class))).thenReturn(nivel);

        ServiceResponse response = service.cadastrar(nivelDTO);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void NivelService_GetAllNivel_ReturnsResponseDto() throws NotFoundException {
        Page<Nivel> clientes = Mockito.mock(Page.class);

        when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(clientes);

        ServiceResponse response = service.consultar(1, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void NivelService_UpdateNivel_ReturnNivelDto() throws NotFoundException {
        long id = 1;
        when(repository.findById(id)).thenReturn(Optional.ofNullable(nivel));
        when(repository.save(nivel)).thenReturn(nivel);

        ServiceResponse response = service.atualizar(nivelDTO, id);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void NivelService_DeleteNivelById_ReturnVoid() {
        long id = 1;

        when(repository.findById(id)).thenReturn(Optional.ofNullable(nivel));
        doNothing().when(repository).delete(nivel);

        assertAll(() -> service.remover(id));
    }
}
