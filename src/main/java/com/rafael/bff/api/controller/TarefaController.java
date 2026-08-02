package com.rafael.bff.api.controller;

import com.rafael.bff.api.dto.BffFrontAgendamentoResponseDTO;
import com.rafael.bff.api.dto.FrontBffAgendamentoRequestDTO;
import com.rafael.bff.domain.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas (BFF Orquestrador)", description = "Endpoints de gerenciamento e agendamento de tarefas expostos ao Front-end, com segurança baseada em token JWT")
public class TarefaController {

    private final TarefaService tarefaService;

    // ====================== BUSCAR TAREFA POR ID ======================
    @GetMapping("/{tarefaId}")
    @Operation(
            summary = "Busca uma tarefa específica por ID",
            description = "Extrai a identidade do usuário logado via token JWT, traduz o e-mail em ID interno e busca a tarefa de forma blindada contra IDOR."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada e retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada ou não pertence ao usuário autenticado")
    })
    public ResponseEntity<BffFrontAgendamentoResponseDTO> buscarTarefaPorId(
            @PathVariable("tarefaId") String tarefaId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        BffFrontAgendamentoResponseDTO response = tarefaService.buscarTarefaPorId(tarefaId, email);
        return ResponseEntity.ok(response);
    }

    // ====================== LISTAR TODAS AS TAREFAS DO USUÁRIO ======================
    @GetMapping
    @Operation(
            summary = "Lista todas as tarefas do usuário autenticado",
            description = "Orquestra a busca de todos os agendamentos vinculados ao usuário dono do token JWT atual."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma tarefa encontrada para este usuário"),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido")
    })
    public ResponseEntity<List<BffFrontAgendamentoResponseDTO>> listarTarefas(JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        List<BffFrontAgendamentoResponseDTO> response = tarefaService.listarTarefas(email);
        return ResponseEntity.ok(response);
    }

    // ====================== CRIAR AGENDAMENTO ======================
    @PostMapping
    @Operation(
            summary = "Cria um novo agendamento",
            description = "Valida os dados de entrada (como data futura obrigatória), identifica o usuário pelo JWT e despacha a criação para o microsserviço de agendamento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos campos (Bean Validation) ou data no passado"),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido")
    })
    public ResponseEntity<BffFrontAgendamentoResponseDTO> criarTarefa(
            @Valid @RequestBody FrontBffAgendamentoRequestDTO request,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();

        BffFrontAgendamentoResponseDTO response = tarefaService.criarTarefa(request, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ==================== DELETAR AGENDAMENTO ====================
    @DeleteMapping("/{tarefaId}")
    @Operation(
            summary = "Remove um agendamento",
            description = "Orquestra a exclusão física ou cancelamento da tarefa, validando estritamente a posse do recurso pelo usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token JWT ausente ou inválido"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada ou não pertence ao usuário")
    })
    public ResponseEntity<Void> deletarTarefa(
            @PathVariable("tarefaId") String tarefaId,
            JwtAuthenticationToken token) {

        String email = token.getToken().getSubject();
        tarefaService.deletarTarefa(tarefaId, email);
        return ResponseEntity.noContent().build();
    }
}