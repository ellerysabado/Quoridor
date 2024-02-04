package com.cs309.quoridorApp.packets;

public class AddFriendPacket implements IPacket
{

    private String id;
    private String friend;

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}
