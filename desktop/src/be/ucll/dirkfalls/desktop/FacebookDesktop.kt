package be.ucll.dirkfalls.desktop

import be.ucll.dirkfalls.service.FacebookInterface

class FacebookDesktop : FacebookInterface {
    override fun share(score: Int) {
        println("Score: $score")
    }
}