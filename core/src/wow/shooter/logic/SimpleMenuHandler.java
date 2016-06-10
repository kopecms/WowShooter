package wow.shooter.logic;

/**
 * Created by kopec on 2016-05-20.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import wow.shooter.managers.GameManager;

public class SimpleMenuHandler implements Input.TextInputListener {
    GameManager manager;

    String inputedText;

    public boolean getNext = true;
    public boolean connected = false;

    public boolean serverAddr = false;
    public boolean port = false;
    public boolean nick = false;

    public SimpleMenuHandler(GameManager m) {
        manager = m;
    }

    public void drawTextDataDialogs(){
        if(getNext) {
            getNext = false;
            if (!nick) {
                Gdx.input.getTextInput(this, "Username:", manager.playerName, "");
                nick = true;
            } else if (!serverAddr) {
                manager.playerName = inputedText;
                Gdx.input.getTextInput(this, "Server address:", manager.serverAddr, "");
                serverAddr = true;
            } else if (!port) {
                manager.serverAddr = inputedText;
                Gdx.input.getTextInput(this, "Server port:", manager.port, "");
                port = true;
            } else if(port && manager.connected == false){
                manager.port = inputedText;
                connected = true;
                manager.createConnection();
                reset();
            }
        }
    }
    public void reset(){
        getNext = true;
        serverAddr = false;
        port = false;
        nick = false;
    }


    @Override
    public void input(String text) {
        inputedText = text;
        getNext = true;
    }

    @Override
    public void canceled() {
        inputedText = null;
    }

    public String getIntputedText() {
        return inputedText;
    }
}
