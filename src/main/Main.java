package main;


import model.Customer;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;
import service.AuthService;
import menu.LibraryMainMenu;
import com.password4j.Password;
import com.password4j.Hash;
import com.password4j.types.Argon2;

public class Main {

    public static void main(String[] args) {

        System.out.println("You have landed on the Drumcondra Library page");

        new LibraryMainMenu().start();




    }

}
