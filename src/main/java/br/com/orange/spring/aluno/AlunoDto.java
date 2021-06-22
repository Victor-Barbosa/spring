package br.com.orange.spring.aluno;

import org.springframework.data.domain.Page;

public class AlunoDto {

    private String nome;
    private String email;

    public AlunoDto(Aluno aluno) {
        this.nome = aluno.getNome();
        this.email = aluno.getEmail();
    }

    public static Page<AlunoDto> converter(Page<Aluno> alunos) {
        return alunos.map(AlunoDto::new);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
