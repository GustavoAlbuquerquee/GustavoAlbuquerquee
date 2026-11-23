# Atividade Prática 2 – Sistema de Academia (Java / POO)

## Como executar

```bash
cd academia/src
javac -encoding UTF-8 *.java
java Main
```

## Estrutura

| Arquivo | Papel |
|---|---|
| `Atividade` (abstrata) | nome, horário, duração, máx. participantes, tipo e status |
| `Musculacao` | + quantidade de aparelhos |
| `Natacao` | + profundidade da piscina; implementa `Certificavel` e `ControladorPresenca` |
| `Spinning` | + quantidade de bicicletas; implementa `ControladorPresenca` |
| `Plano` (abstrata) | nome, valor, status e lista de atividades associadas |
| `PlanoBasico` | só atividades individuais, 1 acesso/dia; implementa `ControladorPresenca` |
| `PlanoPremium` | qualquer atividade, acesso ilimitado (sem controle de frequência) |
| `Certificavel`, `ControladorPresenca` | interfaces pedidas no enunciado |
| `ControleFrequencia` | presença em aula, usada por composição em `Natacao` e `Spinning` |
| `Status`, `TipoAtividade` | enums (ativo/inativo, individual/coletiva) |
| `Academia` | gerencia as listas e as regras (filtros, remoção de inativas) |
| `Menu` | interação com o usuário |
| `Main` | `main` + `carregarDadosIniciais` (3 atividades e 2 planos) |
| `Validacao` | validações reaproveitadas |

## Decisões de POO

- **Herança + polimorfismo:** cada plano decide se aceita uma atividade (`permiteAtividade`),
  e cada atividade monta seus próprios detalhes (`getDetalhesEspecificos`, `getModalidade`).
- **Interfaces transversais:** `ControladorPresenca` é implementada por classes de hierarquias
  diferentes (atividades e plano básico), cada uma com sua regra:
  - atividade: registra presença na aula, respeitando o máximo de participantes;
  - plano básico: registra o acesso diário e bloqueia o 2º acesso no mesmo dia.
- **Encapsulamento:** atributos `private`, listas expostas como somente leitura.
- **Separação de responsabilidades:** regras ficam em `Academia` e nas classes de domínio,
  a entrada e saída ficam em `Menu`.

## Menu

1–7 são as funcionalidades do enunciado. Opções extras:
- **8**: registra o acesso diário em planos com controle de frequência (plano básico);
- **9**: ativa ou inativa uma atividade ou plano, para testar as opções 4 e 7.

> As classes `Atividade`, `Plano` e os enums seriam fornecidas pelo professor e não vieram
> no PDF; aqui elas foram escritas de forma compatível. Se as do professor tiverem nomes de
> atributos ou construtores diferentes, ajuste só as chamadas `super(...)` e os getters usados.
