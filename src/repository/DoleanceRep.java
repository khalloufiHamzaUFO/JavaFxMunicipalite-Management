package repository;

import model.Doleance;

import java.io.IOException;

public interface DoleanceRep {
    void adddol(Doleance d);

    void deleteDol(String m);

    void modifierDol(Doleance d) throws IOException;
}


