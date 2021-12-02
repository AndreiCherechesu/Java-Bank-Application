package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.annotations.ActiveRecord;
import com.luxoft.bankapp.annotations.ActiveRecordEntity;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.lang.annotation.Annotation;

public abstract class AbstractAccount extends ActiveRecord implements Account {
	
	private int id;
	protected double balance;
	
	public AbstractAccount(int id, double balance) {
		this.id = id;
		this.balance = balance;
	}

	@Override
	public void deposit(final double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot deposit a negative amount");
		}
		this.balance += amount;
	}

	@Override
	public void withdraw(final double amount) throws NotEnoughFundsException {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw a negative amount");
		}
		
		if (amount > maximumAmountToWithdraw()) {
			throw new NotEnoughFundsException(id, balance, amount, "Requested amount exceeds the maximum amount to withdraw");
		}
		
		this.balance -= amount;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public void getById(int id) {
		Class<? extends AbstractAccount> cls = this.getClass();
		String tableName = null;
		String keyColumnName = null;

		Annotation[] annotations = cls.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof ActiveRecordEntity) {
				ActiveRecordEntity activeRecordEntity = (ActiveRecordEntity) annotation;
				tableName = activeRecordEntity.tableName();
				keyColumnName = activeRecordEntity.keyColumnName();
			}
		}

		if (tableName != null && keyColumnName != null) {
			System.out.println("SELECT * FROM " + tableName +
					" WHERE " + keyColumnName + " = " + id);
		}
	}

	@Override
	public void getAll() {
		Class<? extends AbstractAccount> cls = this.getClass();
		String tableName = null;

		Annotation[] annotations = cls.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof ActiveRecordEntity) {
				ActiveRecordEntity activeRecordEntity = (ActiveRecordEntity) annotation;
				tableName = activeRecordEntity.tableName();
			}
		}

		if (tableName != null) {
			System.out.println("SELECT * FROM " + tableName);
		}
	}
}
