package be.ucll.dirkfalls.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import be.ucll.dirkfalls.DirkFallsGame;
import be.ucll.dirkfalls.GameConfig.GameConfig;
import be.ucll.dirkfalls.TestGame;
import be.ucll.dirkfalls.samples.ShapeRenderSample;
import be.ucll.dirkfalls.screen.GameScreen;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = GameConfig.WIDTH;
        config.height = GameConfig.HEIGHT;

        //new LwjglApplication(new DirkFallsGame(), config);
        //new LwjglApplication(new ShapeRenderSample(), config);
        new LwjglApplication(new TestGame(), config);
    }
}
