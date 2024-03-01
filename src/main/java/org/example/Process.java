package org.example;

import com.sun.javafx.geom.Quat4f;

import java.sql.*;
import java.util.Scanner;

public class Process {
    static BankInfo bank = new BankInfo();

    static Operation bankInfo = new Operation();
    static Scanner sc = new Scanner(System.in);

    private static final String url = "jdbc:mysql://localhost:3306/spark";
    private static final String username = "root";
    private static final String password = "M@nish13";
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public void openAccount ()
    {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.print("Enter Your Name ");
            String name = sc.next();
            bank.setName(name);
            System.out.print("Account Type ");
            String type = sc.next();
            bank.setAccType(type);
            System.out.print("Account No. ");
            String acc_Number = sc.next();
            bank.setAccountNo(acc_Number);
            System.out.print("Enter Your Phone No. ");
            String phone = sc.next();
            bank.setPhone(phone);
            System.out.print("Enter Amount ");
            int amount = sc.nextInt();
            if(amount >= 3500 ) {
                preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Phone = "+bank.getPhone());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("An Account is already exist withe Specified number......");
                    System.out.println("Try with another number");
                    System.out.println("---------------------------------------------------------------------------------");
                    Operation.operation();
                }
                bank.setBalance(amount);
            }
            else {
                System.out.println();
                System.out.println("*******************************************************************************************************");
                System.out.println("The minimum amount to get the account opened is 3500");
                System.out.println("Warning :: Please Deposit minimum amount to get the account opened");
                System.out.println("*******************************************************************************************************");
                System.out.println();
                Operation.operation();
            }


            String SQLQuery = "INSERT INTO bank(name, phone, acc_Number, type, amount) VALUES(?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(SQLQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, acc_Number);
            preparedStatement.setString(4, type);
            preparedStatement.setInt(5, amount);
            preparedStatement.executeUpdate();
            System.out.println("Your Account has been Created Successfully");
            System.out.println("************************************ Your Account Details ***************************************");
            System.out.println();
            System.out.println("Account Holder's Name    :: " + bank.getName());
            System.out.println("Account No.              :: " + bank.getAccountNo());
            System.out.println("Account Type             :: " + bank.getAccType());
            System.out.println("Phone No.                :: " + bank.getPhone());
            System.out.println("Current Amount           :: " + bank.getBalance() + " Rs.");
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------------------");
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void demoAccount() {
        System.out.println("************************************ Demo Account  ***************************************");
        System.out.println();
        System.out.println("Account Holder's Name    :: Demo");
        System.out.println("Account No.              :: ********4869");
        System.out.println("Account Type             :: Saving");
        System.out.println("Phone No.                :: 0123456789");
        System.out.println("Current Amount           :: ******** Rs.");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------");

    }
    public void withDraw() throws SQLException {

        System.out.print("Enter your Account Number ");
        String acc_Number = sc.next();
        System.out.print("Enter the Amount to be withdraw ");
        int withdraw = sc.nextInt();
        connection = DriverManager.getConnection(url, username, password);
        preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getInt("Amount") > withdraw ) {

                int amount = (int) (resultSet.getInt("Amount") - withdraw);
                if(amount >= 3500) {
                    connection = DriverManager.getConnection(url, username, password);
                    preparedStatement = connection.prepareStatement("UPDATE bank SET Amount = " + amount + " WHERE ACC_Number = " + acc_Number);
                    preparedStatement.executeUpdate();
                    System.out.println("Money withdraw successfully");
                }
                else {
                    connection = DriverManager.getConnection(url, username, password);
                    preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int amount1 = resultSet.getInt("Amount") - 3500;
                        System.out.println("Minimum Amount is required to run the account");
                        System.out.println("You can withdraw only "+amount1);
                    }
                }
            }
        }
        preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println("The current amount is "+resultSet.getInt("Amount"));
        }
    }
    public void deposit() throws SQLException {
        try {
            System.out.print("Enter your Account Number ");
            String acc_Number = sc.next();
            System.out.print("Enter the amount to be deposited ");
            int amount = sc.nextInt();
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                    int crrAmount = resultSet.getInt("Amount") + amount;
                    preparedStatement = connection.prepareStatement("UPDATE bank SET Amount = " + crrAmount + " WHERE Acc_Number = " + acc_Number);
                    preparedStatement.executeUpdate();
                    System.out.println("Money Deposited successful");

                //System.out.println("The current Amount is " + resultSet.getInt("Amount") );

            }

            preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("The current amount is "+resultSet.getInt("Amount"));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void checkBalance() throws SQLException {
        System.out.print("Enter your Account Number to be checked ");
        String acc_Number = sc.next();
        Connection connection = DriverManager.getConnection(url, username, password);
        String SQLQuery = "SELECT * FROM bank WHERE Acc_Number = "+acc_Number;
        preparedStatement = connection.prepareStatement(SQLQuery);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Name        :-  " + resultSet.getString("Name"));
            System.out.println("Phone       :-  " + resultSet.getString("Phone"));
            System.out.println("Account No. :-  " + resultSet.getString("Acc_Number"));
            System.out.println("Account Type:-  " + resultSet.getString("Type"));
            System.out.println("Amount      :-  " + resultSet.getString("Amount"));
        }
    }

    public void closeAccount() throws SQLException {
        System.out.print("Enter your Account Number to be Deleted ");
        String acc_Number = sc.next();
        connection = DriverManager.getConnection(url, username, password);
        String SQLQuery = "DELETE FROM bank WHERE Acc_Number = "+acc_Number;
        preparedStatement = connection.prepareStatement(SQLQuery);
        preparedStatement.executeUpdate();
        System.out.println("Account has been closed successfully ");
    }
}
