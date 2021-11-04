package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import java.util.*;

public class BankReport {
    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public int getNumberOfAccounts(Bank bank) {
        var totalAccounts = 0;
        for (var client : bank.getClients()) {
            totalAccounts += client.getAccounts().size();
        }
        return totalAccounts;
    }

    public SortedSet<Client> getClientsSorted(Bank bank) {
        SortedSet<Client> sortedClients = new TreeSet<>(Comparator.comparing(Client::getName));
        sortedClients.addAll(bank.getClients());
        return sortedClients;
    }

    public double getTotalSumInAccounts(Bank bank) {
        double totalSum = 0;
        for (var client : bank.getClients()) {
            for (var account : client.getAccounts()) {
                totalSum += account.getBalance();
            }
        }
        return totalSum;
    }

    public SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        SortedSet<Account> sortedAccounts = new TreeSet<>(Comparator.comparingDouble(Account::getBalance));
        for (var client : bank.getClients()) {
            var accounts = client.getAccounts();
            sortedAccounts.addAll(accounts);
        }
        return sortedAccounts;
    }

    public double getBankCreditSum(Bank bank) {
        double sum = 0.0;
        for (var client : bank.getClients()) {
            var accounts = client.getAccounts();
            for (var account : accounts) {
                if (account instanceof CheckingAccount) {
                    sum += account.getBalance();
                }
            }
        }
        return sum;
    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        Map<Client, Collection<Account>> customerAccounts = new HashMap<>();
        for (var client : bank.getClients()) {
            customerAccounts.put(client, client.getAccounts());
        }
        return customerAccounts;
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        SortedSet<String> cities = new TreeSet<>();
        Map<String, List<Client>> clientsByCity = new TreeMap<>();
        for (var client : bank.getClients()) {
            cities.add(client.getCity());
        }
        for (var city : cities) {
            List<Client> clients = new ArrayList<>();
            for (var client : bank.getClients()) {
                if (client.getCity().equals(city)) {
                    clients.add(client);
                }
            }
            clientsByCity.put(city, clients);
        }
        return clientsByCity;
    }
}
