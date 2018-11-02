package be.ucll.dirkfalls.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import be.ucll.dirkfalls.DirkFallsGame;
import be.ucll.dirkfalls.samples.ShapeRenderSample;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1080;
        config.height = 720;

        //new LwjglApplication(new DirkFallsGame(), config);
        new LwjglApplication(new ShapeRenderSample(), config);
    }
}
