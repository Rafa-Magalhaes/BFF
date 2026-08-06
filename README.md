# 🚪 BFF - Backend For Frontend (Orquestrador)

## 📌 Visão Geral
Microsserviço de borda (*Edge Service*) que atua como ponto único de entrada para os clientes (Frontend/Mobile). Realiza a orquestração segura entre os domínios, gerenciamento de comunicação inter-serviços via OpenFeign e aplicação da arquitetura de segurança *Zero Trust*.

## 🛠️ Stack Tecnológico
* **Java 21** | **Spring Boot 3.4.x**
* **Spring Cloud OpenFeign**
* **Spring Security & JWT**
* **Springdoc OpenAPI (Swagger UI)**

## 🚀 Como Executar Localmente
1. Certifique-se de que as Core APIs (Usuário, Agendador e Notificação) estejam ativas.
2. Execute o BFF:
   ```bash
   ./gradlew bootRun
   ```

## 🔌 Documentação (Swagger)
Com a aplicação rodando na porta `8082`, acesse a documentação interativa:
🔗 [Swagger UI - BFF Orchestrator](http://localhost:8082/swagger-ui/index.html)