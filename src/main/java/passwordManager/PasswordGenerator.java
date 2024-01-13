package passwordManager;

import java.util.Random;

public class PasswordGenerator {
    private char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private char[] symbol = {'!', '_', '-', '@', '#', '$', '*'};
    private final Random rand = new Random();

    public String genPassword(int length, boolean symbols, boolean numbers, boolean lowerCaseCharacters, boolean topCaseCharacters) {
        String output ="";
        int symbolCount =0;
        int numberCount =0;
        int lowerCount = 0;
        int upperCount =0;

        for(int charIndex =0; charIndex< length; charIndex++){
            int randomType = rand.nextInt(4);
            switch (randomType){
                case 0:
                    if (symbols){
                        output += symbol[rand.nextInt(symbol.length)];
                        symbolCount++;
                    }
                    break;
                case 1:
                    if (numbers){
                        output += rand.nextInt(10);
                        numberCount++;
                    }
                    break;
                case 2:
                    if (lowerCaseCharacters){
                        output += characters[rand.nextInt(characters.length)];
                        lowerCount++;
                    }
                    break;
                case 3:
                    if (topCaseCharacters){
                        output += Character.toUpperCase(characters[rand.nextInt(characters.length)]);
                        upperCount++;
                    }
                    break;
            }
        }

        if (symbolCount == 0 && symbols){
            String[] splitedString = splitStringRandomly(output);
            output = splitedString[0] + symbol[rand.nextInt(symbol.length)] + splitedString[1];
        }
        if (numberCount == 0 && numbers){
            String[] splitedString = splitStringRandomly(output);
            output = splitedString[0] + rand.nextInt(10) + splitedString[1];
        }
        if (lowerCount == 0 && lowerCaseCharacters){
            String[] splitedString = splitStringRandomly(output);
            output = splitedString[0] + characters[rand.nextInt(characters.length)] + splitedString[1];
        }
        if (upperCount == 0 && topCaseCharacters){
            String[] splitedString = splitStringRandomly(output);
            output = splitedString[0] + Character.toUpperCase(characters[rand.nextInt(characters.length)]) + splitedString[1];
        }
        return output;
    }

    private String[] splitStringRandomly(String string){
        int randomPlace = rand.nextInt(string.length());
        String[] output = new String[2];
        output[0] = string.substring(0,randomPlace);
        output[1] = string.substring(randomPlace+1, string.length());
        return output;
    }

    public void setSymbolSet(char[] newSet){
        symbol = newSet;
    }

    public void setCharacters(char[] newSet){
        characters = newSet;
    }

    public char[] getSymbol(){
        return symbol;
    }

    public char[] getCharacters() {
        return characters;
    }
}
