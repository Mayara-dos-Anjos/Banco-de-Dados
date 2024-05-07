package com.example;
import javax.swing.*;
import java.sql.*;

public class Postagem {
    //private int usuario_id;
    private int codigo;
    private String legenda;
    private String comentario;
    private boolean curtir;
    private boolean salvar;

    /*public Postagem(int usuario_id, String legenda, String comentario, boolean curtir, boolean salvar) {
        //this.usuario_id = usuario_id;
        this.legenda = legenda;
        this.comentario = comentario;
        this.curtir = curtir;
        this.salvar = salvar;
    }*/
    @Override
    public String toString() {
        return "Postagem [id=" + codigo + ", legenda=" + legenda + ", comentario=" + comentario + ", curtir=" + curtir
                + ", salvar=" + salvar + "]";
    }

    


    public int getCodigo() {
        return codigo;
    }




    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }




    public String getLegenda() {
        return legenda;
    }




    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }




    public String getComentario() {
        return comentario;
    }




    public void setComentario(String comentario) {
        this.comentario = comentario;
    }




    public boolean isCurtir() {
        return curtir;
    }




    public void setCurtir(boolean curtir) {
        this.curtir = curtir;
    }




    public boolean isSalvar() {
        return salvar;
    }




    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }




    public void adicionarPostagem() {
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

            // Exibir um JOptionPane para selecionar o usuário
            JList<String> lista = new JList<>(listaUsuarios);
            JOptionPane.showMessageDialog(null, lista, "Selecione o usuário", JOptionPane.PLAIN_MESSAGE);

            // Obter o ID do usuário selecionado
            int indexSelecionado = lista.getSelectedIndex();
            if (indexSelecionado != -1) { // Se um usuário foi selecionado
                int idUsuarioSelecionado = Integer.parseInt(listaUsuarios.get(indexSelecionado).split(":")[0]);

                // Inserir dados na tabela de postagens
                String insercao = "INSERT INTO postagem (usuario_id, legenda, comentario, curtir, salvar) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(insercao);
                statement.setInt(1, idUsuarioSelecionado);
                statement.setString(2, legenda);
                statement.setString(3, comentario);
                statement.setBoolean(4, curtir);
                statement.setBoolean(5, salvar);
                int linhasAfetadas = statement.executeUpdate();

                // Verificar se a inserção foi bem-sucedida
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Postagem adicionada com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao adicionar a postagem.");
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
    public void atualizarPostagem(){
        Connection connection = null;
        try {
            // Obter conexão com o banco de dados
            Conexao conexao = new Conexao();
            connection = conexao.obterConexao();
            String consultaPostagens = "SELECT codigo, legenda FROM postagem";
            PreparedStatement consultaStatement = connection.prepareStatement(consultaPostagens);
            ResultSet resultSetPostagens = consultaStatement.executeQuery();

            DefaultListModel<String> listaPostagem = new DefaultListModel<>();
            while (resultSetPostagens.next()) {
                int idPostagem = resultSetPostagens.getInt("codigo");
                String legendaPostagem = resultSetPostagens.getString("legenda");
                listaPostagem.addElement(idPostagem + ": " + legendaPostagem);
            }
            // Exibir um JOptionPane para selecionar o comentário
            JList<String> lista = new JList<>(listaPostagem);
            JOptionPane.showMessageDialog(null, lista, "Selecione a postagem a ser atualizado", JOptionPane.PLAIN_MESSAGE);

            // Obter o ID do comentário selecionado
            int indexSelecionado = lista.getSelectedIndex();
            if (indexSelecionado != -1) { // Se uma postagem foi selecionado
                int idPostagemSelecionada = Integer.parseInt(listaPostagem.get(indexSelecionado).split(":")[0]);

            // Pedir ao usuário para digitar o novo texto do comentário
            String novaLegendaPostagem = JOptionPane.showInputDialog(null, "Digite a nova legenda da postagem:", "Atualizar Postagem", JOptionPane.PLAIN_MESSAGE);

            // Executar a atualização do comentário
            String atualizacao = "UPDATE postagem SET legenda = ? WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(atualizacao);
            statement.setString(1, novaLegendaPostagem);
            statement.setInt(2, idPostagemSelecionada);
            int linhasAfetadas = statement.executeUpdate();

            // Verificar se a atualização foi bem-sucedida
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Postagem atualizada com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao atualizar a Postagem.");
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
    public void deletarPostagem(){
        Connection connection = null;
        try {
            // Obter conexão com o banco de dados
            Conexao conexao = new Conexao();
            connection = conexao.obterConexao();

            // Consultar todos os comentários cadastrados
            String consultaPostagens = "SELECT codigo, legenda FROM postagem";
            PreparedStatement consultaStatement = connection.prepareStatement(consultaPostagens);
            ResultSet resultSetPostagens = consultaStatement.executeQuery();

            DefaultListModel<String> listaPostagem = new DefaultListModel<>();
            while (resultSetPostagens.next()) {
                int idPostagem = resultSetPostagens.getInt("codigo");
                String legendaPostagem = resultSetPostagens.getString("legenda");
                listaPostagem.addElement(idPostagem + ": " + legendaPostagem);
            }
            // Exibir um JOptionPane para selecionar o comentário
            JList<String> lista = new JList<>(listaPostagem);
            JOptionPane.showMessageDialog(null, lista, "Selecione a postagem a ser atualizado", JOptionPane.PLAIN_MESSAGE);

            // Obter o ID do comentário selecionado
            int indexSelecionado = lista.getSelectedIndex();
            if (indexSelecionado != -1) { // Se uma postagem foi selecionado
                int idPostagemSelecionada = Integer.parseInt(listaPostagem.get(indexSelecionado).split(":")[0]);

                // Confirmar se o usuário deseja realmente apagar o comentário
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar esta postagem?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) { // Se o usuário confirmou
                    // Executar a exclusão do comentário
                    String exclusao = "DELETE FROM postagem WHERE codigo = ?";
                    PreparedStatement statement = connection.prepareStatement(exclusao);
                    statement.setInt(1, idPostagemSelecionada);
                    int linhasAfetadas = statement.executeUpdate();

                    // Verificar se a exclusão foi bem-sucedida
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Postagem apagada com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao apagar a postagem.");
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
    public void listarPostagens() {
        String sql = "SELECT * FROM postagem";
        Conexao conn = new Conexao();
        StringBuilder tabela = new StringBuilder();
        
        try (Connection c = conn.obterConexao()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            tabela.append("ID\tLegenda\tComentário\tCurtir\tSalvar\n");
    
            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String legenda = rs.getString("legenda");
                String comentario = rs.getString("comentario");
                boolean curtir = rs.getBoolean("curtir");
                boolean salvar = rs.getBoolean("salvar");
    
                tabela.append(String.format("%d\t%s\t%s\t%s\t%s\n", codigo, legenda, comentario, curtir, salvar));
            }
    
            JOptionPane.showMessageDialog(null, new JTextArea(tabela.toString()), "Lista de Postagens", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao listar postagens.");
        }
    }



}

