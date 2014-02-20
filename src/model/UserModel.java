package model;

import java.util.Collection;

public interface UserModel {
	boolean checkUser(String userId);
	User getUserById(String userID);
	Collection<User> getUserList();
	void addUser(User user);
	void removeUser(User user);
	
	
}