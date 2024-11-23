package repository;

import model.Outils;
import java.io.IOException;
import java.util.List;

public interface OutilsRepository {
    void addOutil(Outils o);

    void deleteOutil(String m);

    void modifierOutil(int value1,String v, int value2,String value3, String value5, String valuex) throws IOException;

}



