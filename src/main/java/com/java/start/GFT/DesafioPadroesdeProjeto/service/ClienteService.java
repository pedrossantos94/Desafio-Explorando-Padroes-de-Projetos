package com.java.start.GFT.DesafioPadroesdeProjeto.service;

import com.java.start.GFT.DesafioPadroesdeProjeto.model.Cliente;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();
    Cliente buscaPorId(Long id);
    void inserir(Cliente cliente);
    void atualizar(Long id, Cliente cliente);
    void remover(Long id);
}
