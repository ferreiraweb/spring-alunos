package com.serpro.spring.sqlite.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="aluno")
public class Aluno implements Serializable {

    private static final Long serialVersionUID=1L;

    // properties --------------------------------
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nome;
    @Column(length = 30, nullable = false)
    private String sobrenome;
    @Email
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private int idade;
    @Column(nullable = true)
    private double peso;
    @Column(nullable = true)
    private double altura;

    /* construtors ---------------------------- */

    public Aluno(){}
    public Aluno(String nome, String sobrenome, String email, int idade, double peso, double altura) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
    }

    /* Getters/Setters ---------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    /* hasCode, equals e toString ---------------------------*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return idade == aluno.idade && Double.compare(peso, aluno.peso) == 0 && Double.compare(altura, aluno.altura) == 0 && Objects.equals(id, aluno.id) && Objects.equals(nome, aluno.nome) && Objects.equals(sobrenome, aluno.sobrenome) && Objects.equals(email, aluno.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, email, idade, peso, altura);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", idade=" + idade +
                ", peso=" + peso +
                ", altura=" + altura +
                '}';
    }
}
