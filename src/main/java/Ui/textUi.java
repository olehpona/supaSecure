package Ui;
import passwordManager.UiCallback;
import passwordManager.UiInterface;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class textUi implements UiInterface {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private UiCallback options = null;

    private ArrayList<Character> symbolSet = new ArrayList<>();
    private ArrayList<Character> charSet = new ArrayList<>();

    private ArrayList<String> passwordList = new ArrayList<>();

    Scanner input = new Scanner(System.in);

    @Override
    public void setOptions(UiCallback option){
        options = option;
    }

    @Override
    public void setPasswordList(String[] array){
        passwordList = StringArrayToArrayList(array);
    }

    @Override
    public void startUi(){
        SetLocation();
        Load();
        while (true){
            System.out.println(ANSI_PURPLE +  "Press Enter to Continue" + ANSI_RESET);
            input.nextLine();
            System.out.println("If you want quit enter 0, if you want go to settings enter 1, 2 to open saved password, else you will go to password generation");
            String enteredMode = input.nextLine();
            if (enteredMode.equals("0")){
                System.out.println(":(");
                System.exit(0);
            } else if (enteredMode.equals("1")){
                Setup();
                PasswordGen();
            } else if (enteredMode.equals("2")){
                passwordPage();
                PasswordGen();
            } else {
                PasswordGen();
            }
        }
    }

    private void SetLocation(){
        while (true) {
            System.out.println(ANSI_YELLOW + "Please enter config file location" + ANSI_RESET);
            String location = input.nextLine();
            System.out.println(ANSI_YELLOW+ "You entered " + location + ANSI_RESET);
            System.out.println("Enter 1 to save location or else to retry");
            if (input.nextInt() == 1){
                options.SetLocation(location);
                break;
            }
            input.nextLine();
        }
    }

    private void Load(){
        System.out.println("If you want to load sets from config file enter 1,else skip");
        if(input.nextInt() == 1){
            options.Load();
            System.out.println("You loaded");
            System.out.println("This char set: " + charSet);
            System.out.println("This symbols set: " + symbolSet);
            return;
        }
        System.out.println("Loaded default configuration");
        System.out.println("This char set: " + charSet);
        System.out.println("This symbols set: " + symbolSet);
        System.out.println("If you want to save default configuration enter 1");
        if(input.nextInt() == 1){
            options.Save(listToCharArray(symbolSet), listToCharArray(charSet), ArrayListToStringArray(passwordList));
        }
    }

    private void Setup(){
        System.out.println(ANSI_GREEN + "You are entered in setup page" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "If you want to setup enter 1, 0 to next page" + ANSI_RESET);
        if (input.nextInt() == 1){
            symbolSet = setSymbolSet();
            charSet = setCharSet();
            options.SetCharSet(listToCharArray(charSet));
            options.SetSymbolSet(listToCharArray(symbolSet));
            System.out.println(ANSI_YELLOW + "If you want to save your configuration to file enter 1,else skip" + ANSI_RESET);
            if(input.nextInt() == 1){
                options.Save(listToCharArray(symbolSet), listToCharArray(charSet), ArrayListToStringArray(passwordList));
            }

        }
    }


    private void passwordPage(){
        System.out.println("Your saved passwords");
        if (passwordList.isEmpty()){
            System.out.println("Your list is empty");
        } else {
            for (String password: passwordList){
                System.out.println(password);
            }
        }
    }

    private void PasswordGen(){
        System.out.println(ANSI_PURPLE + "Press enter to continue" + ANSI_RESET);
        input.nextLine();
        System.out.println(ANSI_GREEN + "Password generation" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Enter password length");
        int len = input.nextInt();
        input.nextLine();
        System.out.println("Enter if you want special symbols there (1 for true, 0 for false)");
        boolean isSymbols = input.nextInt() == 1;
        input.nextLine();
        System.out.println("Enter if you want numbers there (1 for true, 0 for false)");
        boolean isNumber = input.nextInt() == 1;
        input.nextLine();
        System.out.println("Enter if you want lower case character there (1 for true, 0 for false)");
        boolean isLower = input.nextInt() == 1;
        input.nextLine();
        System.out.println("Enter if you want upper case character there (1 for true, 0 for false)" + ANSI_RESET);
        boolean isUpper = input.nextInt() == 1;
        input.nextLine();
        String password = options.GenPassword(len, isSymbols, isNumber, isLower, isUpper);
        System.out.println(ANSI_CYAN + "Your password is: "+ password + ANSI_RESET);
        System.out.print("If you want to save your password enter 1, else skip");
        if( input.nextInt() == 1){
            input.nextLine();
            while (true){
                System.out.println("enter password name");
                String name = input.nextLine();
                System.out.println("Your password is (" + name + ") " + password);
                System.out.println("If you want to save this password enter 1, 0 to retry, else skip");
                int in = input.nextInt();
                if (in == 1){
                    passwordList.add("(" + name + ") " + password);
                    options.Save(listToCharArray(symbolSet), listToCharArray(charSet), ArrayListToStringArray(passwordList));
                    break;
                } else if (in != 0){
                    break;
                }
                input.nextLine();
            }


        }
    }

    private ArrayList<Character> setSymbolSet(){
        input.nextLine();
        System.out.println(ANSI_RED + "Please enter new symbol set. Write all symbol separated by (,) or enter D" + ANSI_RESET);
        System.out.println("Default value is " + symbolSet);
        String enteredValue = input.nextLine();
        if (Objects.equals(enteredValue, "D")){
            System.out.println("You set default symbol set");
            return symbolSet;
        } else {
            System.out.println(ANSI_YELLOW + "You entered this symbol set: " + enteredValue + ANSI_RESET);
            System.out.println("Enter 1 to set symbol set or 0 to restart");
            if(input.nextInt() == 0){
                return setSymbolSet();
            } else {
                return splitStringToList(enteredValue);
            }
        }
    }

    private ArrayList<Character> setCharSet(){
        System.out.println(ANSI_PURPLE + "Press Enter to continue" + ANSI_RESET);
        input.nextLine();
        System.out.println(ANSI_RED + "Please enter new char set. Write all char lower case and separated by (,) or enter D" + ANSI_RESET );
        System.out.println("Default value is " + charSet);
        String enteredValue = input.nextLine();
        if (Objects.equals(enteredValue, "D")){
            System.out.println("You set default char set");
            return charSet;
        } else {
            System.out.println(ANSI_YELLOW + "You entered this char set: " + enteredValue + ANSI_RESET);
            System.out.println("Enter 1 to set char set or 0 to restart");
            if(input.nextInt() == 0){
                return setCharSet();
            } else {
                return splitStringToList(enteredValue);
            }
        }
    }

    private char[] listToCharArray(ArrayList<Character> list){
        char[] newCharArray = new char[list.size()];
        for (int index =0; index< list.size(); index++ ){
            newCharArray[index] = list.get(index);
        }
        return newCharArray;
    }

    private ArrayList<Character> splitStringToList(String input){
        String[] parsed = input.split(",");
        ArrayList<Character> newList = new ArrayList<>();
        for (String chars : parsed){
            newList.add(chars.toCharArray()[0]);
        }
        return newList;
    }

    @Override
    public void setCharSet(char[] charSet) {
        this.charSet = charArrayToArrayList(charSet);
    }

    @Override
    public void setSymbolSet(char[] symbolSet) {
        this.symbolSet = charArrayToArrayList(symbolSet);
    }

    private ArrayList<Character> charArrayToArrayList(char[] array){
        ArrayList<Character> newArrayList = new ArrayList<>();
        for (char chars : array){
            newArrayList.add(chars);
        }
        return newArrayList;
    }

    private ArrayList<String> StringArrayToArrayList(String[] array){
        ArrayList<String> newArrayList = new ArrayList<>();
        for (String string: array){
            newArrayList.add(string);
        }
        return newArrayList;
    }

    private String[] ArrayListToStringArray(ArrayList<String> array){
        String[] newArray = new String[array.size()];
        for (int index =0; index< array.size(); index++){
            newArray[index] = array.get(index);
        }
        return newArray;
    }



}
