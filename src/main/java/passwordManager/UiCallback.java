package passwordManager;

public interface UiCallback {
    void SetCharSet(char[] chars);
    void SetSymbolSet(char[] symbols);
    String GenPassword(int len, boolean isSymbol,boolean isNumber, boolean isLower, boolean isUpper);
    void Load();
    void Save(char[] symbols, char[] chars, String[] paswords);

    void SetLocation(String location);

}
