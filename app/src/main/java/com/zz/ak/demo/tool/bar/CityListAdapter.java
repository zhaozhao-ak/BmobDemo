package com.zz.ak.demo.tool.bar;


import android.support.v7.widget.RecyclerView;

import com.zz.ak.demo.bean._User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * Adapter holding a list of animal names of type String. Note that each item must be unique.
 */
public abstract class CityListAdapter<VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {
  private ArrayList<_User> items = new ArrayList<_User>();

  public CityListAdapter() {
    setHasStableIds(true);
  }

  public void add(_User object) {
    items.add(object);
    notifyDataSetChanged();
  }

  public void add(int index, _User object) {
    items.add(index, object);
    notifyDataSetChanged();
  }

  public void addAll(Collection<? extends _User> collection) {
    if (collection != null) {
      items.clear();
      items.addAll(collection);
      notifyDataSetChanged();
    }
  }

  public void addAll(_User... items) {
    addAll(Arrays.asList(items));
  }

  public void clear() {
    items.clear();
    notifyDataSetChanged();
  }

  public void remove(String object) {
    items.remove(object);
    notifyDataSetChanged();
  }

  public _User getItem(int position) {
    return items==null?null:items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).hashCode();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }
}
