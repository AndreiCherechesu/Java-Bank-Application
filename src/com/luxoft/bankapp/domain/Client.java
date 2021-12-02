package com.luxoft.bankapp.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Client {
	
	private String name;
	private Gender gender;
	private String city;
	private LocalDate birthday;
	private Set<Account> accounts = new LinkedHashSet<Account>();

	public Client(String name, Gender gender, String city, LocalDate birthday) {
		this.name = name;
		this.gender = gender;
		this.city = city;
		this.birthday = birthday;
	}
	
	public void addAccount(final Account account) {
		accounts.add(account);
	}
	
	public String getName() {
		return name;
	}
	
	public Gender getGender() {
		return gender;
	}

	public String getCity() { return city; }
	
	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}
	
	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	@Override
	public String toString() {
		return getClientGreeting();
	}

}
