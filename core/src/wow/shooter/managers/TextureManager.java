package wow.shooter.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kopec on 2016-03-22.
 */
public class TextureManager {
    private Map<String, Texture> textures = new HashMap<String, Texture>();

    public TextureManager() {
        textures.put("player", new Texture("core/assets/player.png"));
        textures.put("player2", new Texture("core/assets/player2.png"));
        textures.put("gun", new Texture("core/assets/gun.png"));
        textures.put("bullet", new Texture("core/assets/bullet.png"));
        textures.put("box", new Texture("core/assets/box.png"));
        textures.put("menuBox", new Texture("core/assets/MenuBox.png"));
    };
    public void dipose(){
        for(Texture texture: textures.values()){
            texture.dispose();
        }
    }

    public Texture getTexture(String textureName){
        return textures.get(textureName);
    }
}

