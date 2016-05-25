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

    public void getData(){
        if(getNext) {
            getNext = false;
            if (!nick) {
                Gdx.input.getTextInput(this, "Username:", manager.playerName, "");
                nick = true;
            } else if (!serverAddr) {
                Gdx.input.getTextInput(this, "Username:", manager.serverAddr, "np. localhost");
                serverAddr = true;
            } else if (!port) {
                Gdx.input.getTextInput(this, "Username:", manager.port, "np. 5055");
                port = true;
                connected = true;
            }
        }
    }

    @Override
    public void input(String text) {
        System.out.print(text);
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
