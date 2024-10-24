package com.example.eternize.eternize;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.eternize.eternize.model.Categoria;
import com.example.eternize.eternize.model.Estilo;
import com.example.eternize.eternize.model.Produto;
import com.example.eternize.eternize.repository.ProdutoRepository;

@DataJpaTest
public class ProdutoRepositoryTest {
    
	@Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testSaveProduto() {
        Produto produto = new Produto("Prato Raso Flowers Neomint", Categoria.PRATO_RASO, 5.00, 20, 
        		"src/main/resources/images/pratoraso_flowers.png", "Verde", Estilo.FLORIDO, true);
        
        Produto savedProduto = produtoRepository.save(produto);
        assertThat(savedProduto).isNotNull();
    }

}
