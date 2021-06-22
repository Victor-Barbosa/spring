package br.com.orange.spring.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaTodosAlunos", allEntries = true)
    public ResponseEntity<?> cadastraAluno(@RequestBody @Valid CadastraAlunoForm request){
        if (alunoRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe aluno com esse email cadastrado!");
        }
        Aluno aluno = request.toModel();
        alunoRepository.save(aluno);
        return ResponseEntity.status(201).body("Cliente cadastrado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> buscaAlunoPorId(@PathVariable Long id){
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado aluno para este Id!");
        }
        return ResponseEntity.ok(new AlunoDto(aluno.get()));
    }
    @GetMapping
    @Cacheable(value = "listaTodosAlunos")
    public Page<AlunoDto> listaTodosAlunos(@PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao){

        Page<Aluno> alunos = alunoRepository.findAll(paginacao);
        return AlunoDto.converter(alunos);

    }
}
