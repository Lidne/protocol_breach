package scenes;

import entities.Player;

public interface SceneLoad {
    void loadAssets();
    void loadAssets(Player player);
    Player clearAssets();
}
