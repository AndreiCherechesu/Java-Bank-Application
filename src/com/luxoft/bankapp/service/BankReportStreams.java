package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BankReportStreams {
    public int getNumberOfClients(Bank bank) {
        return (int) bank.getClients().stream().count();
    }
    public int getNumberOfAccounts(Bank bank) {
        return bank.getClients().stream().map(client -> client.getAccounts().size()).reduce(0, Integer::sum);
    }
    public SortedSet getClientsSorted(Bank bank) {
        return bank.getClients().stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Client::getName))));
    }
    public double getTotalSumInAccounts(Bank bank) {
        Function<Client, Double> totalSumPerClient = (client) ->
                client.getAccounts().stream().map(Account::getBalance).reduce(0.0, Double::sum);

        return bank.getClients().stream().map(totalSumPerClient).reduce(0.0, Double::sum);
    }

    public SortedSet getAccountsSortedBySum(Bank bank) {
        return bank.getClients().stream()
                .map(Client::getAccounts)
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Account::getBalance))));
    }

    public double getBankCreditSum(Bank bank) {
        return (-1.0) * bank.getClients().stream()
                .map(Client::getAccounts)
                .flatMap(Set::stream)
                .filter(account -> account instanceof CheckingAccount)
                .map(Account::getBalance)
                .filter(balance -> Double.compare(balance, 0) < 0)
                .reduce(0.0, Double::sum);
    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        return bank.getClients().stream()
                .collect(Collectors.toMap(Function.identity(), Client::getAccounts));
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        return new TreeMap<>(bank.getClients().stream().collect(Collectors.groupingBy(Client::getCity)));
    }

    public Map<String, List<Client>> getClientsByBirthdayMonth(Bank bank) {
        return new TreeMap<>(bank.getClients().stream().collect(Collectors.groupingBy(
                client -> client.getBirthday().getMonth().name())));
    }
}
