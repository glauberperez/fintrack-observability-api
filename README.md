# 🏦 FinTrack API | Security & Observability POC

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-blue)
![Prometheus](https://img.shields.io/badge/Prometheus-Metrics-E6522C)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)

## 📌 Visão Geral
O **FinTrack API** é uma Prova de Conceito (POC) projetada para simular o backend de um sistema de transações financeiras. O foco principal deste projeto é demonstrar a implementação prática de padrões arquiteturais exigidos em ambientes corporativos de alta performance, especificamente **Segurança Stateless** e **Observabilidade**.

## 🏗️ Arquitetura e Tecnologias
* **Segurança (Autenticação e Autorização):** Implementação de Spring Security com filtros customizados para validação de **JSON Web Tokens (JWT)**.
* **Telemetria (Observabilidade):** Uso do **Micrometer** e **Spring Boot Actuator** para instrumentar o código e expor métricas de negócio.
* **Monitoramento Contínuo:** Servidor **Prometheus** containerizado na mesma rede da API para raspagem (scraping) de séries temporais.
* **Infraestrutura:** Multi-stage build no **Dockerfile** e orquestração via **Docker Compose**.

## 🚀 Como Executar Localmente

### Pré-requisitos
* Docker e Docker Compose instalados na máquina.

### Passos para subir a infraestrutura
1. Clone o repositório.
2. Na raiz do projeto, execute o comando para construir e iniciar os contêineres:
   ```bash
   docker compose up -d --build
   ```
3. A API estará disponível na porta `8080` e o painel do Prometheus na porta `9090`.

## 🔒 Testando o Fluxo de Negócio

**1. Gerar o Token de Acesso (Login)**
```bash
curl -X POST "http://localhost:8080/api/v1/auth/login?username=usuario_teste"
```

**2. Realizar uma Transação Segura**
*Copie o token gerado no passo anterior e substitua `<SEU_TOKEN>`.*
```bash
curl -X POST http://localhost:8080/api/v1/transactions \
     -H "Authorization: Bearer <SEU_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{
           "fromAccount": "12345",
           "toAccount": "98765",
           "amount": 250.00
         }'
```

## 📊 Visualizando as Métricas
Acesse o painel do Prometheus em `http://localhost:9090`. 
No campo de busca (Expression), você pode monitorar os contadores customizados do negócio:
* `fintrack_transactions_success_total`: Volume de transações concluídas.
* `fintrack_transactions_failed_total`: Volume de transações rejeitadas.

---
*Desenvolvido como estudo avançado de arquitetura backend e DevOps.*
