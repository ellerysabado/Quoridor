package com.cs309.tutorial.quoridorApp.packets;

public class StartSessionPacket implements IPacket
{
    private boolean login;
    private String username;
    private String password;
    private String name;

    public String getPassword() {
        return password;
    }

    public boolean isLogin() {
        return login;
    }

    public String getUsername() {
        return username;
    }

    public String getName()
    {
        return name;
    }
}
