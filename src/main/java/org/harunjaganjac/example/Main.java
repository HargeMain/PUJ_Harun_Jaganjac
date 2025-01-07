package org.harunjaganjac.example;
import org.harunjaganjac.example.seeddata.ProgramSeedData;
import org.harunjaganjac.example.ui.Register;


public class Main {
    public static void main(String[] args) {
        ProgramSeedData.seedSuperAdmin();
        new Register(null);
    }

}