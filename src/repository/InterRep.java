package repository;

import model.Intervention;
import java.io.IOException;


public interface InterRep {
    void addInter(Intervention o);

    void delInter(String m);

    void modifInter(Intervention o) throws IOException;

}
