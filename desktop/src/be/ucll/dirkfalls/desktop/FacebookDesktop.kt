package be.ucll.dirkfalls.desktop

import be.ucll.dirkfalls.service.FacebookInterface
import com.badlogic.gdx.Application

class FacebookDesktop : FacebookInterface {
    override lateinit var app: Application
    override fun share(score: Int) {
        print("Score: $score")
    }
}