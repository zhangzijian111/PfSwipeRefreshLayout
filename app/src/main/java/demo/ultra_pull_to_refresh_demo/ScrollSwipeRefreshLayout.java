package demo.ultra_pull_to_refresh_demo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
/**
 * 为了不让webview的滚动事件和下拉更新的事件冲突，重写onTouchEvent事件，当webview滚动到最顶端的时候才有下拉刷新
 */
public class ScrollSwipeRefreshLayout extends SwipeRefreshLayout {

    private ViewGroup viewGroup ;

    public ScrollSwipeRefreshLayout(Context context) {
        super(context);
    }
    public ScrollSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ViewGroup getViewGroup() {
        return viewGroup;
    }
    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if(null!=viewGroup){
            if(viewGroup.getScrollY()> 1){
                //直接截断时间传播，交给webview
                return false;
            }else{
                //说明滑到了最上面，这时候就自己处理，出现下拉刷新
                return super.onTouchEvent(arg0);
            }
        }
        return super.onTouchEvent(arg0);
    }

}
