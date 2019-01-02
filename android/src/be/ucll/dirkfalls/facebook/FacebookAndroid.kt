package be.ucll.dirkfalls.facebook

import android.graphics.*
import be.ucll.dirkfalls.service.FacebookInterface
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.utils.Array
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import de.tomgrill.gdxfacebook.core.*


class FacebookAndroid(override val app: Application) : FacebookInterface {
    override fun share(score: Int) {
        val permissions = Array<String>()
        val gdxFacebook = GDXFacebookSystem.install(FacebookConfig())
        gdxFacebook.signIn(SignInMode.PUBLISH, permissions, object : GDXFacebookCallback<SignInResult> {
            override fun onSuccess(result: SignInResult) {
                print("Success")
            }

            override fun onError(error: GDXFacebookError) {
                print(error.errorMessage)
            }

            override fun onCancel() {
                print("Canceled")
            }

            override fun onFail(t: Throwable) {
                print(t.stackTrace)
            }
        })

        val src = BitmapFactory.decodeStream(Gdx.files.internal("backgrounds/backgroundLevel1.jpeg").read())
        val dest = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)

        val yourText = "I got a score of $score on Dirk Falls!"

        val cs = Canvas(dest)
        val paint = Paint()
        paint.textSize = 35f
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        cs.drawBitmap(src, 0f, 0f, null)
        val height = paint.measureText("yY")
        val width = paint.measureText(yourText)
        val xCoord = (src.width - width) / 2
        val yCoord = (src.height - height) / 2
        cs.drawText(yourText, xCoord, yCoord, paint) // 15f is to put space between top edge and the text, if you want to change it, you can


        val dialog = ShareDialog(app as AndroidApplication)
        val photo = SharePhoto.Builder()
                .setBitmap(dest)
                .build()
        val content = SharePhotoContent.Builder()
                .addPhoto(photo)
                .build()
        dialog.show(content)
    }
}