package repository;

import model.Revenu;
import java.io.IOException;

public interface RevenusRep {

    void addRev(Revenu o);
    void deleteRev(String m);
    void modifRev(int value1,String value2,String value3, String value4, String value5, String value6) throws IOException;

}