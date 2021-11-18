package com.moody.authentication;

import com.moody.blockchain.BusinessType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class UserBank {
    private static List<User> userList;
    private static User currentUser;

    public static List<User> getUserList() {
        return userList;
    }

    public static void setUserList(List<User> userList) {
        UserBank.userList = userList;
    }

    public static User findUser(String fullName, String password){
        return userList.stream().filter(user-> user.getFullName().equals(fullName)&& user.getPassword().equals(password)).findFirst().orElse(null);
    }

    public static void loadUserData(){
        List<String> userData = null;
        try {
            userData = Files.readAllLines(Path.of("userbank.txt"));
            userList = userData.stream().map(UserBank::convertToUser).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeUserData() {
        StringBuilder stringBuilder = new StringBuilder();
        userList.forEach(user -> stringBuilder.append(user.toString()).append("\n"));
        try {
            Files.writeString(Path.of("userbank.txt"),stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static User convertToUser(String userStr){
        String[] split = userStr.split(",");
        return new User(split[0], split[1], split[2], split[3], BusinessType.valueOf(split[4]), split[5]);
    }

    public static void addUser(User user){
        userList.add(user);
        writeUserData();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserBank.currentUser = currentUser;
    }
}
