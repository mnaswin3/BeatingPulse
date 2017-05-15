package co.aswin.elanic.adapter;

import android.support.v4.widget.NestedScrollView;

public abstract class EndlessVerticalOnScrollListener implements NestedScrollView.OnScrollChangeListener {

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            onLoad();
        }
    }

    public abstract void onLoad();
}
