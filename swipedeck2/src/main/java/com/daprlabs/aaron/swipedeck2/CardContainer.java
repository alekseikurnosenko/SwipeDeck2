package com.daprlabs.aaron.swipedeck;

import android.view.View;

import com.daprlabs.aaron.swipedeck.Utility.RxBus;
import com.daprlabs.aaron.swipedeck.Utility.SwipeCallback;
import com.daprlabs.aaron.swipedeck.Utility.SwipeListener;

/**
 * Created by aaron on 21/08/2016.
 */
public class CardContainer {

    private View view;
    private RxBus bus;
    int positionWithinViewGroup = -1;
    private SwipeListener swipeListener;
    private SwipeCallback callback;
    private SwipeDeck parent;

    public CardContainer(View view, SwipeDeck parent, SwipeCallback callback){
        this.view = view;
        this.parent = parent;
        this.callback = callback;

        setupSwipeListener();
    }

    public void setPositionWithinViewGroup(int pos){this.positionWithinViewGroup = pos;}
    public int getPositionWithinViewGroup(){return positionWithinViewGroup;}

    public View getCard(){
        return this.view;
    }

    public void cleanupAndRemoveView(){
        //wait for card to render off screen, do cleanup and remove from viewgroup
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                deleteViewFromSwipeDeck();
            }
        }, SwipeDeck.ANIMATION_DURATION);
    }

    private void deleteViewFromSwipeDeck(){
        parent.removeView(view);
        parent.clearBuffer();
    }

    public void setSwipeEnabled(boolean enabled){
        if(enabled){
            view.setOnTouchListener(swipeListener);
        }else{
            view.setOnTouchListener(null);
        }
    }
    public SwipeListener getSwipeListener(){
        return swipeListener;
    }

    public void setLeftImageResource(int leftImageResource){
        View left = view.findViewById(leftImageResource);
        left.setAlpha(0);
        swipeListener.setLeftView(left);

    }
    public void setRightImageResource(int rightImageResource){
        View right = view.findViewById(rightImageResource);
        right.setAlpha(0);
        swipeListener.setRightView(right);
    }

    public void setupSwipeListener(){
        this.swipeListener = new SwipeListener(view, callback, parent.getPaddingLeft(), parent.getPaddingTop(), parent.ROTATION_DEGREES, parent.OPACITY_END, parent);
    }
}
