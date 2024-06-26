package br.gazin.application.service;

import br.gazin.application.dto.request.DesenvolvedorDTO;
import br.gazin.application.dto.response.MetaResponse;
import br.gazin.application.dto.response.PagesResponse;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.enuns.Mensagens;
import br.gazin.application.exception.NotFoundException;
import br.gazin.application.model.Desenvolvedor;
import br.gazin.application.model.Nivel;
import br.gazin.application.repository.IDesenvolvedorRepository;
import br.gazin.application.repository.INivelRepository;
import br.gazin.application.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DesenvolvedorService {

    private final IDesenvolvedorRepository repository;
    private final INivelRepository nivelRepository;

    public ServiceResponse cadastrar(DesenvolvedorDTO objeto) throws NotFoundException {
        String mensagem = Mensagens.DESENV_SUCCESS_SAVE.value();
        Desenvolvedor desenvolvedor;

        try {
            Nivel nivel = nivelRepository.findById(objeto.getNivelId())
                    .orElseThrow(() -> new NotFoundException(Mensagens.NIVEL_ERROR_FOUND.value()));

            Set<Desenvolvedor> resultados = repository.buscarDesenvolvedorExistente(objeto.getNome(), objeto.getSexo(),
                    objeto.getHobby(), Utils.stringToDate(objeto.getDataNascimento()), nivel);

            if(!resultados.isEmpty())
                throw new NotFoundException(Mensagens.DESENV_EXISTS.value());

            desenvolvedor = repository.save(Desenvolvedor.builder()
                                        .nome(objeto.getNome())
                                        .sexo(objeto.getSexo())
                                        .hobby(objeto.getHobby())
                                        .dtNascimento(Utils.stringToDate(objeto.getDataNascimento()))
                                        .nivel(nivel).build());

        } catch (Exception ex) {
            throw ex;
        }

        return ServiceResponse.builder()
                .status(true)
                .mensagem(mensagem)
                .dados(desenvolvedor).build();
    }

    public ServiceResponse consultar(int page, int size) throws NotFoundException {

        String mensagem = Mensagens.DESENV_SUCCESS_LIST.value();
        int lastPage = 0;

        PagesResponse response = new PagesResponse();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Desenvolvedor> desenvolvedores = repository.findAll(pageable);
        List<Desenvolvedor> listOfDevs = desenvolvedores.getContent();

        if (listOfDevs.isEmpty())
            throw new NotFoundException(Mensagens.NO_RESULTS.value());

        if(desenvolvedores.getTotalPages() < page)
            lastPage = (page+1);

        response.setData(listOfDevs);
        response.setMeta(MetaResponse.builder()
                .currentPage((desenvolvedores.getNumber()+1))
                .perPage(desenvolvedores.getSize())
                .total(desenvolvedores.getTotalElements())
                .lastPage(lastPage).build());

        return ServiceResponse.builder()
                .mensagem(mensagem)
                .dados(response).build();
    }

    public ServiceResponse atualizar(DesenvolvedorDTO objeto, Long id) throws NotFoundException {
        String mensagem = Mensagens.DESENV_SUCCESS_UPDT.value();
        Desenvolvedor desenvUpdt;

        try {
            Desenvolvedor desenvolvedor = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException(Mensagens.NO_RESULTS.value()));

            Nivel nivel = nivelRepository.findById(objeto.getNivelId())
                    .orElseThrow(() -> new NotFoundException(Mensagens.NIVEL_ERROR_FOUND.value()));

            desenvolvedor.setNome(objeto.getNome());
            desenvolvedor.setSexo(objeto.getSexo());
            desenvolvedor.setHobby(objeto.getHobby());
            desenvolvedor.setDtNascimento(Utils.stringToDate(objeto.getDataNascimento()));
            desenvolvedor.setNivel(nivel);

            desenvUpdt = repository.save(desenvolvedor);

        } catch (Exception ex) {
            throw ex;
        }

        return ServiceResponse.builder()
                .status(true)
                .mensagem(mensagem)
                .dados(desenvUpdt).build();
    }

    public ServiceResponse remover(Long id) throws NotFoundException {
        String mensagem = Mensagens.DESENV_SUCCESS_DEL.value();

        try {
            Desenvolvedor consulta = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException(Mensagens.NO_RESULTS.value()));

            repository.delete(consulta);
        } catch (BadRequestException ex) {
            throw ex;
        }

        return ServiceResponse.builder()
                .status(true)
                .mensagem(mensagem)
                .dados(null).build();
    }
}

