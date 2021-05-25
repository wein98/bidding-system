package com.matchingSystem.Model;

import com.matchingSystem.BiddingSystem.Subject;
import com.matchingSystem.ContractDev.Contract;
import com.matchingSystem.LoginSystem.Tutor;
import com.matchingSystem.LoginSystem.UserCookie;

import java.util.ArrayList;
import java.util.Observable;

public class RenewContractModel extends Observable {
    private final boolean newTutor;
    private final Contract contract;
    private ArrayList<Contract> contracts = new ArrayList<>();
    private ArrayList<Tutor> tutors = new ArrayList<>();

    public RenewContractModel(boolean newTutor, Contract contract) {
        this.newTutor = newTutor;
        this.contract = contract;

        if (!newTutor) {
            loadPreviousContracts();
        } else {
            loadMatchingTutors();
        }
    }

    public void loadPreviousContracts() {
        contracts = UserCookie.getUser().getContracts();

        setChanged();
        notifyObservers();
    }

    public void loadMatchingTutors() {
        tutors = contract.getTutors();

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

    public ArrayList<Tutor> getTutors() {
        return tutors;
    }

    public Subject getSubject() {
        return contract.getSubject();
    }
}
