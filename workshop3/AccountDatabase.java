/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @project Assignment 3
 * @author Steven Killoran
 * @studentID 144364189
 */
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.PrintWriter;
import java.text.DateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.StringTokenizer;

        //packageName.ClassName;
//import java.util.;
public class AccountDatabase {

    //Account database[];// = new Account[4];
    protected List<Account> database = new ArrayList<Account>();

    //If an account number is entered which has been already created,then it should display the status of the account. 
    //Like when it was created, how much amountin the account, 
    //how much loan was given (if any), how many payments made on the account, how many payments left to be made etc.
    //https://www.w3schools.com/java/java_files.asp
    //https://www.w3schools.com/java/java_files_create.asp
    void createFile() {
        try {
            File database = new File("accountsDatabase.txt");
            if (database.createNewFile()) {
                System.out.println("File created: " + database.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //File myObj = new File("C:\\Users\\MyName\\filename.txt");
    //https://howtodoinjava.com/java/io/java-append-to-file/
    void writeToFile() {
        try {
            FileWriter myWriter = new FileWriter("accountsDatabase.txt");
            //https://stackoverflow.com/questions/27816933/how-to-access-object-list-elements-in-java
            //Iterator<Account> iter = database.iterator();
            //while(iter.hasNext()){
            //    myWriter.write(
            //            iter.next().getId() + "," + 
            //            iter.next().getBalance() + "," +
            //            iter.next().getAnnualInterestRate() + "," +
            //            DateFormat.getInstance().format(iter.next().getDateCreated()) + "," +
            //            iter.next().payments);
            //}
            //123,123.21,0.495,may11,5
            PrintWriter printWriter = new PrintWriter(myWriter);
            for (int i = 0; i < database.size(); i++) {
                
                printWriter.println(
                        database.get(i).getId() + ","
                        + database.get(i).getBalance() + ","
                        + database.get(i).getAnnualInterestRate() + ","
                        + DateFormat.getInstance().format(database.get(i).getDateCreated()) + ","
                        + database.get(i).payments
                );
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //https://www.w3schools.com/java/java_files_read.asp
    void loadData() {

        try {
            File myObj = new File("accountsDatabase.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                StringTokenizer data = new StringTokenizer(myReader.nextLine());
                //id:123,balance:123.21,annualIntRate:0.0495,date:may11,payments:5
                //123,123.21,0.495,may11,5

                //https://stackoverflow.com/questions/7762848/increment-variable-names
                //StringTokenizer class is deprecated now. It is recommended to use split() method of String class or regex (Regular Expression).
                // so use split() if this doesnt work
                try {
                    database.add(new Account(Integer.parseInt(data.nextToken(",")), Double.parseDouble(data.nextToken(",")), Double.parseDouble(data.nextToken(",")), (data.nextToken(",") + "," + data.nextToken(",")), Integer.parseInt(data.nextToken())));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                database.get(0).print();
                //System.out.println(database);

            }
            myReader.close();
        } catch (FileNotFoundException | NumberFormatException e) {//FileNotFoundException
            System.out.println("Couldnt Load File. Now Creating File.");
            createFile();

            //e.printStackTrace();
        }
    }

    void createNewAccount() {
        //id:123,balance:123.21,annualIntRate:0.0495,date:may11,payments:5
        //123,123.21,0.495,may11,5
        Scanner input = new Scanner(System.in);
        //Iterator<Account> iter = database.iterator();
        System.out.println("new account?(0) or with data?(1)");
        String option = input.next();
        while (!(option.equals("0") || option.equals("1"))) {
            System.out.println("new account?(0) or with data?(1)");
            option = input.next();
        }
        if (option.equals("0")) {
            boolean exists = false;
            for (Account check : database) {
                if (check.getId() == 0) {
                    System.out.println("Account already exists");
                    check.print();
                    exists = true;
                }
            }
            if (!exists) {
                database.add(new Account());
                System.out.println("Account Created");
            }
        } else {
            //we are just going to call the validate function and store data. 
            //currently dont have anything to stop the same ids from being entered

            
            System.out.println("please enter an Loan ID, Annual Loan Rate, and Loan Balance");
            System.out.println("If you mess up, you will have to re enter them");
            String id = input.next(), rate = input.next(), balance = input.next();
            //https://www.geeksforgeeks.org/find-first-and-last-element-of-arraylist-in-java/
            boolean exists = false;
            for (Account check : database) {
                if (check.getId() == Integer.parseInt(id)) {
                    System.out.println("Account already exists");
                    check.print();
                    exists = true;
                }
            }
            if (!exists) {
                database.add(new Account());
                database.get(database.size() - 1).validateAccount(id, rate, balance, "create");
                System.out.println("Account Created");
                database.get(database.size() - 1).print();
            }
        }
    }

    void makePayment() {
        Scanner input = new Scanner(System.in);
        System.out.println("enter an account ID and amount to deposit/pay");
        String id = input.next();
        String payment = input.next();
        for (Account check : database) {
            if (check.getId() == Integer.parseInt(id)) {
                check.print();
                check.deposit(Double.parseDouble(payment));
                check.print();
            }
        }

    }
    
    void makeWithdrawal() {
        Scanner input = new Scanner(System.in);
        System.out.println("enter an account ID and amount to deposit/pay");
        String id = input.next();
        String withdrawal = input.next();
        for (Account check : database) {
            if (check.getId() == Integer.parseInt(id)) {
                check.print();
                check.withdraw(Double.parseDouble(withdrawal));
                check.print();
            }
        }

    }

}
