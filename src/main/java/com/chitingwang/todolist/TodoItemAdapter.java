package com.chitingwang.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {
    private int layoutResourceId;
    public final static String TAG = "TodoItemAdapter";
    private LayoutInflater inflater;
    private List<TodoItem> todoItems;
    public TodoItemAdapter(Context context, int layoutResourceId,
                           List<TodoItem> todoItems) {
        super(context, layoutResourceId, todoItems);
        this.layoutResourceId = layoutResourceId;
        this.todoItems = todoItems;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnimalHolder holder = null;
        if (null == convertView) {
            Log.d(TAG, "getView: rowView null: position " + position);
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new AnimalHolder();
            holder.imgType = (ImageView)convertView.findViewById(R.id.icon);
            holder.txtTaskTitle = (TextView)convertView.findViewById(R.id.list_view_Title);
            holder.txtMonth = (TextView)convertView.findViewById(R.id.list_view_month);
            holder.txtDate = (TextView)convertView.findViewById(R.id.list_view_date);
            holder.txtDescription = (TextView)convertView.findViewById(R.id.list_view_description);

            // Tags can be used to store data
            convertView.setTag(holder);
        }
        else {
            Log.d(TAG, "getView: rowView !null - reuse holder: position " +
                    position);
            holder = (AnimalHolder)convertView.getTag();
        }
        Log.d(TAG, " getView todoItems " + todoItems.size());
        // Put inside a try/catch block;
        // animal.get can return an IndexOutOfBoundsException
        // and if the exception occurs and is not handed, it will crash your program.
        try {
            TodoItem todoItem = todoItems.get(position);
            holder.txtTaskTitle.setText(todoItem.getTaskTitle());
            holder.txtMonth.setText(todoItem.getMonth());
            holder.txtDescription.setText(todoItem.getDescription());
            holder.txtDate.setText(todoItem.getDate());

            if (todoItem.getType().equals(TodoItem.URGENT)) {
                holder.imgType.setImageResource(R.drawable.ic_roller);
            }
            else if (todoItem.getType().equals(TodoItem.NORMAL)) {
                holder.imgType.setImageResource(R.drawable.ic_horse);
            }
            else {
                holder.imgType.setImageResource(R.drawable.ic_ghost);
            }
        } catch(Exception e) {
            Log.e(TAG, " getView todoItems " + e + " position was : " + position +
                    " todoItems.size: " + todoItems.size());
        }
        return convertView;
    }
    // This is used to cache the imageView and TextView of the
    // ImageTextArrayAdapter class
    // so they can be reused for every row in the ListView
    static class AnimalHolder {
        ImageView imgType;
        TextView txtTaskTitle;
        TextView txtMonth;
        TextView txtDate;
        TextView txtDescription;
    }
}
