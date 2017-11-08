package com.zz.ak.demo.tool.bar;


import android.support.v7.widget.RecyclerView;

import com.zz.ak.demo.bean.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * Adapter holding a list of animal names of type String. Note that each item must be unique.
 */
public abstract class CityListAdapter<VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {
  private ArrayList<Person> items = new ArrayList<Person>();

  public CityListAdapter() {
    setHasStableIds(true);
  }

  public void add(Person object) {
    items.add(object);
    notifyDataSetChanged();
  }

  public void add(int index, Person object) {
    items.add(index, object);
    notifyDataSetChanged();
  }

  public void addAll(Collection<? extends Person> collection) {
    if (collection != null) {
      items.clear();
      items.addAll(collection);
      notifyDataSetChanged();
    }
  }

  public void addAll(Person... items) {
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

  public Person getItem(int position) {
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
