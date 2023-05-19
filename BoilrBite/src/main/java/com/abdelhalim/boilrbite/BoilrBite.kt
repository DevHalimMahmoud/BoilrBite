package com.abdelhalim.boilrbite

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * BoilrBite is an abstract class that extends from ListAdapter and serves as a base adapter for RecyclerView.
 * It provides functionalities to manipulate a list of items, add or remove them, and track the selected item in the list.
 * @param T is the type of the item in the list
 * @param VH is the ViewHolder associated with the item type T
 * @param diffCallback is used by ListAdapter to calculate the difference between two lists and update the UI accordingly.
 */
abstract class BoilrBite<T : Any, VH : ViewHolder<T>>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(diffCallback) {

    /**
     * Companion object to provide a factory method to create instances of BoilrBite adapter.
     */

    companion object {

        /**
         * Creates an adapter for use in a RecyclerView with the BoilrBite library.
         * @param items The list of items to be displayed in the RecyclerView.
         * @param layoutResId The resource ID of the layout to be inflated for each item view.
         * @param clickableViewIds The set of resource IDs for views in the layout that should have click listeners attached to them.
         * @param compareItems A lambda expression that compares two items and returns true if they are equal.
         * @param compareContents A lambda expression that compares the contents of two items and returns true if they are equal.
         * @param bind A lambda expression that binds the data from an item to the views in its corresponding ViewHolder.
         * @return A new instance of BoilrBite that can be used as an adapter in a RecyclerView.
         */
        fun <I : Any> createBoilrBiteAdapter(
            items: MutableList<I>,
            @LayoutRes layoutResId: Int,
            @LayoutRes clickableViewIds: Set<Int> = emptySet(),
            compareItems: (old: I, new: I) -> Boolean = { old, new -> old == new },
            compareContents: (old: I, new: I) -> Boolean,
            bind: (View, I) -> Unit
        ): BoilrBite<I, ViewHolder<I>> =
            object : BoilrBite<I, ViewHolder<I>>(
                RecyclerDiffCallback(
                    compareItems,
                    compareContents
                )
            ) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ViewHolder<I> {
                    return object : ViewHolder<I>(parent, layoutResId, this) {
                        override fun bind(
                            item: I,
                            onTClickListener: OnItemClickListener<I, View?>?
                        ) {
                            // Bind the data from the item to the views in the ViewHolder using the provided block function.
                            bind(itemView, item)

                            // Set a click listener on the item view that calls the onItemClicked method of the OnItemClickListener.
                            itemView.setOnClickListener {
                                onTClickListener?.onItemClicked(item, it, layoutPosition)
                            }

                            // Set click listeners on any additional views in the item layout that were specified by their resource IDs in the ids set.
                            clickableViewIds.forEach { id ->
                                itemView.findViewById<View>(id).setOnClickListener {
                                    onTClickListener?.onItemClicked(item, it, layoutPosition)
                                }
                            }
                        }
                    }
                }
            }
    }

    private var selected: Int? = null
    var onItemClickListener: OnItemClickListener<T, View?>? = null
    var onSelectedItemChange: OnSelectedItemChange<T>? = null

    /**
     *  Sets the currently selected item and deselects the old item.
     * @param selected is the position of the newly selected item.
     * @param reverse is used to deselect the item if it's already selected.
     * @param callback is a lambda that takes the newly selected item as an argument and executes when the item is selected.
     * @return Returns true if an item was selected, false otherwise.
     */
    fun setSelected(
        selected: Int,
        reverse: Boolean = true,
        callback: ((T?) -> Unit)? = null
    ): Boolean {
        val old = this.selected ?: -1
        this.selected = selected.coerceIn(0 until itemCount)
        if (old == selected && reverse) {
            this.selected = null
        }
        notifyItemChanged(old)
        notifyItemChanged(selected)

        callback?.invoke(getSelectedItem())
        onSelectedItemChange?.onSelectedItemChange(
            old,
            if (old == -1) null else getItem(old),
            selected,
            getItem(selected)
        )
        return this.selected != null
    }

    /**
     * Returns the selected item from the list.
     * @return The selected item or null if no item is selected.
     */

    open fun getSelectedItem(): T? {
        return selected?.let { getItem(it) }
    }

    /**
     * Returns the index of the first occurrence of the specified item in the list, or -1 if the list does not contain the item.
     * @param t is the item to search for in the list.
     * @return The index of the first occurrence of the specified item in the list, or -1 if the list does not contain the item.
     */

    open fun indexOf(t: T): Int {
        return currentList.indexOf(t)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder is the ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position is the position of the item within the adapter's data set.
     */

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { holder.bind(it, onItemClickListener) }
    }

    /**
     * Clears the current list by submitting an empty list to ListAdapter.
     */

    open fun clear() {
        submitList(emptyList())
    }

    /**
     * Adds or updates multiple items to the current list while ensuring that there are no duplicates in the list.
     * @param items is a list of items to be added or updated in the list.
     */

    open fun addOrUpdateItems(items: List<T>) {
        if (items.isEmpty()) return // early exit
        val uniqItems = items.toSet()
        val indexMap = currentList.withIndex().associateBy({ it.value }, { it.index })
        val newList = ArrayList(currentList)
        newList.addAll(uniqItems.mapNotNull { item ->
            val index = indexMap[item]
            if (index != null) {
                newList[index] = item
                null
            } else {
                item
            }
        })
        submitList(newList)
    }

    /**
     * Adds or updates a single item to the current list while ensuring that there are no duplicates in the list.
     * @param item is the item to be added or updated in the list.
     */

    open fun addOrUpdateItem(item: T) {
        addOrUpdateItems(listOf(item))
    }

    /**
     * Removes multiple items from the current list.
     * @param items is a list of items to be removed from the list.
     */

    open fun remove(items: List<T>) {
        val newList = currentList.toMutableList().apply { removeAll(items) }
        submitList(newList)
    }

    /**
     * Removes a single item from the current list.
     * @param item is the item to be removed from the list.
     */

    open fun remove(item: T) {
        val index = indexOf(item)
        if (index in 0 until itemCount) {
            val newList = currentList.toMutableList().apply { removeAt(index) }
            submitList(newList)
            notifyItemRemoved(index)
        }
    }

    /**
     * Replaces the current list with a new list of items.
     * @param items is the new list of items to replace the current list.
     */

    open fun setItems(items: List<T>) {
        submitList(items)
    }

    /**
     * Returns the position of the currently selected item in the list.
     * @return the position of the selected item or null if no item is selected.
     */

    open fun getSelected(): Int? {
        return selected
    }

    /**
     * A private inner class that extends DiffUtil.ItemCallback to calculate the difference between two lists and update the UI accordingly.
     */

    private class RecyclerDiffCallback<K : Any>(
        private val compareItems: (old: K, new: K) -> Boolean,
        private val compareContents: (old: K, new: K) -> Boolean
    ) : DiffUtil.ItemCallback<K>() {
        override fun areItemsTheSame(old: K, new: K) = compareItems(old, new)
        override fun areContentsTheSame(old: K, new: K) = compareContents(old, new)
    }

    /**
     * A functional interface for handling changes to the selected item in a list.
     * This interface provides a single abstract method, onSelectedItemChange(), which is called whenever the selected item
     * in a list changes. By implementing this interface, a client code can receive notifications about the change of selection,
     * and take appropriate actions based on the old and new positions and items.
     * @param I The type of items stored in the list.
     */

    fun interface OnSelectedItemChange<I> {

        /**
         * Called when the selected item in the list changes.
         * @param oldPosition is the position of the previously selected item.
         * @param oldItem is the previously selected item or null if there was no previous selection.
         * @param newPosition is the position of the newly selected item or null if there is no new selection.
         * @param newItem is the newly selected item or null if there is no new selection.
         */

        fun onSelectedItemChange(oldPosition: Int, oldItem: I?, newPosition: Int?, newItem: I?)
    }
}
