package com.serpro.spring.sqlite.controllers;

import com.serpro.spring.sqlite.dtos.AlunoDto;
import com.serpro.spring.sqlite.exceptions.CustomResponseMessage;
import com.serpro.spring.sqlite.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AlunoDto dto, Errors validations) {

        if (validations.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getCustomResponseMessage(validations, HttpStatus.BAD_REQUEST.toString()));
        }

        AlunoDto newAlunoDto = service.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newAlunoDto);
    }


    @GetMapping
    public ResponseEntity<?> findAll() {

        List<AlunoDto> dtos = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){

        AlunoDto dto = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid AlunoDto dto,
            Errors validations
    ){

        if (validations.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getCustomResponseMessage(validations,
                            HttpStatus.BAD_REQUEST.toString()));
        }

        AlunoDto updatedDto = service.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        this.service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    /* --------------------------------------------- */

    private CustomResponseMessage getCustomResponseMessage(Errors validations, String status) {

        String[] allErrors = validations.getAllErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toArray(String[]::new);

        return new CustomResponseMessage(
                status,
                new Date(),
                "Erros de validação",
                allErrors
        );
    }


}
