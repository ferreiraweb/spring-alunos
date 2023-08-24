package com.serpro.spring.sqlite.services;

import com.serpro.spring.sqlite.domain.Aluno;
import com.serpro.spring.sqlite.dtos.AlunoDto;
import com.serpro.spring.sqlite.exceptions.AlunoEmailMustBeNonRepeatedException;
import com.serpro.spring.sqlite.exceptions.AlunoNotFoundException;
import com.serpro.spring.sqlite.repositories.IAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private IAlunoRepository repository;

    public AlunoDto save(AlunoDto dto) {

        Optional<Aluno> savedAluno = repository.findByEmail(dto.email());

        if (savedAluno.isPresent()) {
            throw new AlunoEmailMustBeNonRepeatedException("Email informado já existe");
        }

        Aluno aluno = repository.save(AlunoDto.dtoToAluno(dto));
        return AlunoDto.alunoToDto(aluno);
    }

    public List<AlunoDto> findAll() {
        return this.repository.findAll()
                .stream()
                .map(aluno -> AlunoDto.alunoToDto(aluno))
                .toList();
    }

    public AlunoDto findById(Long id) throws AlunoNotFoundException {

        Aluno aluno = repository.findById(id).orElseThrow(() ->
                new AlunoNotFoundException("Aluno não encontrado")
                );

        return AlunoDto.alunoToDto(aluno);
    }


    public AlunoDto update(Long id, AlunoDto dto) throws AlunoNotFoundException, AlunoEmailMustBeNonRepeatedException  {

        Aluno aluno = repository.findById(id).orElseThrow(() ->
          new AlunoNotFoundException("Aluno não econtrado")
        );

       Optional<Aluno> alunoByEmail = repository.findByEmail(dto.email());

        if (alunoByEmail.isPresent()) {
            if (aluno.getId() != alunoByEmail.get().getId()) {
              throw  new AlunoEmailMustBeNonRepeatedException("Email já cadastrado para outro usuário");
            }
        }

        aluno.setNome(dto.nome());
        aluno.setSobrenome(dto.sobrenome());
        aluno.setEmail(dto.email());
        aluno.setIdade(dto.idade());
        aluno.setPeso(dto.peso());
        aluno.setAltura(dto.altura());

       Aluno updatedAluno = repository.save(aluno);

        return AlunoDto.alunoToDto(updatedAluno);
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }


}
