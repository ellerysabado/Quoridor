package com.cs309.quoridorApp.dataPackets.toServer;

import com.cs309.quoridorApp.dataPackets.toClient.ClientPacket;
import com.cs309.quoridorApp.dataPackets.toClient.ResultPacket;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;
import com.cs309.quoridorApp.repository.SessionRepository;

/**
 * Either sends friend request to given user with username friend
 * (Friend) by given user (User), or accepts friend request from
 * Friend using User's account, if User has a friend request
 * from Friend
 */
public class AddFriendPacket extends SessionGameCheckPacket
{
    private String friend;

    public AddFriendPacket()
    {
        setSessionCheck(true);
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    @Override
    public ClientPacket afterCheck(PlayerRepository players, SessionRepository sessions, PlayerStatsRepository stats, HistoryRepository history)
    {
        if(!players.existsById(friend))
            return new ResultPacket(false, "Player Does Not Exist");
        else if(players.getOne(friend).getFriends().contains(getPlayer(sessions)))
            return new ResultPacket(false, "Already Friends");
        else if(players.getOne(friend).getFriendRequests().contains(getPlayer(sessions)))
            return new ResultPacket(false, "Friend Request Already Sent");

        Player p = getPlayer(sessions);
        Player f = players.getOne(friend);

        if(p.getFriendRequests().contains(f))
        {
            p.getFriendRequests().removeIf(friend ->
                    {return friend.getUsername().equals(friend);}
            );
            p.getFriends().add(f);
            players.save(p);

            f.getRequestedFriends().removeIf(player ->
                    {return player.getUsername().equals(p.getUsername());}
            );
            f.getFriends().add(p);
            players.save(f);
            return new ResultPacket(true, "Accepted Friend Request");
        }
        else
        {
            p.getRequestedFriends().add(f);
            players.save(p);

            f.getFriendRequests().add(p);
            players.save(f);
            return new ResultPacket(true, "Sent Friend Request");
        }
    }
}
