# Locadora de Veículos — Atividade Avaliativa 2 (LPM)

Sistema em Java, orientado a objetos, para gerenciar os veículos e os contratos de aluguel de uma locadora de automóveis.

## Estrutura

```
src/
├── app/
│   ├── Main.java              # ponto de entrada + criação dos dados iniciais (3 veículos e 2 aluguéis)
│   ├── MenuLocadora.java      # menu e interação com o usuário
│   └── LeitorConsole.java     # leitura e validação da entrada do teclado
├── enums/
│   └── StatusVeiculo.java     # DISPONIVEL, ALUGADO, MANUTENCAO
├── interfaces/
│   ├── Avaliavel.java         # void avaliar(int nota)
│   └── Inspecionavel.java     # void realizarInspecao()
├── modelo/
│   ├── veiculos/
│   │   ├── Veiculo.java       # classe abstrata
│   │   ├── Carro.java         # portas | Avaliavel
│   │   ├── Moto.java          # cilindrada | Inspecionavel
│   │   └── Van.java           # passageiros | Avaliavel e Inspecionavel
│   ├── alugueis/
│   │   ├── Aluguel.java       # classe abstrata (possui vários veículos)
│   │   ├── AluguelBasico.java     # até 100 km/dia, sem seguro
│   │   ├── AluguelPremium.java    # km ilimitado, com seguro
│   │   └── AluguelExecutivo.java  # km ilimitado, motorista particular | Avaliavel
│   └── registros/
│       ├── RegistroAvaliacoes.java  # notas e média (composição)
│       └── RegistroInspecoes.java   # histórico de inspeções (composição)
└── sistema/
    └── Locadora.java          # regras de gerenciamento de veículos e aluguéis
```

## Funcionalidades (menu)

1. Associar veículos a um aluguel
2. Exibir todos os veículos cadastrados
3. Exibir todos os aluguéis cadastrados
4. Exibir apenas veículos disponíveis
5. Avaliar objetos que implementam `Avaliavel` (Carro, Van e Aluguel Executivo)
6. Realizar inspeção em veículos que implementam `Inspecionavel` (Moto e Van)
7. Remover veículos em manutenção do sistema

## Regras adotadas

- Só é possível associar a um aluguel um veículo **disponível**; ao ser associado, ele passa a **alugado**.
- As notas de avaliação vão de **1 a 5**.
- Na inspeção, se o veículo rodou mais que o intervalo de revisão desde a última inspeção
  (Moto: 5.000 km, Van: 10.000 km), ele vai para **manutenção**; caso contrário, fica **disponível**.
  Um veículo alugado não pode ser inspecionado.
- Valor total do aluguel = soma das diárias dos veículos × quantidade de dias.

## Como executar

Na pasta `LocadoraVeiculos`:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out app.Main
```

No Windows (PowerShell):

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out app.Main
```

Ou abra a pasta em uma IDE (VS Code, IntelliJ, Eclipse) com `src` como pasta de código-fonte e execute `app.Main`.
