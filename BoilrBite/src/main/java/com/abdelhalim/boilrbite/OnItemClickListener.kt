package com.abdelhalim.boilrbite

import android.view.View

/**
 * An interface for handling item clicks in a RecyclerView.
 */
open class OnItemClickListener<T : Any?, V : View?> {

    /**
     * Called when an item is clicked.
     *
     * @param item The data associated with the clicked item.
     * @param view The view that was clicked.
     * @param position The position of the clicked item in the RecyclerView.
     */
    open fun onItemClicked(
        item: T? = null, view: V? = null, position: Int = -1
    ) {
        onItemClicked()
        item?.let { onItemClicked(it) }
        view?.let { onItemClicked(it) }
        if (position >= 0) {
            onItemClicked(position)
            item?.let { onItemClicked(it, position) }
        }
        view?.let { item?.let { it1 -> onItemClicked(it, it1, position) } }
    }

    protected open fun onItemClicked() {}
    protected open fun onItemClicked(item: T) {}
    protected open fun onItemClicked(view: V) {}
    protected open fun onItemClicked(position: Int) {}
    protected open fun onItemClicked(item: T, position: Int) {}
    protected open fun onItemClicked(view: V, item: T, position: Int) {}
}
