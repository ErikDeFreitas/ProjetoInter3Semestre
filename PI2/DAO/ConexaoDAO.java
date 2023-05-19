package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    public Connection conectaBD() {

        Connection conn;

        try {
            String url = "jdbc:sqlite:Banco/ProjetoInter.db";
            conn = DriverManager.getConnection(url);
            return conn;

        }catch(SQLException erro) {
            System.out.println("ConexaoDAO " + erro);
            return null;

        }
    }
}
