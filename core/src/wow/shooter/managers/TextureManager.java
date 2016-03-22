package wow.shooter.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kopec on 2016-03-22.
 */
public class TextureManager {
    public Map<String, Texture> textures = new HashMap<String, Texture>();

    public TextureManager() {
        textures.put("player", new Texture("core/assets/player.png"));
    };

    public Texture getTexture(String textureName){
        return textures.get(textureName);
    }
}

