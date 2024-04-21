package org.example;


import com.sun.deploy.util.SyncAccess;
import jdk.nashorn.internal.runtime.ECMAException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class Operation {


    static Scanner sc = new Scanner(System.in);


    public static void operation(){
        System.out.println();
        System.out.println("Enter 1 to create new Account \nEnter 2 to see Demo Account \nEnter 3 to withdraw Money \nEnter 4 to deposit Money \nEnter 5 to Change your PIN \nEnter 6 to check balance \nEnter 7 to close the account \nEnter 8 to Exit");
        System.out.println("------------------------------------------------------------");
        System.out.print("Enter Choice ");
        int key = sc.nextInt();
        System.out.println();
        Execute(key);
    }
    public static void Execute(int key){

        Process process = new Process();
        BankInfo bankinfo = new BankInfo();
        try {
                switch (key) {
                    case 1:
                        System.out.println("-----------------------------------------------------------------------------------");
                        process.openAccount();
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Main Page :: Press 1 :: ");
                        if(sc.nextInt() == 1)
                            operation();
                        break;
                    case 2:
                        System.out.println("-----------------------------------------------------------------------------------");
                        process.demoAccount();
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Main Page :: Press 1 :: ");
                        if(sc.nextInt() == 1)
                            operation();
                        break;
                    case 3:
                        System.out.println("-----------------------------------------------------------------------------------");
                        process.withDraw();
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Main Page :: Press 1 :: ");
                        if(sc.nextInt() == 1)
                            operation();
                        break;
                    case 4:
                        System.out.println("-----------------------------------------------------------------------------------");
                        process.deposit();
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Main Page :: Press 1 :: ");
                        if(sc.nextInt() == 1)
                            operation();
                        break;
                    case 5:
                        System.out.println("-----------------------------------------------------------------------------------");
                        process.changeUPIPin();
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Main Page :: Press 1 :: ");
                        if(sc.nextInt() == 1)
                            operation();
                        break;

                    case 6:
                        System.out.println("-----------------------------------------------------------------------------------");
                        process.checkBalance();
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Main Page :: Press 1 :: ");
                        if(sc.nextInt() == 1)
                            operation();
                        break;
                    case 7:
                        System.out.println("-----------------------------------------------------------------------------------");
                        process.closeAccount();
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Main Page :: Press 1 :: ");
                        if(sc.nextInt() == 1)
                            operation();
                        break;
                    case 8:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid number. Please try Again ");
                        break;
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
