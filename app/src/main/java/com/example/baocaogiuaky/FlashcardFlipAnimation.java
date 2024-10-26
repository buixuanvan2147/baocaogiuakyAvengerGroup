package com.example.baocaogiuaky;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

public class FlashcardFlipAnimation {

    private AnimatorSet frontAnim, backAnim;
    private boolean isFrontVisible = true;

    public FlashcardFlipAnimation(Context context, View frontView, View backView) {
        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.front_animator);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.back_animator);

        frontAnim.setTarget(frontView);
        backAnim.setTarget(backView);
    }

    public void flipCard() {
        if (isFrontVisible) {
            frontAnim.start();
        } else {
            backAnim.start();
        }
        isFrontVisible = !isFrontVisible;
    }
}
