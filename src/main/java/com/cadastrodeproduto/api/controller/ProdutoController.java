package com.cadastrodeproduto.api.controller;

import com.cadastrodeproduto.api.model.Produto;
import com.cadastrodeproduto.api.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repositorio;

    public ProdutoController(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Produto> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return localizar(id);
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@Valid @RequestBody Produto produto) {
        produto.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(produto));
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody Produto dados) {
        Produto produto = localizar(id);
        produto.setNome(dados.getNome());
        produto.setPreco(dados.getPreco());
        return repositorio.save(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado");
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Produto localizar(Long id) {
        return repositorio.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado")
        );
    }
}
