package com.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Menu principal
        int opcaoPrincipal;
        do{
        String[] opcoesPrincipais = {"Usuario", "Postagem","Amizade","Comentario", "Sair"};
        opcaoPrincipal = exibirMenu("Selecione uma opção:", opcoesPrincipais);

        switch (opcaoPrincipal) {
            case 0: // Usuario
                menuUsuario();
                break;
            case 1: // Postagem
                menuPostagem();
                break;
            case 2: // Amizade
                menuAmizade();
                break;
            case 3: //Comentario
                menuComentario();
                break;
            case 4: // Sair
                System.out.println("Saindo do programa...");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
            }
        }while(opcaoPrincipal!= 4);
    }

    private static void menuUsuario() {
        String[] opcoesUsuario = {"Adicionar", "Atualizar", "Listar", "Apagar", "Voltar"};
        int opcaoUsuario = exibirMenu("Selecione uma opção de Usuario:", opcoesUsuario);

        switch (opcaoUsuario) {
            case 0: // Adicionar
                String nome = JOptionPane.showInputDialog("Nome?");
                String nascimento = JOptionPane.showInputDialog("Data de nascimento?");
                String email = JOptionPane.showInputDialog("Email?");
                String senha = JOptionPane.showInputDialog("Senha?");
                Usuario u = new Usuario();
                u.setNome(nome);
                u.setNascimento(nascimento);
                u.setEmail(email);
                u.setSenha(senha);
                u.cadastrarUsuario();
                break;
            case 1: // Atualizar
                Usuario u1 = new Usuario();
                u1.atualizarUsuario();
                break;
            case 2: // Listar
                Usuario u2 = new Usuario();
                u2.listarUsuario();
                break;
            case 3: // Apagar
                Usuario u3 = new Usuario();
                u3.deletarUsuario();
                break;
            case 4: // Voltar
                main(null); 
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    private static void menuPostagem() {
        String[] opcoesPostagem = {"Adicionar", "Atualizar", "Listar", "Apagar", "Voltar"};
        int opcaoPostagem = exibirMenu("Selecione uma opção de Postagem:", opcoesPostagem);

        switch (opcaoPostagem) {
            case 0: // Cadastrar
                String legenda = JOptionPane.showInputDialog("Legenda:");
                String comentario = JOptionPane.showInputDialog("Comentario:");
                boolean curtir = Boolean.parseBoolean(JOptionPane.showInputDialog("Curtir?"));
                boolean salvar = Boolean.parseBoolean(JOptionPane.showInputDialog("Salvar?"));
                Postagem p = new Postagem();
                p.setLegenda(legenda);
                p.setComentario(comentario);
                p.setCurtir(curtir);
                p.setSalvar(salvar);
                p.adicionarPostagem();
                break;
            case 1: // Atualizar
            Postagem p1 = new Postagem();
            p1.atualizarPostagem();
                break;
            case 2: // Listar
                Postagem p2 = new Postagem();
                p2.listarPostagens();
                break;
            case 3: // Deletar
                Postagem p3 = new Postagem();;
                p3.deletarPostagem();
                break;
            case 4: // Voltar
                Main.main(null); 
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }
    private static void menuAmizade() {
        String[] opcoesAmizade = {"Adicionar", "Listar", "Apagar", "Voltar"};
        int opcaoAmizade = exibirMenu("Selecione uma opção de Postagem:", opcoesAmizade);
    
    switch (opcaoAmizade){
        case 0:
        Amizade a = new Amizade();
        a.adicionarAmizade();
        break;
        case 1:
        Amizade a2 = new Amizade();
        a2.listarAmizades();
        break;
        case 2:
        Amizade a3 = new Amizade();
        a3.removerAmizade();
        break;
        case 3: // Voltar
        Main.main(null); 
        break;
    default:
        System.out.println("Opção inválida.");
        break;
    }
}

    private static int exibirMenu(String mensagem, String[] opcoes) {
        return JOptionPane.showOptionDialog(null, mensagem, "Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
    }
    private static void menuComentario() {
        
        String[] opcoesComentario = {"Adicionar", "Atualizar", "Listar", "Apagar", "Voltar"};
        int opcaoComentario = exibirMenu("Selecione uma opção de Comentario:", opcoesComentario);

        switch (opcaoComentario) {
            case 0:
                Comentario com = new Comentario();
                com.adicionarComentario();
            break;
            case 1:
            Comentario com1 = new Comentario();
            com1.atualizarComentario();
            break;
            case 2:
            Comentario com2 = new Comentario();
            com2.listarComentarios();
            break;
            case 3:
            Comentario com3 = new Comentario();
            com3.apagarComentario();
            break;
            case 4: // Voltar
            Main.main(null);
            break;
            default:
            System.out.println("Opção inválida.");
            break;
        }
    }
}

