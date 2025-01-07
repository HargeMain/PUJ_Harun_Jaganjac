package org.harunjaganjac.example;
import org.harunjaganjac.example.datacontext.DataContext;
import org.harunjaganjac.example.seeddata.ProgramSeedData;
import org.harunjaganjac.example.ui.Register;

import javax.xml.crypto.Data;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        while(true){
            try {
                System.out.println("Do you wish to connect to Localhost or Cloud?");
                System.out.println("1. Localhost");
                System.out.println("2. Cloud");
                Scanner scanner = new Scanner(System.in);
                var choice = scanner.nextLine();
                if (choice.equals("1")) {
                    DataContext.CONNECTION_STRING="mongodb://localhost:27017";
                    break;
                } else if (choice.equals("2")) {
                    DataContext.CONNECTION_STRING="mongodb+srv://HargeAdmin:TestDashboard@hrmanagmentsystem.899ra.mongodb.net/";
                    break;
                }else{
                    System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        ProgramSeedData.seedSuperAdmin();
        new Register(null);
    }

}