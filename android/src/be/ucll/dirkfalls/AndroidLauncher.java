package be.ucll.dirkfalls;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.Objects;

import be.ucll.dirkfalls.DirkFallsGame;
import be.ucll.dirkfalls.utils.TouchHandler;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new TestGame(), config);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Objects.requireNonNull(getCurrentFocus()).setOnTouchListener(new View.OnTouchListener() { //TouchListener checkt voor touches
            @Override
            public boolean onTouch(View view, MotionEvent event) { //Wat gebeurd er als er een touch is
                float x = event.getX()/view.getWidth(); //Relatief punt op scherm (zie TouchHandler voor werkelijk punt)
                float y = event.getY()/view.getHeight();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: //Welk soort touch (hier bewegend)
                        TouchHandler.INSTANCE.setTouch(new float[]{x, y}); //Check TouchHandler
                        view.performClick(); //Niet persé nodig ma anders klaagt hij
                    case MotionEvent.ACTION_DOWN: //Hier gwn ingedrukt
                        TouchHandler.INSTANCE.setTouch(new float[]{x, y});
                        view.performClick();
                }
                return true;
            }
        });
	}
}
