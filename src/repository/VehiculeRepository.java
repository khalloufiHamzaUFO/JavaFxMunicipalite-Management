package repository;

import model.Vehicule;
import java.io.IOException;
import java.util.List;

public interface VehiculeRepository {

        void  addVehicule(Vehicule v) ;
        void  DeleteVehicule(String m);
        void  modifiermVehiculeDAO(Vehicule v)throws IOException;
        List<Vehicule> getDataVehicules();
    }

