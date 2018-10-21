package dirk.ucll.be.dirkfalls.renderer

import dirk.ucll.be.dirkfalls.Board

class SysoutRenderer : BoardRenderer {
    override fun render(board: Board) {
        for(entity in board.entities) {
            println("Entity on (${entity.position.x}, ${entity.position.y})")
        }
    }

}