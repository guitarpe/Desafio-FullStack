package br.gazin.application.service;

import br.gazin.application.dto.request.NivelDTO;
import br.gazin.application.dto.response.MetaResponse;
import br.gazin.application.dto.response.PagesResponse;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.enuns.Mensagens;
import br.gazin.application.exception.BadRequestException;
import br.gazin.application.exception.NotFoundException;
import br.gazin.application.model.Nivel;
import br.gazin.application.repository.INivelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NivelService {

    private final INivelRepository repository;

    public ServiceResponse cadastrar(NivelDTO objeto) {
        String mensagem = Mensagens.NIVEL_SUCCESS_SAVE.value();
        Nivel nivelCad = new Nivel();
        boolean status = true;

        try {
            Optional<Nivel> consulta = repository.buscarPorNivel(objeto.getNivel());

            if(consulta.isPresent())
                throw new NotFoundException(Mensagens.NIVEL_ERROR_EXISTS.value());

            nivelCad = repository.save(Nivel.builder()
                                            .nivel(objeto.getNivel())
                                            .build());

        } catch (Exception ex) {
            status = false;
            mensagem = ex.getMessage();
        }

        return ServiceResponse.builder()
                .status(status)
                .mensagem(mensagem)
                .dados(nivelCad).build();
    }

    public ServiceResponse listaNiveis() throws NotFoundException {

        String mensagem = Mensagens.NIVEL_SUCCESS_LIST.value();

        List<Nivel> niveis = repository.findAll();

        if (niveis.isEmpty())
            throw new NotFoundException(Mensagens.NO_RESULTS.value());

        return ServiceResponse.builder()
                .status(true)
                .mensagem(mensagem)
                .dados(niveis).build();
    }

    public ServiceResponse consultar(int page, int size) throws NotFoundException {

        String mensagem = Mensagens.NIVEL_SUCCESS_LIST.value();
        int lastPage = 0;

        PagesResponse response = new PagesResponse();

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Nivel> niveis = repository.findAll(pageable);
        List<Nivel> listNiveis = niveis.getContent();

        if (listNiveis.isEmpty())
            throw new NotFoundException(Mensagens.NO_RESULTS.value());

        if (niveis.getTotalPages() < page)
            lastPage = (page + 1);

        response.setData(listNiveis);
        response.setMeta(MetaResponse.builder()
                .currentPage((niveis.getNumber() + 1))
                .perPage(niveis.getSize())
                .total(niveis.getTotalElements())
                .lastPage(lastPage).build());

        return ServiceResponse.builder()
                .status(true)
                .mensagem(mensagem)
                .dados(response).build();
    }

    public ServiceResponse atualizar(NivelDTO objeto, Long id) throws NotFoundException {
        String mensagem = Mensagens.NIVEL_SUCCESS_UPDT.value();
        Nivel nivelUpdt;
        try{
            Nivel nivel = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException(Mensagens.NO_RESULTS.value()));

            nivel.setNivel(objeto.getNivel());
            nivelUpdt = repository.save(nivel);

        } catch (Exception ex) {
            throw ex;
        }

        return ServiceResponse.builder()
                .status(true)
                .mensagem(mensagem)
                .dados(nivelUpdt).build();
    }

    public ServiceResponse remover(Long id) throws NotFoundException {
        String mensagem = Mensagens.NIVEL_SUCCESS_DEL.value();

        try{
            Nivel nivel = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException(Mensagens.NOT_FOUND.value()));

            repository.delete(nivel);

        } catch (BadRequestException ex) {
            throw ex;
        }

        return ServiceResponse.builder()
                .status(true)
                .mensagem(mensagem)
                .dados(null).build();
    }
}

