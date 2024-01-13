package passwordManager;

public interface UiInterface {
        void startUi();

        void setOptions(UiCallback options);

        void setCharSet(char[] set);
        void setSymbolSet(char[] set);

        void setPasswordList(String[] array);

}
