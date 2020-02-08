
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;
import java.lang.Math;

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
public class Account {

    protected int id;
    protected double balance;
    protected double annualInterestRate;
    protected Date dateCreated;
    int payments;  //was lazy and used it in accountDatabase  database.get(i).payments. so leaving public

    //------
    Account() {
        id = 0;
        balance = 0;
        annualInterestRate = 0;
        dateCreated = new Date();
        payments = 0;
    }

    Account(int id, double balance) {
        this.setId(id);
        this.setBalance(balance);
        annualInterestRate = 0;
        dateCreated = new Date();
        payments = 0;
    }
    Account(int id, double balance, double annualInterestRate, String date, int paymentsmade) throws ParseException {
        this.setId(id);
        this.setBalance(balance);
        this.setAnnualInterestRate(annualInterestRate);
        dateCreated = DateFormat.getInstance().parse(date);
        payments = paymentsmade;
    }

    void setId(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    void setBalance(double balance) {
        this.balance = balance;
    }

    double getBalance() {
        return balance;
    }

    void setAnnualInterestRate(double rate) {
        this.annualInterestRate = rate;
    }

    double getAnnualInterestRate() {
        return annualInterestRate;
    }

    Date getDateCreated() {
        return dateCreated;
    }

    double getMonthlyInterestRate() {
        //monthlyInterestRate is annualInterestRate / 12. 
        //Note that annualInterestRate is a percentage,•e.g., like 4.5%.
        return annualInterestRate / 12;
    }

    double getMonthlyInterest() {
        //getMonthlyInterest() is to return monthly interest, not the interest rate. 
        //•Monthly interest is balance * monthlyInterestRate.
        return balance * getMonthlyInterestRate();
    }

    void withdraw(double additionalLoan) {
        //Use the withdraw method to withdraw a valid users entered valid amount

        balance += additionalLoan;
        //return 2.3;
    }

    void deposit(double payment) {
        //Use the deposit method to deposit a valid users entered valid amount
        //balance is a loan, so it should get smaller after depositing, rather than bigger.
        payments++;
        balance -= payment;
        //return 3.4;
    }

    void print() {
        //Print the balance, the month interest, the date when this account was created
        System.out.println("Current balance - " + getBalance());
        System.out.println("Monthly interest - " + getMonthlyInterest());
        System.out.println("Creation Date - " + getDateCreated());
        //Also design the output to show the user how many monthly payments required to pay off the loan amount, 
        //with a date when each payment should be made (chose a fix date for each month).
        //529.93/24 = 22.08
        double years = 0;
        switch ((int) (getAnnualInterestRate() * 10000)) {
            //{0.0495,0.0395,0.0295,0.0195,0.0099}
            case 99:
                years = .5;
                break;
            case 195:
                years = 1;
                break;
            case 295:
                years = 2;
                break;
            case 395:
                years = 4;
                break;
            case 495:
                years = 5;
                break;
        }
        //double bal = (balance*(1+ getAnnualInterestRate()));
        double totalBalance = Math.pow(((1+ getAnnualInterestRate()/12)), (12*years));
        totalBalance *= balance;
//balance * (years + getAnnualInterestRate()); //could be wrong. might wanna calculate the first year and then compound the interest for the next year?
        double amountOfMonthsToPay = (years * 12);
        double monthlyPayments = totalBalance / amountOfMonthsToPay;
        //Also design the output to show the user how many monthly payments required to pay off the loan amount, 
        //with a date when each payment should be made (chose a fix date for each month).
        System.out.println("number of monthly payments required to pay off the loan amount - " + amountOfMonthsToPay + " months at $" + monthlyPayments);
        System.out.println("number of payments made - " + payments);
    }

    String verifyOnlyNumbers(String input, String type) {
        Scanner in = new Scanner(System.in);
        String output = input;
        boolean letters = false;
        for (int i = 0; i < output.length(); i++) {
            if (!Character.isDigit(output.charAt(i))) {
                if(output.charAt(i) == '.') continue;
                letters = true;
            }
            if (letters) {
                System.out.println("error with " + type + ", please enter again");
                output = in.nextLine();
                i = 0;
            }
        }
        return output;
    }

    boolean validateAccount(String loanId, String loanRate, String loanBalance, String command) {
        //A method that will ask the user to enter a valid account ID (only numbers), 
        //a valid balance amount ( > 0 ), and 
        //a valid annual interest rate (interest rate values ranges from 4.95% for 5 years loan, 3.95% for 4 years loan, 2.95% for 2 years loan, 1.95% for 1 year loan and 0.99% for below 6 months loan)
        Scanner input = new Scanner(System.in);
        int accountId = 0;
        double validInterestRates[] = {0.0495, 0.0395, 0.0295, 0.0195, 0.0099};
        double accountRate = 0, accountBalance = 0;
        String bal = loanBalance, rate = loanRate;
        boolean valid = false, balanceCheck = true, interestCheck = false;

        while (valid == false) {

            //accountId check, Just checking to verify the number has no letters
            accountId = Integer.parseInt(verifyOnlyNumbers(loanId, "ID"));

            //balance check, check to verify all numbers and greater than 0
            bal = verifyOnlyNumbers(bal, "Balance");
            accountBalance = Double.parseDouble(bal);
            if (accountBalance < 0) {
                balanceCheck = false;
                System.out.println("error with balance, please enter a balance greater than 0");
                bal = verifyOnlyNumbers(input.next(), "Balance");
            }

            //rates check
            accountRate = Double.parseDouble(verifyOnlyNumbers(rate, "Loan Rate"));
            for (int i = 0; i < validInterestRates.length; i++) {
                if (accountRate == validInterestRates[i]) {
                    interestCheck = true;
                }
            }
            if (!interestCheck) {
                System.out.println("error with interest rate, please enter again");
                rate = verifyOnlyNumbers(input.next(), "Loan Rate");
            }
            
            //loop check
            if (balanceCheck && interestCheck) {
                valid = true;
            }
        }
        //setting may be unneeded. might create empty account and run this to fill data, and return valid
        if(command.equals("create")){
            setAnnualInterestRate(accountRate);
            setBalance(accountBalance);
            setId(accountId);
        }
        
       
        return valid;
    }
}
