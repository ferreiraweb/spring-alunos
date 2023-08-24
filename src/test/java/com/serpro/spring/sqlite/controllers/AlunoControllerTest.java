package com.serpro.spring.sqlite.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.serpro.spring.sqlite.domain.Aluno;
import com.serpro.spring.sqlite.dtos.AlunoDto;
import com.serpro.spring.sqlite.services.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class AlunoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AlunoService service;

    private Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno("Matheus", "Figueira",
                "matheusfigueira.br@gmail.com",
                25, 87.3, 1.84);
    }

    @Test
    void save_deve_salvar_um_aluno() throws Exception {

        /* given */
        when(service.save(any(AlunoDto.class))).thenReturn(AlunoDto.alunoToDto(aluno));

        /* when */

        ResultActions response = mvc.perform(MockMvcRequestBuilders
                .post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AlunoDto.alunoToDto(aluno)))
        );

        /* then */
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(aluno.getNome()));
    }

    @Test
    void findAll_deve_retornar_todos_alunos() throws Exception {

        /* given */
        List<AlunoDto> dtos = List.of(AlunoDto.alunoToDto(aluno));
        when(service.findAll()).thenReturn(dtos);

        /* when */
        ResultActions result = mvc.perform(MockMvcRequestBuilders
                .get("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
        );

        /* then */

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(dtos.size()));
    }

    @Test
    void findById_deve_retorna_um_aluno_pelo_id() throws Exception {

        /* given */
        AlunoDto dto = AlunoDto.alunoToDto(aluno);
        when(service.findById(anyLong())).thenReturn(dto);

        /* when */
        ResultActions response = mvc.perform(MockMvcRequestBuilders
                .get("/api/alunos/{id}", anyLong() ));

        /* then */

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(dto.nome()));
    }

    @Test
    void update_deve_atualiazar_aluno() throws Exception {

        /* given */
        AlunoDto dto = AlunoDto.alunoToDto(aluno);
        when(service.update(anyLong(), dto)).thenReturn(dto);

        /* when */
        ResultActions response = mvc.perform(MockMvcRequestBuilders
                .put("/api/livros/{id}", anyLong())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
        );

        /* then */

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$nome").value(dto.nome()));

    }


}
