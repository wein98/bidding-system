package com.matchingSystem.Model;

import com.matchingSystem.ContractDev.Contract;
import com.matchingSystem.LoginSystem.UserCookie;

import java.util.ArrayList;
import java.util.Observable;

public class RenewContractModel extends Observable {
    private final boolean newTutor;
    private ArrayList<Contract> contracts = new ArrayList<>();

    public RenewContractModel(boolean newTutor) {
        this.newTutor = newTutor;

        if (!newTutor) {
            loadPreviousContracts();
        }
    }

    public void loadPreviousContracts() {
        contracts = UserCookie.getUser().getContracts();

        setChanged();
        notifyObservers();
    }

    public boolean isNewTutor() {
        return newTutor;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public Contract getContract(int index) {
        Contract c = contracts.get(index);
        return c.clone();
    }
}
