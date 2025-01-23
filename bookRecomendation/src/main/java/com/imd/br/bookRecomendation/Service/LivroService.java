package com.imd.br.bookRecomendation.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imd.br.bookRecomendation.Model.Livro;
import com.imd.br.bookRecomendation.Model.Produto;
import com.imd.br.bookRecomendation.Repository.LivroRepository;
import com.imd.br.bookRecomendation.Repository.ProdutoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService implements ProdutoService<Livro> {

    @Autowired
    private LivroRepository lr;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Livro> listarTodos() {
        return lr.findAll();
    }

    @Override
    public Optional<Livro> buscarPorId(Long id) {
        return lr.findById(id);
    }

    public Livro salvar(Livro produto) {
        return lr.save(produto);
    }

    public Livro atualizar(Long id, Livro produtoAtualizado) {
        Livro produtoExistente = lr.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setTitulo(produtoAtualizado.getTitulo());
        produtoExistente.setGenero(produtoAtualizado.getGenero());
        produtoExistente.setSinopse(produtoAtualizado.getSinopse());
        produtoExistente.setAvaliacaoMedia(produtoAtualizado.getAvaliacaoMedia());

        return lr.save(produtoExistente);
    }

    public void deletar(Long id) {
        lr.deleteById(id);
    }

    public void salvarProdutos(String jsonResponse) throws Exception {
        JsonNode root = objectMapper.readTree(jsonResponse);
        JsonNode docs = root.path("docs");

        for (JsonNode doc : docs) {
            Livro produto = new Livro();
            int coverId = doc.path("cover_i").asInt();
            produto.setTitulo(doc.path("title").asText());
            produto.setAvaliacaoMedia(doc.path("ratings_average").asDouble());
            produto.setGenero(doc.path("subject").isArray() ? doc.path("subject").get(0).asText() : "Desconhecido");
            produto.setSinopse(doc.path("description").asText());
            produto.setLinkImage("https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg");
            produto.setAutor(doc.path("authors").isArray() ? doc.path("authors").get(0).asText() : "Desconhecido");
            produto.setNumeroPaginas(doc.path("number_of_pages").asInt());

            lr.save(produto);
        }
    }

    public List<Livro> filtrarProdutos(String titulo, String autor, String genero, Double avaliacaoMediaMin) {
        Specification<Produto> spec = Specification.where(ProdutoSpecification.filtroPorTitulo(titulo))
                .and(ProdutoSpecification.filtroPorAutor(autor))
                .and(ProdutoSpecification.filtroPorGenero(genero))
                .and(ProdutoSpecification.filtroPorAvaliacaoMedia(avaliacaoMediaMin));

        return lr.findAll((Sort) spec);
    }
}
