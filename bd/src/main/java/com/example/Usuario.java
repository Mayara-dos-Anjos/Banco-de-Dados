package com.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
public class Usuario {
    
    private int id;
    private String nome;
    private String nascimento;
    private String email;
    private String senha;

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", nascimento=" + nascimento + ", email=" + email + ", senha="
                + senha + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public  void cadastrarUsuario(){
        String sql = "INSERT INTO usuario (nome, nascimento, email, senha) VALUES(?,?,?,?)";
        Conexao conn = new Conexao();
        try(Connection c =  conn.obterConexao()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, this.nome);
            ps.setString(2, this.nascimento);
            ps.setString(3, this.email);
            ps.setString(4, this.senha);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuario.");
        }
        
        
    }
    public void atualizarUsuario(){
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
    JOptionPane.showMessageDialog(null, lista, "Selecione o usuário a ser atualizado", JOptionPane.PLAIN_MESSAGE);

    // Obter o ID do usuário selecionado
    int indexSelecionado = lista.getSelectedIndex();
    if (indexSelecionado != -1) { // Se um usuário foi selecionado
        int idUsuarioSelecionado = Integer.parseInt(listaUsuarios.get(indexSelecionado).split(":")[0]);

        // Agora você pode implementar a lógica para atualizar os detalhes do usuário selecionado
        // por exemplo, pedir ao usuário para digitar o novo nome, data de nascimento, email, senha, etc.,
        // e executar a atualização correspondente

        // Para este exemplo, vou supor que você tem os novos dados do usuário
        String novoNomeUsuario = JOptionPane.showInputDialog(null, "Digite o novo nome do usuário:", "Atualizar Usuário", JOptionPane.PLAIN_MESSAGE);
        String novoEmailUsuario = JOptionPane.showInputDialog(null, "Digite o novo email do usuário:", "Atualizar Usuário", JOptionPane.PLAIN_MESSAGE);
        String novaSenhaUsuario = JOptionPane.showInputDialog(null, "Digite a nova senha do usuário:", "Atualizar Usuário", JOptionPane.PLAIN_MESSAGE);
        // Suponha que você também tenha a nova data de nascimento do usuário como uma String
        String novaDataNascimentoUsuario = JOptionPane.showInputDialog(null, "Digite a nova data de nascimento do usuário):", "Atualizar Usuário", JOptionPane.PLAIN_MESSAGE);

        // Executar a atualização do usuário
        String atualizacao = "UPDATE usuario SET nome=?, nascimento=?, email=?, senha=? WHERE codigo=?";
        PreparedStatement statement = connection.prepareStatement(atualizacao);
        statement.setString(1, novoNomeUsuario);
        statement.setString(2, novaDataNascimentoUsuario);
        statement.setString(3, novoEmailUsuario);
        statement.setString(4, novaSenhaUsuario);
        statement.setInt(5, idUsuarioSelecionado);
        int linhasAfetadas = statement.executeUpdate();

        // Verificar se a atualização foi bem-sucedida
        if (linhasAfetadas > 0) {
            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso.");
        } else {
            JOptionPane.showMessageDialog(null, "Falha ao atualizar o usuário.");
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
        
    public void deletarUsuario(){
        Connection connection = null;
        try {
            // Obter conexão com o banco de dados
            Conexao conexao = new Conexao();
            connection = conexao.obterConexao();

            // Consultar todos os comentários cadastrados
            String consultaUsuarios = "SELECT codigo, nome FROM usuario";
            PreparedStatement consultaStatement = connection.prepareStatement(consultaUsuarios);
            ResultSet resultSetUsuarios = consultaStatement.executeQuery();

            // Criar uma lista de comentários para exibir no JOptionPane
            DefaultListModel<String> listaUsuarios = new DefaultListModel<>();
            while (resultSetUsuarios.next()) {
                int idUsuario = resultSetUsuarios.getInt("codigo");
                String nomeUsuario = resultSetUsuarios.getString("nome");
                listaUsuarios.addElement(idUsuario + ": " + nomeUsuario + " ");
            }

            // Exibir um JOptionPane para selecionar o comentário
            JList<String> lista = new JList<>(listaUsuarios);
            JOptionPane.showMessageDialog(null, lista, "Selecione o usuário a ser apagado", JOptionPane.PLAIN_MESSAGE);

            // Obter o ID do comentário selecionado
            int indexSelecionado = lista.getSelectedIndex();
            if (indexSelecionado != -1) { // Se um comentário foi selecionado
                int idUsuarioSelecionado = Integer.parseInt(listaUsuarios.get(indexSelecionado).split(":")[0]);

                // Confirmar se o usuário deseja realmente apagar o comentário
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este Usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) { // Se o usuário confirmou
                    // Executar a exclusão do comentário
                    String exclusao = "DELETE FROM usuario WHERE codigo = ?";
                    PreparedStatement statement = connection.prepareStatement(exclusao);
                    statement.setInt(1, idUsuarioSelecionado);
                    int linhasAfetadas = statement.executeUpdate();

                    // Verificar se a exclusão foi bem-sucedida
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Usuário apagado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao apagar o usuário.");
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
        
public void listarUsuario() {
    String sql = "SELECT * FROM usuario";
    Conexao conn = new Conexao();
    StringBuilder tabela = new StringBuilder();
    
    try (Connection c = conn.obterConexao()) {
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        tabela.append("ID\tNome\tData de Nascimento\tEmail\tSenha\n");

        while (rs.next()) {
            int id = rs.getInt("Codigo");
            String nome = rs.getString("Nome");
            String nascimento = rs.getString("Nascimento");
            String email = rs.getString("Email");
            String senha = rs.getString("Senha");

            tabela.append(String.format("%d\t%s\t%s\t%s\t%s\n", id, nome, nascimento, email, senha));
        }

        JOptionPane.showMessageDialog(null, new JTextArea(tabela.toString()), "Lista de Usuários", JOptionPane.PLAIN_MESSAGE);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erro ao listar usuários.");
    }
}

    
}
