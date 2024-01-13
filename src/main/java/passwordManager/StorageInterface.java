package passwordManager;

public interface StorageInterface {
    void Save(DataObject data);
    DataObject Load();
    void init(String location);
}
