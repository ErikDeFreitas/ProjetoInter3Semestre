import DAO.UsuarioDAO;
import DTO.UsuarioDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""
                                        
                    Seja Muito Bem-Vindo ao sistema!\s
                    Por favor insira seu Opção Desejada:\s
                    [ 1 ] - Entrar\s
                    [ 2 ] - Cadastrar\s
                    [ 3 ] - Depositar\s
                    [ 4 ] - Fechar Sistema
                    """);

            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1 -> login(scanner);
                //Parte De Login, Solicitar CPF e SENHA, comparar com o banco e Liberar ou nao
                case 2 -> cadastrarUsuario(scanner);
                //Parte do Cadastrar, Solicitar as informações, enviar para o BD e voltar para a tela principal
                case 3 -> depositoUsuarioDAO(scanner);
                case 4 -> isRunning = false;
                default -> System.out.println("Opção invalida");
            }

        }

        System.out.println("Sistema Encerrado!");

    }

    public static void cadastrarUsuario(Scanner scanner) throws SQLException {


        System.out.println("Bem vindo ao nosso Banco ELLPT, favor informar seguintes informações para cadastramento");

        System.out.println("Nome");
        String nome = scanner.next();

        System.out.println("CPF");
        String cpf = scanner.next();

        System.out.println("Telefone");
        String cel = scanner.next();

        System.out.println("Data de Nascimento");
        String data = scanner.next();

        System.out.println("Senha");
        String senha = scanner.next();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(nome);
        usuarioDTO.setCpf(cpf);
        usuarioDTO.setCel(cel);
        usuarioDTO.setData(data);
        usuarioDTO.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int rsUsuarioDAO = usuarioDAO.cadastrarUsuarioDAO(usuarioDTO);

        if (rsUsuarioDAO != 0) {
            System.out.println(" ");
            System.out.println("Cadastrado com sucesso");
        } else {
            System.out.println("Erro ao cadastrar");
        }

    }

    public static void login(Scanner scanner) {

        try {

            System.out.println("Por favor, informe seu CPF Cadastrado: ");
            String cpf = scanner.next();
            System.out.println("Senha: ");
            String senha = scanner.next();

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setCpf(cpf);
            usuarioDTO.setSenha(senha);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ResultSet rsUsuarioDAO = usuarioDAO.autenticacaoUsuario(usuarioDTO);

            if (rsUsuarioDAO.next()) {
                System.out.println(" ");
                menuEscolha(scanner);
            } else {
                System.out.println("Usuario/Senha invalida" + rsUsuarioDAO.next());
            }

        } catch (SQLException erro) {
            System.out.println("Login com erro, favor consulte nossa equipe do SAC(1197345-0696) " + erro);
        }
    }

    public static void menuEscolha(Scanner scanner) {

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("");
            System.out.println("Por favor, digite a opção Desejada: ");
            System.out.println("[ 1 ] - Conferir Saldo");
            System.out.println("[ 2 ] - Transferencia");
            System.out.println("[ 3 ] - Deposito");
            System.out.println("[ 4 ] - Extrato");
            System.out.println("[ 5 ] - Encerrar sessão");

            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1 -> conferirSaldo(scanner);
                case 2 -> transferirUsuarioDAO(scanner);

                case 3 -> depositoUsuarioDAO(scanner);

                case 4 -> extrato(scanner);
                case 5 -> isRunning = false;
                default -> System.out.println("Opção invalida");

            }
        }
    }

    public static void transferirUsuarioDAO(Scanner scanner) {
        System.out.println("");
        System.out.println("Digite o CPF de quem deseja transferir: ");
        String cpffalso = scanner.next();
        System.out.println("Qual o valor desejado? ");
        int valor_Transferencia = scanner.nextInt();
        System.out.println("Por favor, insira seu cpf para prosseguir: ");
        String cpfTrans = scanner.next();
        System.out.println("Por favor confirme a operação: \n" +
                "[ 1 ] - Sim\n" +
                "[ 2 ] - Nao");
        int op_ac = scanner.nextInt();

        if (op_ac == 1) {

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setValor_Transferencia(valor_Transferencia);
            usuarioDTO.setCpf(cpfTrans);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            int rs = usuarioDAO.transferirUsuarioDAO(usuarioDTO);

            if (rs != 0) {
                System.out.println("Tranferecia realizada com sucesso");
            } else {
                System.out.println("Erro ao fazer a transferencia");
            }
        }else {
            System.out.println("Operacao Cancelada");
        }
    }

    public static void depositoUsuarioDAO(Scanner scanner) {
        System.out.println("");
        System.out.println("Digite o CPF de quem deseja Depositar: ");
        String cpfDep = scanner.next();
        System.out.println("Qual o valor desejado? ");
        String valor_Deposito = scanner.next();
        System.out.println("Por favor confirme a operação: \n" +
                "[ 1 ] - Sim\n" +
                "[ 2 ] - Nao");
        int op_ac = scanner.nextInt();

        if (op_ac == 1) {

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setDeposito(valor_Deposito);
            usuarioDTO.setCpf(cpfDep);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            int rs = usuarioDAO.depositoUsuarioDAO(usuarioDTO);

            if (rs != 0) {
                System.out.println("Deposito realizado com sucesso");
            } else {
                System.out.println("Erro ao fazer o Deposito");
            }
        }else {
            System.out.println("Operacao Cancelada");
        }

    }

    public static void conferirSaldo(Scanner scanner){
        try {

            System.out.println("");
            System.out.println("Por favor, confirme seu CPF");
            String cpfCS = scanner.next();

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setCpf(cpfCS);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ResultSet rs = usuarioDAO.conferirSaldo(usuarioDTO);

            if (rs.next()) {
                System.out.println(" ");
            } else {
                System.out.println("CPF invalido" + rs.next());
            }
        }catch(SQLException erro) {
            System.out.println("Erro ao conferir, favor consulte nossa equipe do SAC(1197345-0696) " + erro);
        }
    }

    public static void extrato(Scanner scanner){

        System.out.println("");
        System.out.println("Por favor, confirme seu CPF");
        String cpfCS = scanner.next();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf(cpfCS);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.extrato(usuarioDTO);

    }
}