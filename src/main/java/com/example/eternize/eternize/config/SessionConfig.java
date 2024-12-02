package com.example.eternize.eternize.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.eternize.eternize.repository.ItemCarrinhoRepository;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class SessionConfig implements HttpSessionListener {
	
	@Autowired
	ItemCarrinhoRepository itemCarrinhoRepository;
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		String jsessionId = event.getSession().getId();
        // Exclua os ItemCarrinho com o JSESSIONID correspondente
        itemCarrinhoRepository.deleteByJsessionId(jsessionId); // Verificar caso se não tiver ItemCarrinhos
	}
}
