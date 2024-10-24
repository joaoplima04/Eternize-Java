package com.example.eternize.eternize.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eternize.eternize.model.Categoria;
import com.example.eternize.eternize.model.Produto;
import com.example.eternize.eternize.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Método para listar todos os produtos
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    // Método para buscar um produto pelo ID
    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    // Método para salvar um novo produto ou atualizar um existente
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    public List<Produto> getProdutosByCategoria(String categoriaNome) {
        // Converte String categoriaNome para o enum corresopondente
    	Categoria categoria;
        try {
            categoria = Categoria.valueOf(categoriaNome);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Categoria inválida: " + categoriaNome);
        }
        return produtoRepository.findByCategoria(categoria);
    }
    
 // Método para atualizar um produto existente através do ID
    public Produto atualizarProduto(Long id, Produto novoProduto) {
        // Verifica se o produto com o ID especificado existe
        return produtoRepository.findById(id).map(produtoExistente -> {
            // Atualiza os campos do produto existente com os valores do novo produto
            produtoExistente.setNome(novoProduto.getNome());
            produtoExistente.setCategoria(novoProduto.getCategoria());
            produtoExistente.setPreco(novoProduto.getPreco());
            produtoExistente.setQuantidadeEstoque(novoProduto.getQuantidadeEstoque());
            produtoExistente.setImagem(novoProduto.getImagem());
            produtoExistente.setCor(novoProduto.getCor());
            produtoExistente.setEstilo(novoProduto.getEstilo());
            produtoExistente.setPublicado(novoProduto.getPublicado());
            
            // Salva as alterações no banco de dados
            return produtoRepository.save(produtoExistente);
        }).orElse(null); // Retorna null se o produto não for encontrado
    }
    
    // Método para deletar um produto pelo ID
    public boolean deleteById(Long id) {
    	boolean deletado = false;
        produtoRepository.deleteById(id);
        if (this.findById(id) == null) {
        	deletado = true;
        }
        return deletado;
    }
    
}