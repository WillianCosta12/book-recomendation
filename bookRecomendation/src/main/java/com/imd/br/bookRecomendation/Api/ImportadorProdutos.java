package com.imd.br.bookRecomendation.Api;

import com.imd.br.bookRecomendation.Service.LivroService;
import com.imd.br.bookRecomendation.Service.ProdutoService;
import com.imd.br.bookRecomendation.Service.OpenLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportadorProdutos {

    @Autowired
    private OpenLibraryService openLibraryService;
    @Autowired
    private LivroService ls;

    public void importarProdutos(String query) {
        int page = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            String response = openLibraryService.buscarProdutos(query, page);

            try {
                ls.salvarProdutos(response);
                page++;
            } catch (Exception e) {
                e.printStackTrace();
                hasMoreData = false; // Para em caso de erro
            }
        }
    }

}
