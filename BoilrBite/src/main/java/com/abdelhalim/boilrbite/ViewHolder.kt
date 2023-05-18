package com.abdelhalim.boilrbite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder is an abstract ViewHolder class for RecyclerView.
 * @property selected holds the current position of the selected item.
 * @param <H> specifies the type of data that this ViewHolder can bind to.
 * @constructor creates a new instance of the ViewHolder with the specified parameters.
 * @constructor overload method creates a new instance of the ViewHolder with the specified parameters.
 */
abstract class ViewHolder<H : Any>(
    view: View,
    private val adapter: BoilrBite<H, *>
) : RecyclerView.ViewHolder(view) {

    constructor(
        parent: ViewGroup,
        layoutRes: Int,
        adapter: BoilrBite<H, *>
    ) : this(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false), adapter
    )

//    lateinit var item: H

    /**
     * Binds an item to the view held by this ViewHolder instance.
     * @param item the item to be bound to the ViewHolder's View.
     * @param onTClickListener a listener for click events on items.
     */
    abstract fun bind(item: H, onTClickListener: OnItemClickListener<H, View?>?)

    /**
     * Gets the currently selected item position.
     *
     * @return the position of the currently selected item, or null if no item is selected.
     */
    var selected: Int?
        get() = adapter.getSelected()
        set(selected) {
            setSelected(selected)
        }

    /**
     * Sets the selected item position and notifies listeners when it changes.
     *
     * @param selected the position of the selected item.
     * @param revers whether to deselect the item if it is already selected.
     */
    private fun setSelected(selected: Int?, revers: Boolean = true) {
        adapter.setSelected(selected ?: -1, revers)
    }
}