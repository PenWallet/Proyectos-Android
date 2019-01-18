package com.example.ofunes.pennypanphone;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class Utils {
    public static void animateClick(View v)
    {
        ScaleAnimation animation = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new CycleInterpolator(1));
        animation.setDuration(200);

        v.startAnimation(animation);
    }

    public static void animateError(View v)
    {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new CycleInterpolator(2));
        animation.setDuration(200);

        v.startAnimation(animation);
    }

    public static void animateEnter(final View v)
    {
        /*View parent = (View)v.getParent();
        int widthParent = parent.getWidth()/2;
        int heightParent = parent.getHeight()/2;
        int widthChild = v.getWidth()/2;
        int heightChild = v.getHeight()/2;
        int left = v.getLeft();
        int top = v.getTop();
        int width = widthParent - left - widthChild;
        int height = heightParent - top - heightChild;

        TranslateAnimation move = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, width, Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, height);
        move.setFillAfter(true);
        move.setFillEnabled(true);
        move.setDuration(500);

        final ScaleAnimation scale = new ScaleAnimation(1, 10, 1, 10, Animation.ABSOLUTE, width, Animation.ABSOLUTE, height);
        scale.setStartOffset(500);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(move);
        animationSet.addAnimation(scale);
        animationSet.setFillAfter(true);



        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //v.startAnimation(scale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/

        final Animation anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.market_enter_zoomfade);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        v.startAnimation(anim);
    }

}
