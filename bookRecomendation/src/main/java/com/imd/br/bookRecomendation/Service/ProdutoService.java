package com.imd.br.bookRecomendation.Service;

import com.imd.br.bookRecomendation.Model.Produto;

import java.util.List;
import java.util.Optional;
public interface ProdutoService<T extends Produto> {

    public List<T> listarTodos();
    public Optional<T> buscarPorId(Long id);

    T salvar(T produto);

    public T atualizar(Long id, T produtoAtualizado);

    public void deletar(Long id);
    public void salvarProdutos(String jsonResponse) throws Exception;

    public List<T> filtrarProdutos(String titulo, String autor, String genero, Double avaliacaoMediaMin);
}
