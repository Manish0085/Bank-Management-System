package org.example;

import com.sun.javafx.geom.Quat4f;

import java.sql.*;
import java.util.Objects;
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

    public void openAccount() throws SQLException {
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
            if (acc_Number.length() != 12) {
                System.out.println("Account Number Length must contain 12 digit");
                Operation.operation();
            }
            bank.setAccountNo(acc_Number);
            System.out.print("Enter Your Phone No. ");
            String phone = sc.next();
            bank.setPhone(phone);
            System.out.print("Enter Amount ");
            int amount = sc.nextInt();
            if (amount >= 3500) {
                preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Phone = " + bank.getPhone());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("An Account is already exist withe Specified number......");
                    System.out.println("Try with another number");
                    System.out.println("---------------------------------------------------------------------------------");
                    Operation.operation();
                }
                bank.setBalance(amount);
            } else {
                System.out.println();
                System.out.println("*******************************************************************************************************");
                System.out.println("The minimum amount to get the account opened is 3500");
                System.out.println("Warning :: Please Deposit minimum amount to get the account opened");
                System.out.println("*******************************************************************************************************");
                System.out.println();
                Operation.operation();
            }

            System.out.println("Set Your UPI Pin");
            System.out.println("Enter four digit UPI Pin for security Purpose");

            String pin = sc.next();
            if (pin.length() != 4) {
                System.out.println("Pin length must be four");
                Operation.operation();
            }
            bank.setPIN(pin);
            System.out.println("Warning :- Do Not Share this pin with anyone");
            String SQLQuery = "INSERT INTO bank(name, phone, acc_Number, type, amount, pin) VALUES(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(SQLQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, acc_Number);
            preparedStatement.setString(4, type);
            preparedStatement.setInt(5, amount);
            preparedStatement.setString(6, pin);
            preparedStatement.executeUpdate();
            System.out.println("******* Thanks for creating account ******");
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
        } catch (Exception e) {
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
        if (withdraw < 500) {
            System.out.println("Can,t withdraw less than 500 Rs.");
            Process.deposit();
        }
        connection = DriverManager.getConnection(url, username, password);
        preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
        resultSet = preparedStatement.executeQuery();
        System.out.print("Enter UPI Pin ");
        String pin = sc.next();
        while (resultSet.next()) {
            if (!Objects.equals(resultSet.getString("Pin"), pin)) {
                System.out.println("Incorrect UPI Pin");
                Operation.operation();
            }
            if (resultSet.getInt("Amount") > withdraw) {

                int amount = (int) (resultSet.getInt("Amount") - withdraw);
                if (amount >= 3500) {
                    connection = DriverManager.getConnection(url, username, password);
                    preparedStatement = connection.prepareStatement("UPDATE bank SET Amount = " + amount + " WHERE ACC_Number = " + acc_Number);
                    preparedStatement.executeUpdate();
                    System.out.println("Money withdraw successfully");
                } else {
                    connection = DriverManager.getConnection(url, username, password);
                    preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int amount1 = resultSet.getInt("Amount") - 3500;
                        System.out.println("Minimum Amount is required to run the account");
                        System.out.println("You can withdraw only " + amount1);
                    }
                }
            }
        }
        preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("The current amount is " + resultSet.getInt("Amount"));
        }
        System.out.println("************* THANKS **************");
    }

    public static void deposit() {
        try {
            System.out.print("Enter your Account Number ");
            String acc_Number = sc.next();

            System.out.print("Enter the amount to be deposited ");
            int amount = sc.nextInt();
            if (amount < 500) {
                System.out.println("The minimum amount is 500");
                Process.deposit();
            }
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
            resultSet = preparedStatement.executeQuery();
            System.out.print("Enter UPI Pin ");
            String pin = sc.next();
            while (resultSet.next()) {
                if (!Objects.equals(resultSet.getString("Pin"), pin)) {
                    System.out.println("Incorrect UPI Pin");
                    Operation.operation();
                }
                int crrAmount = resultSet.getInt("Amount") + amount;
                preparedStatement = connection.prepareStatement("UPDATE bank SET Amount = " + crrAmount + " WHERE Acc_Number = " + acc_Number);
                preparedStatement.executeUpdate();
                System.out.println("Money Deposited successful");

                //System.out.println("The current Amount is " + resultSet.getInt("Amount") );

            }

            preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("The current amount is " + resultSet.getInt("Amount"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("************* THANKS **************");
    }

    public void checkBalance() throws SQLException {
        System.out.print("Enter your Account Number to be checked ");
        String acc_Number = sc.next();
        Connection connection = DriverManager.getConnection(url, username, password);
        String SQLQuery = "SELECT * FROM bank WHERE Acc_Number = " + acc_Number;
        preparedStatement = connection.prepareStatement(SQLQuery);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Name        :-  " + resultSet.getString("Name"));
            System.out.println("Phone       :-  " + resultSet.getString("Phone"));
            System.out.println("Account No. :-  " + resultSet.getString("Acc_Number"));
            System.out.println("Account Type:-  " + resultSet.getString("Type"));
            System.out.println("Amount      :-  " + resultSet.getString("Amount"));
        }
        System.out.println("************* THANKS **************");
    }

    public void closeAccount() throws SQLException {
        System.out.print("Enter your Account Number to be Deleted ");
        String acc_Number = sc.next();
        connection = DriverManager.getConnection(url, username, password);
        String SQLQuery = "DELETE FROM bank WHERE Acc_Number = " + acc_Number;
        preparedStatement = connection.prepareStatement(SQLQuery);
        preparedStatement.executeUpdate();
        System.out.println("Account has been closed successfully ");
        System.out.println("************* THANKS **************");
    }

    public static void changeUPIPin() throws SQLException {
        System.out.print("Enter your Account Number ");
        String acc_Number = sc.next();
        System.out.print("Enter old UPI Pin ");
        String pin = sc.next();
        connection = DriverManager.getConnection(url, username, password);
        preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE Acc_Number = " + acc_Number);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (!Objects.equals(resultSet.getString("Pin"), pin)) {
                System.out.println("Incorrect UPI Pin");
                Operation.operation();
            }

        }
        System.out.print("Enter New UPI Pin ");
        String newPin = sc.next();
        if (newPin.length() != 4) {
            System.out.println("Warning :- Pin length must be 4");
            Process.changeUPIPin();
        }
        System.out.print("confirm Pin ");
        if (!Objects.equals(newPin, sc.next())) {
            System.out.println("Warning :- Pin is not matched");
            Process.changeUPIPin();
        }
        preparedStatement = connection.prepareStatement("UPDATE bank SET Pin = " + newPin + " Where Acc_Number = " + acc_Number);
        int i = preparedStatement.executeUpdate();
        System.out.println("************* THANKS **************");
        System.out.println("Your UPI Pin has been changed successfully");
        //System.out.println("New Pin is "+bank.getPIN());


    }
}

