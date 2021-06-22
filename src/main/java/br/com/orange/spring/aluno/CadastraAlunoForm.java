package br.com.orange.spring.aluno;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CadastraAlunoForm {

    @NotBlank @Length(min = 3, max = 30)
    private String nome;
    @NotBlank @Length(min = 5, max = 30) @Email
    private String email;
    @NotNull
    @Range(min = 18, max = 120)
    private Integer idade;

    public CadastraAlunoForm(String nome, String email, Integer idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    public Aluno toModel() {
        return new Aluno(nome, email, idade);
    }

    public String getEmail() {
        return email;
    }
}
