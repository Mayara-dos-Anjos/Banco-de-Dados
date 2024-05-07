package com.example;
import javax.swing.*;
import java.sql.*;
public class Comentario {
    private int codigo;
    private int usuario_id;
    private int postagem_id;
    private String texto;

    @Override
    public String toString() {
        return "Comentario [codigo=" + codigo + ", usuario_id=" + usuario_id + ", postagem_id=" + postagem_id
                + ", texto=" + texto + "]";
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getPostagem_id() {
        return postagem_id;
    }

    public void setPostagem_id(int postagem_id) {
        this.postagem_id = postagem_id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    
    public void adicionarComentario() {
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

                // Consultar todas as postagens cadastradas
                String consultaPostagens = "SELECT codigo, legenda FROM postagem";
                PreparedStatement consultaPostagensStatement = connection.prepareStatement(consultaPostagens);
                ResultSet resultSetPostagens = consultaPostagensStatement.executeQuery();

                // Criar uma lista de postagens para exibir no JOptionPane
                DefaultListModel<String> listaPostagens = new DefaultListModel<>();
                while (resultSetPostagens.next()) {
                    int idPostagem = resultSetPostagens.getInt("codigo");
                    String tituloPostagem = resultSetPostagens.getString("legenda");
                    listaPostagens.addElement(idPostagem + ": " + tituloPostagem);
                }

                // Exibir um JOptionPane para selecionar a postagem
                JList<String> listaPostagensView = new JList<>(listaPostagens);
                JOptionPane.showMessageDialog(null, listaPostagensView, "Selecione a postagem", JOptionPane.PLAIN_MESSAGE);

                // Obter o ID da postagem selecionada
                int indexPostagemSelecionada = listaPostagensView.getSelectedIndex();
                if (indexPostagemSelecionada != -1) { // Se uma postagem foi selecionada
                    int idPostagemSelecionada = Integer.parseInt(listaPostagens.get(indexPostagemSelecionada).split(":")[0]);

                    // Pedir ao usuário para digitar o comentário
                    String textoComentario = JOptionPane.showInputDialog(null, "Digite o seu comentário:", "Adicionar Comentário", JOptionPane.PLAIN_MESSAGE);

                    // Inserir dados na tabela de comentários
                    String insercao = "INSERT INTO comentario (usuario_id, postagem_id, texto) VALUES (?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(insercao);
                    statement.setInt(1, idUsuarioSelecionado);
                    statement.setInt(2, idPostagemSelecionada);
                    statement.setString(3, textoComentario);
                    int linhasAfetadas = statement.executeUpdate();

                    // Verificar se a inserção foi bem-sucedida
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Comentário adicionado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao adicionar o comentário.");
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
    
    public void listarComentarios() {
        Connection connection = null;
        try {
            // Obter conexão com o banco de dados
            Conexao conexao = new Conexao();
            connection = conexao.obterConexao();

            // Consultar todos os comentários cadastrados
            String consultaComentarios = "SELECT usuario.nome AS nome_usuario, postagem.legenda AS legenda_postagem, comentario.texto AS texto_comentario " +
            "FROM comentario " +
            "INNER JOIN usuario ON comentario.usuario_id = usuario.codigo " +
            "INNER JOIN postagem ON comentario.postagem_id = postagem.codigo";
            PreparedStatement consultaStatement = connection.prepareStatement(consultaComentarios);
            ResultSet resultSetComentarios = consultaStatement.executeQuery();

            // Criar uma StringBuilder para armazenar os comentários
            StringBuilder comentarios = new StringBuilder();

            // Iterar sobre os resultados e adicionar os comentários à StringBuilder
            while (resultSetComentarios.next()) {
                String nomeUsuario = resultSetComentarios.getString("nome_usuario");
                String tituloPostagem = resultSetComentarios.getString("legenda_postagem");
                String textoComentario = resultSetComentarios.getString("texto_comentario");

                comentarios.append("Usuário: ").append(nomeUsuario).append("\n");
                comentarios.append("Postagem: ").append(tituloPostagem).append("\n");
                comentarios.append("Comentário: ").append(textoComentario).append("\n\n");
            }

            // Exibir os comentários em um JOptionPane
            JTextArea textArea = new JTextArea(comentarios.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
            JOptionPane.showMessageDialog(null, scrollPane, "Lista de Comentários", JOptionPane.PLAIN_MESSAGE);

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
    public void apagarComentario() {
        Connection connection = null;
        try {
            // Obter conexão com o banco de dados
            Conexao conexao = new Conexao();
            connection = conexao.obterConexao();

            // Consultar todos os comentários cadastrados
            String consultaComentarios = "SELECT codigo, texto FROM comentario";
            PreparedStatement consultaStatement = connection.prepareStatement(consultaComentarios);
            ResultSet resultSetComentarios = consultaStatement.executeQuery();

            // Criar uma lista de comentários para exibir no JOptionPane
            DefaultListModel<String> listaComentarios = new DefaultListModel<>();
            while (resultSetComentarios.next()) {
                int idComentario = resultSetComentarios.getInt("codigo");
                String textoComentario = resultSetComentarios.getString("texto");
                listaComentarios.addElement(idComentario + ": " + textoComentario);
            }

            // Exibir um JOptionPane para selecionar o comentário
            JList<String> lista = new JList<>(listaComentarios);
            JOptionPane.showMessageDialog(null, lista, "Selecione o comentário a ser apagado", JOptionPane.PLAIN_MESSAGE);

            // Obter o ID do comentário selecionado
            int indexSelecionado = lista.getSelectedIndex();
            if (indexSelecionado != -1) { // Se um comentário foi selecionado
                int idComentarioSelecionado = Integer.parseInt(listaComentarios.get(indexSelecionado).split(":")[0]);

                // Confirmar se o usuário deseja realmente apagar o comentário
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este comentário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) { // Se o usuário confirmou
                    // Executar a exclusão do comentário
                    String exclusao = "DELETE FROM comentario WHERE codigo = ?";
                    PreparedStatement statement = connection.prepareStatement(exclusao);
                    statement.setInt(1, idComentarioSelecionado);
                    int linhasAfetadas = statement.executeUpdate();

                    // Verificar se a exclusão foi bem-sucedida
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Comentário apagado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao apagar o comentário.");
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
    public void atualizarComentario() {
        Connection connection = null;
        try {
            // Obter conexão com o banco de dados
            Conexao conexao = new Conexao();
            connection = conexao.obterConexao();

            // Consultar todos os comentários cadastrados
            String consultaComentarios = "SELECT codigo, texto FROM comentario";
            PreparedStatement consultaStatement = connection.prepareStatement(consultaComentarios);
            ResultSet resultSetComentarios = consultaStatement.executeQuery();

            // Criar uma lista de comentários para exibir no JOptionPane
            DefaultListModel<String> listaComentarios = new DefaultListModel<>();
            while (resultSetComentarios.next()) {
                int idComentario = resultSetComentarios.getInt("codigo");
                String textoComentario = resultSetComentarios.getString("texto");
                listaComentarios.addElement(idComentario + ": " + textoComentario);
            }

            // Exibir um JOptionPane para selecionar o comentário
            JList<String> lista = new JList<>(listaComentarios);
            JOptionPane.showMessageDialog(null, lista, "Selecione o comentário a ser atualizado", JOptionPane.PLAIN_MESSAGE);

            // Obter o ID do comentário selecionado
            int indexSelecionado = lista.getSelectedIndex();
            if (indexSelecionado != -1) { // Se um comentário foi selecionado
                int idComentarioSelecionado = Integer.parseInt(listaComentarios.get(indexSelecionado).split(":")[0]);

                // Pedir ao usuário para digitar o novo texto do comentário
                String novoTextoComentario = JOptionPane.showInputDialog(null, "Digite o novo texto do comentário:", "Atualizar Comentário", JOptionPane.PLAIN_MESSAGE);

                // Executar a atualização do comentário
                String atualizacao = "UPDATE comentario SET texto = ? WHERE codigo = ?";
                PreparedStatement statement = connection.prepareStatement(atualizacao);
                statement.setString(1, novoTextoComentario);
                statement.setInt(2, idComentarioSelecionado);
                int linhasAfetadas = statement.executeUpdate();

                // Verificar se a atualização foi bem-sucedida
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Comentário atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao atualizar o comentário.");
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
}
