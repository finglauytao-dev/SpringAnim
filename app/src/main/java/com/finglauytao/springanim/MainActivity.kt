package com.finglauytao.springanim

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val animOneX = SpringAnimation(touch_two, SpringAnimation.TRANSLATION_X);
        val animOneY = SpringAnimation(touch_two, SpringAnimation.TRANSLATION_Y);
        val animTwoX = SpringAnimation(touch_three, SpringAnimation.TRANSLATION_X);
        val animTwoY = SpringAnimation(touch_three, SpringAnimation.TRANSLATION_Y);

        touch_one.setOnTouchListener(object : View.OnTouchListener {

            var lastX = 0f
            var lastY = 0f
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.actionMasked == MotionEvent.ACTION_MOVE) {
                    val dX = event.rawX - lastX;
                    val dY = event.rawY - lastY;
                    touch_one.translationX = dX + v.translationX
                    touch_one.translationY = dY + v.translationY
                    animOneX.animateToFinalPosition(touch_one.translationX)
                    animOneY.animateToFinalPosition(touch_one.translationY)
                }
                lastX = event.rawX
                lastY = event.rawY
                return true
            }

        })

        animOneX.addUpdateListener(object : DynamicAnimation.OnAnimationUpdateListener {
            override fun onAnimationUpdate(animation: DynamicAnimation<out DynamicAnimation<*>>?, value: Float, velocity: Float) {
                animTwoX.animateToFinalPosition(value)
                animTwoX.spring!!.stiffness = SpringForce.STIFFNESS_VERY_LOW
            }

        })
        animOneY.addUpdateListener(object : DynamicAnimation.OnAnimationUpdateListener {
            override fun onAnimationUpdate(animation: DynamicAnimation<out DynamicAnimation<*>>?, value: Float, velocity: Float) {
                animTwoY.animateToFinalPosition(value)
                animTwoY.spring!!.stiffness = SpringForce.STIFFNESS_VERY_LOW
            }

        })

    }
}
