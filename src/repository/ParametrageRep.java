package repository;

import model.Parametrage;

import java.io.IOException;
import java.util.List;

public interface ParametrageRep {

        void  addParametre(Parametrage p) ;
        void  DeleteParametrage(String m);
        void  modifierParametrage(Parametrage p)throws IOException;
        List<Parametrage> getDataParametre();
    }


