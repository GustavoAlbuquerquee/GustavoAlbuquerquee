import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Interação com o usuário: exibe o menu e encaminha cada opção para a Academia.
 */
public class Menu {

    private final Academia academia;
    private final Scanner entrada;

    public Menu(Academia academia, Scanner entrada) {
        this.academia = academia;
        this.entrada = entrada;
    }

    public void executar() {
        try {
            int opcao;
            do {
                exibirOpcoes();
                opcao = lerInteiro("Escolha uma opção: ");
                System.out.println();
                executarOpcao(opcao);
            } while (opcao != 0);
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Entrada encerrada. Saindo do sistema.");
        }
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("============== ACADEMIA - MENU ==============");
        System.out.println("1 - Associar atividades a um plano");
        System.out.println("2 - Exibir todas as atividades cadastradas");
        System.out.println("3 - Exibir todos os planos cadastrados");
        System.out.println("4 - Exibir apenas planos ativos");
        System.out.println("5 - Emitir certificado");
        System.out.println("6 - Registrar presença em atividade");
        System.out.println("7 - Remover atividades inativas");
        System.out.println("8 - Registrar acesso diário de cliente (plano)");
        System.out.println("9 - Ativar/Inativar atividade ou plano");
        System.out.println("0 - Sair");
        System.out.println("=============================================");
    }

    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                associarAtividadesAPlano();
                break;
            case 2:
                exibirLista("ATIVIDADES CADASTRADAS", academia.getAtividades(), "Nenhuma atividade cadastrada.");
                break;
            case 3:
                exibirLista("PLANOS CADASTRADOS", academia.getPlanos(), "Nenhum plano cadastrado.");
                break;
            case 4:
                exibirLista("PLANOS ATIVOS", academia.getPlanosAtivos(), "Nenhum plano ativo.");
                break;
            case 5:
                emitirCertificado();
                break;
            case 6:
                registrarPresenca(academia.getAtividadesComControlePresenca(),
                        "Atividades que registram presença:", "Nome do participante: ",
                        "Presença registrada com sucesso.");
                break;
            case 7:
                removerAtividadesInativas();
                break;
            case 8:
                registrarPresenca(academia.getPlanosComControlePresenca(),
                        "Planos com controle de frequência:", "Nome do cliente: ",
                        "Acesso registrado com sucesso.");
                break;
            case 9:
                alterarStatus();
                break;
            case 0:
                System.out.println("Encerrando o sistema. Até logo!");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    // ---------------------------------------------------------------- opções

    private void associarAtividadesAPlano() {
        Plano plano = escolherItem("Planos cadastrados:", academia.getPlanos());
        if (plano == null) {
            return;
        }
        boolean continuar = true;
        while (continuar) {
            System.out.println();
            Atividade atividade = escolherItem("Atividades cadastradas:", academia.getAtividades());
            if (atividade == null) {
                return;
            }
            try {
                plano.associarAtividade(atividade);
                System.out.println("Atividade \"" + atividade.getNome() + "\" associada ao plano \""
                        + plano.getNome() + "\".");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            continuar = confirmar("Deseja associar outra atividade a este plano?");
        }
    }

    private void emitirCertificado() {
        Certificavel certificavel = escolherItem("Itens que emitem certificado:", academia.getCertificaveis());
        if (certificavel == null) {
            return;
        }
        String nome = lerTexto("Nome do participante: ");
        System.out.println();
        try {
            certificavel.emitirCertificado(nome);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void registrarPresenca(List<ControladorPresenca> controladores, String titulo,
                                   String mensagemNome, String mensagemSucesso) {
        ControladorPresenca controlador = escolherItem(titulo, controladores);
        if (controlador == null) {
            return;
        }
        String nome = lerTexto(mensagemNome);
        try {
            controlador.registrarPresenca(nome);
            System.out.println(mensagemSucesso);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerAtividadesInativas() {
        List<Atividade> removidas = academia.removerAtividadesInativas();
        if (removidas.isEmpty()) {
            System.out.println("Nenhuma atividade inativa encontrada.");
            return;
        }
        System.out.println(removidas.size() + " atividade(s) inativa(s) removida(s):");
        for (Atividade atividade : removidas) {
            System.out.println(" - " + atividade.getNome());
        }
    }

    private void alterarStatus() {
        System.out.println("1 - Atividade");
        System.out.println("2 - Plano");
        int tipo = lerInteiro("Alterar status de: ");
        if (tipo == 1) {
            Atividade atividade = escolherItem("Atividades cadastradas:", academia.getAtividades());
            if (atividade != null) {
                if (atividade.isAtiva()) {
                    atividade.inativar();
                } else {
                    atividade.ativar();
                }
                System.out.println("Atividade \"" + atividade.getNome() + "\" agora está: "
                        + atividade.getStatus().getDescricao());
            }
        } else if (tipo == 2) {
            Plano plano = escolherItem("Planos cadastrados:", academia.getPlanos());
            if (plano != null) {
                if (plano.isAtivo()) {
                    plano.inativar();
                } else {
                    plano.ativar();
                }
                System.out.println("Plano \"" + plano.getNome() + "\" agora está: "
                        + plano.getStatus().getDescricao());
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // ------------------------------------------------------------- auxiliares

    private void exibirLista(String titulo, List<?> itens, String mensagemVazia) {
        System.out.println("---------- " + titulo + " ----------");
        if (itens.isEmpty()) {
            System.out.println(mensagemVazia);
            return;
        }
        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + ") " + itens.get(i));
        }
    }

    /** Exibe a lista numerada e devolve o item escolhido, ou null se cancelar/lista vazia. */
    private <T> T escolherItem(String titulo, List<T> itens) {
        if (itens.isEmpty()) {
            System.out.println("Nenhum item disponível para esta operação.");
            return null;
        }
        System.out.println(titulo);
        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + ") " + itens.get(i));
        }
        while (true) {
            int indice = lerInteiro("Informe o número (0 para cancelar): ");
            if (indice == 0) {
                return null;
            }
            if (indice >= 1 && indice <= itens.size()) {
                return itens.get(indice - 1);
            }
            System.out.println("Número fora da lista. Tente novamente.");
        }
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = entrada.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = entrada.nextLine().trim();
            if (!linha.isEmpty()) {
                return linha;
            }
            System.out.println("O valor não pode ser vazio.");
        }
    }

    private boolean confirmar(String mensagem) {
        while (true) {
            String resposta = lerTexto(mensagem + " (S/N): ");
            if (resposta.equalsIgnoreCase("S")) {
                return true;
            }
            if (resposta.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Responda com S ou N.");
        }
    }
}
