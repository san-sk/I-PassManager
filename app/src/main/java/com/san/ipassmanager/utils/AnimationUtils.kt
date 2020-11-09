package com.san.ipassmanager.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation


fun View.slideUp(duration: Int = 500) {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
    animate.duration = duration.toLong()
    animate.fillAfter = true
    this.startAnimation(animate)
}

fun View.slideDown(duration: Int = 500) {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
    animate.duration = duration.toLong()
    animate.fillAfter = true
    this.startAnimation(animate)
}

fun rotate(view: View): ObjectAnimator {

    // Rotate the view for a second around its center once

    val animator = ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f)
    animator.duration = 1000
    return animator
}

fun translate(view: View): ObjectAnimator {

    // Translate the view 200 pixels to the right and back

    val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 200f)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE

    return animator
}

fun scale(view: View): ObjectAnimator {

    // Scale the view up to 4x its default size and back

    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
    val animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE
    return animator
}

fun fade(view: View): ObjectAnimator {

    // Fade the view out to completely transparent and then back to completely opaque

    val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE

    return animator
}

fun setFadeAnimation(view: View) {
    val anim = AlphaAnimation(0.0f, 1.0f)
    anim.duration = 500
    view.startAnimation(anim)
}

fun colorizer(view: View): ObjectAnimator {

    // Animate the color of the view's container from black to red over a half
    // second, then reverse back to black. Note that using a propertyName of
    // "backgroundColor" will cause the animator to call the backgroundColor property
    // (in Kotlin) or setBackgroundColor(int) (in Java).

    var animator = ObjectAnimator.ofArgb(
        view.parent,
        "backgroundColor", Color.BLACK, Color.RED
    )
    animator.duration = 500
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE

    return animator
}

fun ObjectAnimator.disableViewDuringAnimation(view: View) {

    // This extension method listens for viewt/end events on an animation and disables
    // the given view for the entirety of that animation.

    addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            view.isEnabled = false
        }

        override fun onAnimationEnd(animation: Animator?) {
            view.isEnabled = true
        }
    })
}

/*fun shower(view: View): ObjectAnimator {

    // Create a new view view in a random X position above the container.
    // Make it rotateButton about its center as it falls to the bottom.

    // Local variables we'll need in the code below
    val container = view.parent as ViewGroup
    val containerW = container.width
    val containerH = container.height
    var viewW: Float = view.width.toFloat()
    var viewH: Float = view.height.toFloat()

    // Create the new view (an ImageView holding our drawable) and add it to the container
    val newview = AppCompatImageView(this)
    newview.setImageResource(R.drawable.ic_view)
    newview.layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )
    container.addView(newview)

    // Scale the view randomly between 10-160% of its default size
    newview.scaleX = Math.random().toFloat() * 1.5f + .1f
    newview.scaleY = newview.scaleX
    viewW *= newview.scaleX
    viewH *= newview.scaleY

    // Position the view at a random place between the left and right edges of the container
    newview.translationX = Math.random().toFloat() * containerW - viewW / 2

    // Create an animator that moves the view from a viewting position right about the container
    // to an ending position right below the container. Set an accelerate interpolator to give
    // it a gravity/falling feel
    val mover = ObjectAnimator.ofFloat(newview, View.TRANSLATION_Y, -viewH, containerH + viewH)
    mover.interpolator = AccelerateInterpolator(1f)

    // Create an animator to rotateButton the view around its center up to three times
    val rotator = ObjectAnimator.ofFloat(
        newview, View.ROTATION,
        (Math.random() * 1080).toFloat()
    )
    rotator.interpolator = LinearInterpolator()

    // Use an AnimatorSet to play the falling and rotating animators in parallel for a duration
    // of a half-second to two seconds
    val set = AnimatorSet()
    set.playTogether(mover, rotator)
    set.duration = (Math.random() * 1500 + 500).toLong()

    // When the animation is done, remove the created view from the container
    set.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            container.removeView(newview)
        }
    })

    // start the animation
    return set
}*/
