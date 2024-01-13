package passwordManager;

public class PasswordManager {

    PasswordGenerator generator = new PasswordGenerator();

    UiInterface ui;

    public PasswordManager(UiInterface ui, StorageInterface storage){
        this.ui = ui;
        UiCallback options = new UiCallback() {
            @Override
            public void SetCharSet(char[] chars) {
                generator.setCharacters(chars);
            }

            @Override
            public void SetSymbolSet(char[] symbols) {
                generator.setSymbolSet(symbols);
            }

            @Override
            public String GenPassword(int len, boolean isSymbol, boolean isNumber, boolean isLower, boolean isUpper) {
                return generator.genPassword(len, isSymbol, isNumber, isLower, isUpper);
            }

            @Override
            public void Load(){
                DataObject data = storage.Load();
                ui.setPasswordList(data.savedPasswords);
                SetSets(StringToCharArray(data.symbolSet) , StringToCharArray(data.charSet));
            }

            @Override
            public void Save(char[] symbols, char[] chars, String[] strings){
                DataObject data = new DataObject();
                data.charSet = CharArrayToString(chars);
                data.symbolSet = CharArrayToString(symbols);
                data.savedPasswords = strings;
                storage.Save(data);
            }

            @Override
            public void SetLocation(String location) {
                storage.init(location);
            }
        };
        ui.setOptions(options);
        SetSets(generator.getSymbol(), generator.getCharacters());
        ui.startUi();
    }

    private void SetSets(char[] symbols, char[] chars){
        ui.setSymbolSet(symbols);
        ui.setCharSet(chars);
        generator.setSymbolSet(symbols);
        generator.setCharacters(chars);
    }

    private char[] StringToCharArray(String input){
        String[] splited = input.split(",");
        char[] charArray = new char[splited.length];
        for (int index = 0; index< splited.length; index++){
            charArray[index] = splited[index].toCharArray()[0];
        }
        return charArray;
    }

    private String CharArrayToString(char[] charArray){
        String output = "";
        for (int index = 0; index< charArray.length; index++){
            output += charArray[index];
            if (index < charArray.length-1){
                output += ",";
            }
        }
        return output;
    }
}
