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

import java.util.Scanner; // Import the Scanner class to read text files
public class Main {

    /**
     * @param args the command line arguments
     */
       public static void main(String[] args) {
        // TODO code application logic here
        AccountDatabase database = new AccountDatabase();
        
        Scanner input = new Scanner(System.in);
        boolean exitFlag = false;
        while (!exitFlag){
            
            menu1();
            System.out.print(">");
            //int option = input.nextInt();
            exitFlag = menu2(input.nextInt(), database);
            //option =0;
            //input.close();
            //System.out.flush();
        }
         
    }
    
    static void  menu1(){
        System.out.println("********************************");
        System.out.println("  Assignment 3 - Loan Accounts  ");
        System.out.println("********************************");
        System.out.println();
        System.out.println("Please select an option 1 - 3");
        System.out.println();
        System.out.println("Load Accounts from file   - 1");
        //System.out.println("Create Account");
        System.out.println("Create New Account        - 2");
        System.out.println("Save Accounts to Database - 3");
        System.out.println("Make Deposit/Payment      - 4");
        System.out.println("Make Withdrawal           - 5");
        System.out.println("Exit - 6");
        
    }
    
        static boolean menu2(int choice, AccountDatabase database){
        boolean temp = false;
        switch (choice){
            case 1:
                database.loadData();
                break;
            case 2:
                database.createNewAccount();
                break;
            case 3:
                database.writeToFile();
                break;
            case 4:
                database.makePayment();
                break;
            case 5:
                database.makeWithdrawal();
                break;
            default:
                temp = true;
                break;
        }
        return temp;
    }
}
