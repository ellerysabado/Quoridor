package com.cs309.quoridorApp.packets;

public class StartSessionPacket implements IPacket
{
    private boolean login;
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public boolean isLogin() {
        return login;
    }

    public String getUsername() {
        return username;
    }
}
