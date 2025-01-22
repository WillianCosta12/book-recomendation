package com.imd.br.bookRecomendation.Controller;

import com.imd.br.bookRecomendation.Api.ImportadorProdutos;
import com.imd.br.bookRecomendation.Model.Livro;
import com.imd.br.bookRecomendation.Model.Produto;
import com.imd.br.bookRecomendation.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService<Livro> ls;
    @Autowired
    private ImportadorProdutos il;

    @GetMapping("/filtro")
    public List<Livro> filtrarProdutos(@RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Double avaliacaoMediaMin) {
        return ls.filtrarProdutos(titulo, autor, genero, avaliacaoMediaMin);
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return ls.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Livro> buscarPorId(@PathVariable Long id) {
        return ls.buscarPorId(id);
    }

    @PostMapping
    public Livro salvar(@RequestBody Livro produto) {
        return ls.salvar(produto);
    }

    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id, @RequestBody Livro produtoAtualizado) {
        return ls.atualizar(id, produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        ls.deletar(id);
    }

    @PostMapping("/importar")
    public ResponseEntity<String> importarProdutos() {
        il.importarProdutos("books");
        return ResponseEntity.ok("Importação de produtos iniciada!");
    }
}
