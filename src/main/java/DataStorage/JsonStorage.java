package DataStorage;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import passwordManager.DataObject;
import passwordManager.StorageInterface;

import java.io.*;

public class JsonStorage implements StorageInterface {

    String fileLocation;

    GsonBuilder builder = new GsonBuilder();
    Gson gson;

    File configFile;
    FileReader fileReader;

    String location;

    @Override
    public void init(String location) {
        this.location = location;
        try {
            configFile = new File(location);
            configFile.createNewFile();
            fileReader = new FileReader(configFile);
            builder.setPrettyPrinting();
            gson = builder.create();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void Save(DataObject data) {
        try {
            String json = gson.toJson(data);
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile, false));
            writer.write(json);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);

        }
    }

    @Override
    public DataObject Load() {
        return gson.fromJson(fileReader, DataObject.class);
    }

}
