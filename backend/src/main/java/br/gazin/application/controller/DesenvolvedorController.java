package br.gazin.application.controller;

import br.gazin.application.dto.request.DesenvolvedorDTO;
import br.gazin.application.dto.response.ServiceResponse;
import br.gazin.application.dto.response.SuccessResponse;
import br.gazin.application.exception.NotFoundException;
import br.gazin.application.service.DesenvolvedorService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
public class DesenvolvedorController {

    @Autowired
    private DesenvolvedorService service;

    @GetMapping(value = "/desenvolvedores",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Desenvolvedor recuperado com sucesso"),
            @ApiResponse(code = 404, message = "Desenvolvedor não encontrado"),
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

    @PostMapping(value = "/desenvolvedores",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Desenvolvedor registrado com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<SuccessResponse> cadastrar(@RequestBody DesenvolvedorDTO desenvolvedor) throws NotFoundException {
        ServiceResponse retorno = service.cadastrar(desenvolvedor);

        return ResponseEntity.ok().body(SuccessResponse.builder()
                .code(200)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }

    @PutMapping("/desenvolvedores/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Desenvolvedor atualizado com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<SuccessResponse> atualizar(@RequestBody DesenvolvedorDTO desenvolvedor,
                                                     @PathVariable("id") long id) throws NotFoundException {
        ServiceResponse retorno = service.atualizar(desenvolvedor, id);

        return ResponseEntity.ok().body(SuccessResponse.builder()
                .code(200)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }

    @DeleteMapping("/desenvolvedores/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Desenvolvedor deletado com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<SuccessResponse> remover(@PathVariable("id") long id) throws NotFoundException {

        ServiceResponse retorno = service.remover(id);

        return ResponseEntity.ok().body(SuccessResponse.builder()
                .code(200)
                .success(true)
                .timestamp(LocalDateTime.now())
                .message(retorno.getMensagem())
                .data(retorno.getDados()).build());
    }
}
