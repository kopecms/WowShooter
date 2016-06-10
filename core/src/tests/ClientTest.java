package tests;


import org.junit.Test;
import wow.shooter.logic.Client;
import wow.shooter.managers.DataStore;
import wow.shooter.managers.GameManager;

import static org.junit.Assert.*;

/**
 * Created by kopec on 2016-05-25.
 */
public class ClientTest {
    Client client = new Client();
    @Test
    public void cannotConnect() throws Exception {
       assertFalse(client.connect("",1,new GameManager(new DataStore())));
    }

    @Test
    public void disconnect() throws Exception {
        client.disconnect();
        assertFalse(client.connected);
    }

    @Test
    public void sending() throws Exception {
        assertArrayEquals(client.send(new byte[]{0,1,2}),new byte[]{0,1,2});
    }
}