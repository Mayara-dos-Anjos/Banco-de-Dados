package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
public class Conexao  {
 
    private String usuario = "root"; 
    private String senha = "May@2005";
    private String host = "localhost";
    private String porta = "3306";
    private String bd = "rede_social";


    public Connection obterConexao() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + porta + "/" + bd,
                    usuario,
                    senha
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


