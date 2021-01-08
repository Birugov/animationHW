package ru.techcrat.homework3sem

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.View
import android.widget.Button
import android.transition.Scene.getSceneForLayout
import android.view.animation.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.second_scene.materialButton
import kotlinx.android.synthetic.main.second_scene.materialButton2


class MainActivity : AppCompatActivity() {

    lateinit var hiddenButton: Button
    lateinit var sceneRoot: ConstraintLayout
    lateinit var scene1: Scene
    lateinit var scene2: Scene
    var sceneChangeFlag:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneRoot = container

        scene1 = getSceneForLayout(sceneRoot, R.layout.first_scene, this)
        scene2 = getSceneForLayout(sceneRoot, R.layout.second_scene, this)


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun onClick(view: View) {


        if (!sceneChangeFlag) {
            hiddenButton = findViewById(R.id.materialButton2)
            val animationRotate =
                AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_reverse).apply {
                    setAnimationListener(animationListener)
                }
            hiddenButton.startAnimation(animationRotate)
            sceneChangeFlag=true

        } else {

            val set = TransitionSet()

            set.addTransition(Fade().apply {
                duration = 600
                addTarget(materialButton2.id)
            })

            set.addTransition(ChangeBounds().apply {
                duration = 250
                interpolator = AccelerateInterpolator()
                addTarget(materialButton.id)
            })

            set.ordering = TransitionSet.ORDERING_TOGETHER
            set.addListener(firstSceneTransitionListener)
            TransitionManager.go(scene2, set)
            sceneChangeFlag=false

        }

     }


    private val firstSceneTransitionListener = object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition?) {
            hiddenButton = findViewById(R.id.materialButton2)
            val animationRotate =
                AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
            hiddenButton.startAnimation(animationRotate)


        }

        override fun onTransitionResume(transition: Transition?) {
        }

        override fun onTransitionPause(transition: Transition?) {

        }

        override fun onTransitionCancel(transition: Transition?) {

        }

        override fun onTransitionStart(transition: Transition?) {

        }

    }

    private val animationListener = object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {

            val reverseSet = TransitionSet()
            reverseSet.addTransition(Fade().apply {
                duration = 500
                addTarget(materialButton2.id)
            })

            reverseSet.addTransition(ChangeBounds().apply {
                duration = 500
                interpolator = AccelerateInterpolator()
                addTarget(materialButton.id)
            })
            reverseSet.ordering = TransitionSet.ORDERING_TOGETHER
            TransitionManager.go(scene1, reverseSet)

        }

        override fun onAnimationStart(animation: Animation?) {

        }

    }

}