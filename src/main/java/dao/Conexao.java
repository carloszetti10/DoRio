package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/ProjetoSucon";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";
    
    //private static final String URL = "jdbc:postgresql://monorail.proxy.rlwy.net:31645/railway";
    //private static final String USUARIO = "postgres";
    //private static final String SENHA = "yUNlBukTygcMvuHMljORaXqOpnszUrAs";


    // Método para obter a conexão
    public Connection getConexao() {
        try {
            // Tenta estabelecer a conexão
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            return conexao;
        } catch (SQLException e) {
            // Trata o erro de conexão
            e.printStackTrace();  // Exibe mais detalhes sobre o erro
            return null;
        }
    }
}
