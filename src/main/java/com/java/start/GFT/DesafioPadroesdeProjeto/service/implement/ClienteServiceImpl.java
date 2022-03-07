package com.java.start.GFT.DesafioPadroesdeProjeto.service.implement;


import com.java.start.GFT.DesafioPadroesdeProjeto.model.Cliente;
import com.java.start.GFT.DesafioPadroesdeProjeto.model.ClienteRepository;
import com.java.start.GFT.DesafioPadroesdeProjeto.model.Endereco;
import com.java.start.GFT.DesafioPadroesdeProjeto.model.EnderecoRepository;
import com.java.start.GFT.DesafioPadroesdeProjeto.service.ClienteService;
import com.java.start.GFT.DesafioPadroesdeProjeto.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscaPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteCep(cliente);
    }

    private void salvarClienteCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteTemp = clienteRepository.findById(id);
        if(clienteTemp.isPresent()){
            salvarClienteCep(cliente);
        }
    }

    @Override
    public void remover(Long id) {
        clienteRepository.deleteById(id);
    }
}
