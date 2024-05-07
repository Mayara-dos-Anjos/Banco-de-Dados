package com.example;
import javax.swing.*;
import java.sql.*;

public class Amizade {
    private int id;
    private int usuario_id1;
    private int usuario_id2;

    @Override
    public String toString() {
        return "Amizade{" + "id=" + id + ", usuario_id1=" + usuario_id1 + ", usuario_id2=" + usuario_id2 + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id1() {
        return usuario_id1;
    }

    public void setUsuario_id1(int usuario_id1) {
        this.usuario_id1 = usuario_id1;
    }

    public int getUsuario_id2() {
        return usuario_id2;
    }

    public void setUsuario_id2(int usuario_id2) {
        this.usuario_id2 = usuario_id2;
    }

    public void adicionarAmizade() {
        Connection connection = null;
        try {
            // Obter conexão com o banco de dados
            Conexao conexao = new Conexao();
            connection = conexao.obterConexao();

            // Consultar todos os usuários cadastrados
            String consultaUsuarios = "SELECT codigo, nome FROM usuario";
            PreparedStatement consultaStatement = connection.prepareStatement(consultaUsuarios);
            ResultSet resultSetUsuarios = consultaStatement.executeQuery();

            // Criar uma lista de usuários para exibir no JOptionPane
            DefaultListModel<String> listaUsuarios = new DefaultListModel<>();
            while (resultSetUsuarios.next()) {
                int idUsuario = resultSetUsuarios.getInt("codigo");
                String nomeUsuario = resultSetUsuarios.getString("nome");
                listaUsuarios.addElement(idUsuario + ": " + nomeUsuario);
            }

            // Exibir um JOptionPane para selecionar o primeiro usuário
            JList<String> lista1 = new JList<>(listaUsuarios);
            JOptionPane.showMessageDialog(null, lista1, "Selecione o primeiro usuário", JOptionPane.PLAIN_MESSAGE);

            // Obter o ID do primeiro usuário selecionado
            int indexSelecionado1 = lista1.getSelectedIndex();
            if (indexSelecionado1 != -1) { // Se um usuário foi selecionado
                int idUsuarioSelecionado1 = Integer.parseInt(listaUsuarios.get(indexSelecionado1).split(":")[0]);

                // Exibir um JOptionPane para selecionar o segundo usuário
                JList<String> lista2 = new JList<>(listaUsuarios);
                JOptionPane.showMessageDialog(null, lista2, "Selecione o segundo usuário", JOptionPane.PLAIN_MESSAGE);

                // Obter o ID do segundo usuário selecionado
                int indexSelecionado2 = lista2.getSelectedIndex();
                if (indexSelecionado2 != -1) { // Se um usuário foi selecionado
                    int idUsuarioSelecionado2 = Integer.parseInt(listaUsuarios.get(indexSelecionado2).split(":")[0]);

                    // Inserir dados na tabela de amizades
                    String insercao = "INSERT INTO amizade (usuario_1, usuario_2) VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(insercao);
                    statement.setInt(1, idUsuarioSelecionado1);
                    statement.setInt(2, idUsuarioSelecionado2);
                    int linhasAfetadas = statement.executeUpdate();

                    // Verificar se a inserção foi bem-sucedida
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Amizade adicionada com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao adicionar a amizade.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar a conexão
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void removerAmizade() {
        Connection connection = null;
try {
    // Obter conexão com o banco de dados
    Conexao conexao = new Conexao();
    connection = conexao.obterConexao();

    // Consultar todas as amizades cadastradas
    String consultaAmizades = "SELECT a.codigo, u1.nome as usuario1, u2.nome as usuario2 " +
                              "FROM amizade a " +
                              "JOIN usuario u1 ON a.usuario_1 = u1.codigo " +
                              "JOIN usuario u2 ON a.usuario_2 = u2.codigo";
    PreparedStatement consultaStatement = connection.prepareStatement(consultaAmizades);
    ResultSet resultSetAmizades = consultaStatement.executeQuery();

    // Criar uma lista de amizades para exibir no JOptionPane
    DefaultListModel<String> listaAmizades = new DefaultListModel<>();
    while (resultSetAmizades.next()) {
        int codigoAmizade = resultSetAmizades.getInt("codigo");
        String nomeUsuario1 = resultSetAmizades.getString("usuario1");
        String nomeUsuario2 = resultSetAmizades.getString("usuario2");
        listaAmizades.addElement(codigoAmizade + ": " + nomeUsuario1 + " e " + nomeUsuario2);
    }

    // Exibir um JOptionPane para selecionar a amizade a ser removida
    JList<String> lista = new JList<>(listaAmizades);
    JOptionPane.showMessageDialog(null, lista, "Selecione a amizade a ser removida", JOptionPane.PLAIN_MESSAGE);

    // Obter o código da amizade selecionada
    int indexSelecionado = lista.getSelectedIndex();
    if (indexSelecionado != -1) { // Se uma amizade foi selecionada
        int codigoAmizadeSelecionada = Integer.parseInt(listaAmizades.get(indexSelecionado).split(":")[0]);

        // Executar a remoção da amizade
        String remocao = "DELETE FROM amizade WHERE codigo=?";
        PreparedStatement statement = connection.prepareStatement(remocao);
        statement.setInt(1, codigoAmizadeSelecionada);
        int linhasAfetadas = statement.executeUpdate();

        // Verificar se a remoção foi bem-sucedida
        if (linhasAfetadas > 0) {
            JOptionPane.showMessageDialog(null, "Amizade removida com sucesso.");
        } else {
            JOptionPane.showMessageDialog(null, "Falha ao remover a amizade.");
        }
    }
} catch (SQLException e) {
    e.printStackTrace();
} finally {
    // Fechar a conexão
    if (connection != null) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    }
        public void listarAmizades() {
            String sql = "SELECT a.codigo, u1.nome as usuario1, u2.nome as usuario2 " +
                         "FROM amizade a " +
                         "JOIN usuario u1 ON a.usuario_1 = u1.codigo " +
                         "JOIN usuario u2 ON a.usuario_2 = u2.codigo";
            Conexao conn = new Conexao();
            StringBuilder tabela = new StringBuilder();
    
            try (Connection c = conn.obterConexao()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
    
                tabela.append("Codigo\tUsuario1\tUsuario2\n");
    
                while (rs.next()) {
                    int id = rs.getInt("codigo");
                    String usuario1 = rs.getString("usuario1");
                    String usuario2 = rs.getString("usuario2");
    
                    tabela.append(String.format("%d\t%s\t%s\n", id, usuario1, usuario2));
                }
    
                JOptionPane.showMessageDialog(null, new JTextArea(tabela.toString()), "Lista de Amizades", JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao listar amizades.");
            }
        }
    }

