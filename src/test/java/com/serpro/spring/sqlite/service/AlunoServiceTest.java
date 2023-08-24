package com.serpro.spring.sqlite.service;

import com.serpro.spring.sqlite.domain.Aluno;
import com.serpro.spring.sqlite.dtos.AlunoDto;
import com.serpro.spring.sqlite.exceptions.AlunoEmailMustBeNonRepeatedException;
import com.serpro.spring.sqlite.exceptions.AlunoNotFoundException;
import com.serpro.spring.sqlite.repositories.IAlunoRepository;
import com.serpro.spring.sqlite.services.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    private IAlunoRepository repository;

    @InjectMocks
    private AlunoService service;


    Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno("Matheus", "Figueira",
                "matheusfigueira.br@gmail.com",
                25, 87.3, 1.84);
    }

    @Test
    void save_deve_salvar_um_aluno() {

        /* given */
        when(repository.save(aluno)).thenReturn(aluno);
        /* when */
        AlunoDto dto = service.save(AlunoDto.alunoToDto(aluno));

        /* then */
        assertNotNull(dto);
        assertEquals("Matheus", dto.nome());
    }

    @Test
    void save_deve_lancar_excecao_para_email_repetido() {

        /*given*/
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(aluno));
        /*when*/
        /*then*/
        assertThrows(
                AlunoEmailMustBeNonRepeatedException.class,
                () -> service.save(AlunoDto.alunoToDto(aluno)) );

        verify(repository, never()).save(any(Aluno.class));

    }

    @Test
    void findAll_deve_retornar_lista_de_alunos(){

        /* given */
        List<AlunoDto>  alunosDto = List.of(AlunoDto.alunoToDto(aluno));
        when(repository.findAll()).thenReturn(List.of(aluno));

        /* when */
        List<AlunoDto> dtos = service.findAll();

        /* then */
        assertEquals(1, dtos.size());
        assertArrayEquals(alunosDto.toArray(), dtos.toArray());
    }

    @Test
    void update_deve_atualizar_Aluno() {

        /* given */

        when(repository.findById(anyLong())).thenReturn(Optional.of(aluno));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(aluno));
        when(repository.save(any(Aluno.class))).thenReturn(aluno);

        /* when */

       AlunoDto updatedDto = service.update(anyLong(), AlunoDto.alunoToDto(aluno));

       /* then */

       assertNotNull(updatedDto);
       assertEquals(aluno.getNome(), updatedDto.nome());
    }

    @Test
    void update_deve_lancar_excecao_se_usuario_nao_existe() {

        /* given */
        when(repository.findById(anyLong()))
                .thenThrow(AlunoNotFoundException.class);

        /* when / then */
        assertThrows(
                AlunoNotFoundException.class,
                () -> service.update(anyLong(), AlunoDto.alunoToDto(aluno))
                );
    }

    /*@Test
    void delete_deve_remover_um_aluno() {
        service.delete(anyLong());
        verify(repository, times(1)).deleteById(1L);
    }*/

}
