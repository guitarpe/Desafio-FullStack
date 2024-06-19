package services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import br.gazin.application.dto.request.DesenvolvedorDTO;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.exception.NotFoundException;
import br.gazin.application.model.Desenvolvedor;
import br.gazin.application.model.Nivel;
import br.gazin.application.repository.IDesenvolvedorRepository;
import br.gazin.application.service.DesenvolvedorService;
import br.gazin.application.utils.Utils;
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

@ExtendWith(MockitoExtension.class)
class DesenvolvedorServiceTest {

    @Mock
    private IDesenvolvedorRepository repository;

    @InjectMocks
    private DesenvolvedorService service;

    private DesenvolvedorDTO desenvolvedorDTO;
    private Desenvolvedor desenvolvedor;
    private Nivel nivel;

    @BeforeEach
    void setUp() {
        desenvolvedorDTO = DesenvolvedorDTO.builder()
                .id(1L)
                .nome("Dev 1")
                .sexo('M').nivelId(1L)
                .dataNascimento("1981-08-11")
                .hobby("filmes").build();

        nivel = new Nivel();
        nivel.setId(1L);
        nivel.setNivel("Nivel 1");

        desenvolvedor = new Desenvolvedor();
        desenvolvedor.setId(1L);
        desenvolvedor.setNome("Dev 1");
        desenvolvedor.setSexo('M');
        desenvolvedor.setNivel(nivel);
        desenvolvedor.setDtNascimento(Utils.stringToDate("1981-08-11"));
        desenvolvedor.setHobby("filmes");
    }

    @Test
    void DesenvolvedorService_CreateDesenvolvedor_ReturnsResponseDto() throws NotFoundException {
        when(repository.save(Mockito.any(Desenvolvedor.class))).thenReturn(desenvolvedor);

        ServiceResponse response = service.cadastrar(desenvolvedorDTO);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void DesenvolvedorService_GetAllDesenvolvedor_ReturnsResponseDto() throws NotFoundException {
        Page<Desenvolvedor> clientes = Mockito.mock(Page.class);

        when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(clientes);

        ServiceResponse response = service.consultar(1, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void DesenvolvedorService_UpdateDesenvolvedor_ReturnDesenvolvedorDto() throws NotFoundException {
        long id = 1;
        when(repository.findById(id)).thenReturn(Optional.ofNullable(desenvolvedor));
        when(repository.save(desenvolvedor)).thenReturn(desenvolvedor);

        ServiceResponse response = service.atualizar(desenvolvedorDTO, id);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void DesenvolvedorService_DeleteDesenvolvedorById_ReturnVoid() {
        long id = 1;

        when(repository.findById(id)).thenReturn(Optional.ofNullable(desenvolvedor));
        doNothing().when(repository).delete(desenvolvedor);

        assertAll(() -> service.remover(id));
    }
}
