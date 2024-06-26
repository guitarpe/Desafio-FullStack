package br.gazin.application.controller;

import br.gazin.application.dto.request.NivelDTO;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.dto.response.SuccessResponse;
import br.gazin.application.exception.NotFoundException;
import br.gazin.application.service.NivelService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class NivelController {

    @Autowired
    private NivelService service;

    @GetMapping(value = "/lista-niveis",
            produces = MediaType.APPLICATION_JSON_VALUE)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Nivel recuperado com sucesso"),
            @ApiResponse(code = 404, message = "Nivel não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<SuccessResponse> listagem() throws NotFoundException {

        ServiceResponse retorno = service.listaNiveis();

        return ResponseEntity.ok().body(SuccessResponse.builder()
                .code(200)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }

    @GetMapping(value = "/niveis",
            produces = MediaType.APPLICATION_JSON_VALUE)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Nivel recuperado com sucesso"),
            @ApiResponse(code = 404, message = "Nivel não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<SuccessResponse> consultar(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) throws NotFoundException {

        ServiceResponse retorno = service.consultar(page, size);

        return ResponseEntity.ok().body(SuccessResponse.builder()
                .code(200)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }

    @PostMapping(value = "/niveis",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Nivel registrado com sucesso"),
            @ApiResponse(code = 400, message = "Corpo da requisição incorreto"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SuccessResponse> cadastrar(@RequestBody @Valid NivelDTO nivel) {
        ServiceResponse retorno = service.cadastrar(nivel);

        if (!retorno.isStatus()) {
            throw new EntityNotFoundException(retorno.getMensagem());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.builder()
                .code(201)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }

    @PutMapping("/niveis/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Nivel atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Corpo da requisição incorreto"),
            @ApiResponse(code = 404, message = "Nivel não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SuccessResponse> atualizar(@RequestBody @Valid NivelDTO nivel,
                                                     @PathVariable("id") long id) throws NotFoundException {
        ServiceResponse retorno = service.atualizar(nivel, id);

        return ResponseEntity.ok().body(SuccessResponse.builder()
                .code(200)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }

    @DeleteMapping("/niveis/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Nivel deletado com sucesso"),
            @ApiResponse(code = 400, message = "Erro interno do servidor"),
            @ApiResponse(code = 404, message = "Nivel não encontrado")
    })
    public ResponseEntity<SuccessResponse> remover(@PathVariable("id") long id) throws NotFoundException {

        ServiceResponse retorno = service.remover(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(SuccessResponse.builder()
                .code(204)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }
}
