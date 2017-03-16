package me.danielpan.youtubelike.abs;

/**
 * Created by Pan Chen on 16/03/2017 : 17:17.
 * Company : www.CreditEase.cn;
 * Email : chenpan1@creditease.cn;
 * Motto : If you can take it, you can make it.
 */

public interface DragAndSwipeHelper {

    void onDraggedAndDropped(int fromPosition, int toPosition);

    void onSwiped(int position);

}
