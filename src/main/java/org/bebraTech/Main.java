package org.bebraTech;
import DataStorage.JsonStorage;
import Ui.textUi;
import passwordManager.PasswordGenerator;
import passwordManager.PasswordManager;
import passwordManager.UiInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UiInterface ui = new textUi();
        JsonStorage storage = new JsonStorage();
        PasswordManager passManager = new PasswordManager(ui, storage);
    }
}