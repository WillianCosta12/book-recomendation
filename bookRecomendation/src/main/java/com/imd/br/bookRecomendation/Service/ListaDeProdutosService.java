package com.imd.br.bookRecomendation.Service;

import java.util.Set;

import com.imd.br.bookRecomendation.Model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imd.br.bookRecomendation.Model.ListaDeProdutos;
import com.imd.br.bookRecomendation.Model.Produto;
import com.imd.br.bookRecomendation.Repository.ListaDeProdutosRepository;

@Service
public class ListaDeProdutosService {

    @Autowired
    private ListaDeProdutosRepository listaDeProdutosRepository;

    public void adicionarProduto(Long idLista, Long idProduto) {
        ListaDeProdutos listaDeProdutos = listaDeProdutosRepository.findById(idLista).get();
        Set<Produto> produtos = listaDeProdutos.getProdutos();
        Produto produto = new Livro();
        produto.setId(idProduto);
        produtos.add(produto);
        listaDeProdutos.setProdutos(produtos);
        listaDeProdutosRepository.save(listaDeProdutos);
    }

    public void removerProduto(Long idLista, Long idProduto) {
        ListaDeProdutos listaDeProdutos = listaDeProdutosRepository.findById(idLista).get();
        listaDeProdutos.getProdutos().removeIf(produto -> produto.getId().equals(idProduto));
        listaDeProdutosRepository.save(listaDeProdutos);
    }
}
