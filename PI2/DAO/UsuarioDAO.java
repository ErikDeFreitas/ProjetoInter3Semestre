package DAO;

import DTO.UsuarioDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    Connection conn;

    public ResultSet autenticacaoUsuario(UsuarioDTO usuarioDTO){

        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "select * from cadastro where cpf = ? and senha = ? ";

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, usuarioDTO.getCpf());
            pstm.setString(2, usuarioDTO.getSenha());

            ResultSet rs = pstm.executeQuery();
            conn.close();
            return rs;


        } catch (SQLException erro) {
            System.out.println("UsuarioDAO: " + erro);
            return null;
        }
    }


    public int cadastrarUsuarioDAO(UsuarioDTO usuarioDTO){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "insert into Cadastro(nome,cpf,cel,data_nascimento,senha,saldo) values (?,?,?,?,?,0);";
            String ext = "insert into Extrato(cpf,entrada,saida) values (?,0,0);";

            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, usuarioDTO.getNome());
            pstm.setString(2, usuarioDTO.getCpf());
            pstm.setString(3, usuarioDTO.getCel());
            pstm.setString(4, usuarioDTO.getData());
            pstm.setString(5, usuarioDTO.getSenha());

            int rs = pstm.executeUpdate();

            PreparedStatement extpstm = conn.prepareStatement(ext);
            extpstm.setString(1, usuarioDTO.getCpf());
            extpstm.executeUpdate();

            conn.close();
            return rs;

        } catch (SQLException erro) {
            System.out.println("UsuarioDAO Cadastro: " + erro);
            return 0;
        }
    }
    public int transferirUsuarioDAO(UsuarioDTO usuarioDTO){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "update cadastro set saldo = (select saldo from cadastro where cpf=?)-? where cpf = ?";
            String ext = "insert into Extrato(cpf,entrada,saida) values (?,0,?);";

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, usuarioDTO.getCpf());
            pstm.setString(3, usuarioDTO.getCpf());
            pstm.setInt(2, usuarioDTO.getValor_Transferencia());
            int rs = pstm.executeUpdate();

            PreparedStatement extpstm = conn.prepareStatement(ext);
            extpstm.setString(1, usuarioDTO.getCpf());
            extpstm.setInt(2, usuarioDTO.getValor_Transferencia());

            extpstm.executeUpdate();

            conn.close();
            return rs;

        } catch(SQLException erro){
            System.out.println("UsuarioDAO Transferencia: " + erro +" "+ usuarioDTO.getValor_Transferencia() +" "+ usuarioDTO.getCpf());
            return 0;
        }
    }

    public int depositoUsuarioDAO(UsuarioDTO usuarioDTO){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "update cadastro set saldo = (select saldo from cadastro where cpf=?)+? where cpf = ?";
            String ext = "insert into Extrato(cpf,entrada,saida) values (?,?,0);";


            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, usuarioDTO.getCpf());
            pstm.setString(3, usuarioDTO.getCpf());
            pstm.setString(2, usuarioDTO.getDeposito());
            int rs = pstm.executeUpdate();

            PreparedStatement extpstm = conn.prepareStatement(ext);
            extpstm.setString(1, usuarioDTO.getCpf());
            extpstm.setString(2, usuarioDTO.getDeposito());

            extpstm.executeUpdate();

            conn.close();
            return rs;

        } catch(SQLException erro){
            System.out.println("UsuarioDAO Deposito: " + erro +" "+ usuarioDTO.getDeposito() +" "+ usuarioDTO.getCpf());
            return 0;
        }
    }

    public ResultSet conferirSaldo(UsuarioDTO usuarioDTO){
        conn = new ConexaoDAO().conectaBD();

        try{

            String sql = "select nome, cpf, saldo from cadastro where cpf = ?";

            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, usuarioDTO.getCpf());
            ResultSet rs = pstm.executeQuery();
            String rs1 = rs.getString(1);
            String rs2 = rs.getString(2);
            int rs3 = rs.getInt(3);

            System.out.println("Nome:" + rs1 + " | Cpf:" + rs2 + " | R$" + rs3);
            conn.close();
            return rs;

        }catch(SQLException erro){
            System.out.println("UsuarioDAO Conferir Saldo " + erro);
            return null;
        }

    }

    public void extrato(UsuarioDTO usuarioDTO){
        conn = new ConexaoDAO().conectaBD();

        try{

            String sql = "SELECT c.nome, c.cpf, e.entrada, e.saida FROM Cadastro c INNER JOIN Extrato e ON c.cpf = e.cpf WHERE c.cpf = ?;";

            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1, usuarioDTO.getCpf());
            ResultSet rs = pstm.executeQuery();

            while (rs.next()){

                String r1 = rs.getString(1);
                String r2 = rs.getString(2);
                int r3 = rs.getInt(3);
                int r4 = rs.getInt(4);

                System.out.println("Nome: " + r1 + " | Cpf: " + r2 + " | R$+" + r3 + " | R$-" + r4);

            }
            conn.close();

        }catch(SQLException erro){
            System.out.println("UsuarioDAO Conferir Extrato " + erro);
        }

    }

}