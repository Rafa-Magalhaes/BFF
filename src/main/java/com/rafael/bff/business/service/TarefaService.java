package com.rafael.bff.business.service;

import com.rafael.bff.business.client.TarefaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaClient tarefaClient;

    public void buscarTarefasPendentes(){
        tarefaClient.buscarTarefasPendentes();
    }
}



