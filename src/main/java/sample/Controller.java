package sample;

import sample.ModelLogic.Model;

public class Controller {
    // Usecases we will implement:
    //
    // 1) UC1: add update to an event
    // 2) UC2: view update history
    // 3) UC3: publish an event

    Model m;

    public void setModel(Model m) {
        this.m = m;
    }
}
