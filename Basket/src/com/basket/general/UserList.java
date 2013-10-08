package com.basket.general;

import java.util.List;

public class UserList
{
	public List< User > users;

	public List< User > getResults() {
		return users;
	}

	public void setResults( List< User > results ) {
		this.users = results;
	}

	@Override
	public String toString() {
		return "UserList [users=" + users + "]";
	}
}
