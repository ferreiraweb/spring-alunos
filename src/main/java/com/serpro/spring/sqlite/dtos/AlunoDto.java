package com.serpro.spring.sqlite.dtos;

import com.serpro.spring.sqlite.domain.Aluno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

public record AlunoDto(
        Optional<Long> id,
        @NotNull(message = "O nome deve ser informado")
        @Size(min = 5, max = 30, message = "O nome deve ter entre 5 e 30 caracteres")
        String nome,
        @NotNull(message = "O sobrenome deve ser informado")
        @Size(min = 5, max = 30, message = "O sobrenome deve ter entre 5 e 30 caracteres")
        String sobrenome,
        @NotNull(message = "O email deve ser informado")
        @Email(message = "e-mail invalido")
        String email,
        int idade,
        double peso,
        double altura
) {

        public static AlunoDto alunoToDto(Aluno aluno) {

                AlunoDto dto = new AlunoDto(
                        Optional.ofNullable(aluno.getId()),
                        aluno.getNome(),
                        aluno.getSobrenome(),
                        aluno.getEmail(),
                        aluno.getIdade(),
                        aluno.getPeso(),
                        aluno.getAltura()
                );

                return dto;
        }

        public static Aluno dtoToAluno(AlunoDto dto) {
                Aluno aluno = new Aluno();
                BeanUtils.copyProperties(dto, aluno);
                return aluno;
        }

}
