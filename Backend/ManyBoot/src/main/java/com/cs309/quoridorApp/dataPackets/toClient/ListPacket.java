package com.cs309.quoridorApp.dataPackets.toClient;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple packet used to list any object
 */
public class ListPacket<A> extends ClientPacket
{
    private List<A> list = new ArrayList<>();

    public ListPacket(List<A> list)
    {
        setResult(true);

        this.list = list;
    }

    public void setList(List<A> list) {
        this.list = list;
    }

    public List<A> getList() {
        return list;
    }
}
