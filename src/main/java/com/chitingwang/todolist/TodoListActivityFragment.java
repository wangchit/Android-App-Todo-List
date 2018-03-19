package com.chitingwang.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodoListActivityFragment extends Fragment {

    public final static String TAG = "TodoList";
    List<TodoItem> todoItems =new ArrayList<TodoItem>();
    ArrayAdapter<TodoItem> adapter=null;
    private DBHelper dbHelper = null;

    public TodoListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        try {
            dbHelper = new DBHelper(getActivity());
            todoItems = dbHelper.selectAll();
        } catch (Exception e) {
            Log.d(TAG, "onCreate: DBHelper threw exception : " + e);
            e.printStackTrace();
        }


        ListView list=(ListView)getActivity().findViewById(R.id.list_view_todo);

        adapter=new TodoItemAdapter(getActivity(),R.layout.row, todoItems);

        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                onDelete(view, position);
                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                TodoItem todoItem = adapter.getItem(position);
                String item = "Addition: " + todoItem.getAdditionDescription();
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });

        Button saveButton = (Button) getActivity().findViewById(R.id.add_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSave();
            }
        });

    }

    private void onSave() {
        TodoItem todoItem = new TodoItem();

        EditText taskTitle = (EditText) getActivity().findViewById(R.id.fragment_title_input);

        Spinner month = (Spinner) getActivity().findViewById(R.id.fragment_month_input);

        Spinner date = (Spinner) getActivity().findViewById(R.id.fragment_date_input);

        String taskTitleString = taskTitle.getText().toString();
        String monthString = month.getSelectedItem().toString();
        String dateString = date.getSelectedItem().toString();

        if (TextUtils.isEmpty(taskTitleString)||TextUtils.isEmpty(monthString)||TextUtils.isEmpty(dateString)) {
            showMissingInfoAlert();
        } else {

            todoItem.setTaskTitle(taskTitle.getText().toString());

            todoItem.setMonth(monthString);
            todoItem.setDate(dateString);

            EditText description = (EditText) getActivity().findViewById(R.id.fragment_description_input);
            todoItem.setDescription(description.getText().toString());

            EditText addition_description = (EditText) getActivity().findViewById(R.id.fragment_addition_input);
            todoItem.setAdditionDescription(addition_description.getText().toString());

            RadioGroup types = (RadioGroup) getActivity().findViewById(R.id.fragment_type_group);

            switch (types.getCheckedRadioButtonId()) {
                case R.id.fragment_type_urgent:
                    todoItem.setType("urgent");
                    break;
                case R.id.fragment_type_normal:
                    todoItem.setType("normal");
                    break;
                case R.id.fragment_type_scary:
                    todoItem.setType("scary");
                    break;
            }

            //add the todoItem to the database when the todoItem is added to the list.
            long animalId = 0;
            if (dbHelper != null) {
                animalId = dbHelper.insert(todoItem);
                todoItem.setId(animalId);
            }


            // Add the object at the end of the array.
            adapter.add(todoItem);
            // Notifies the adapter that the underlying data has changed,
            // any View reflecting the data should refresh itself.
            adapter.notifyDataSetChanged();

        }

        // hiding keyboard after hitting the save button
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().
                getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


    }

    private void onDelete(View view, int position){
    // When clicked, delete the item that was clicked.
    // (Show a toast to indicate what is occurring)
        TodoItem todoItem = adapter.getItem(position);
        if (todoItem != null) {
            String item = "deleting: " + todoItem.getTaskTitle();
            Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            Log.d(TAG, " onItemClick: " + todoItem.getTaskTitle());

            // database delete record
            if (dbHelper != null) dbHelper.deleteRecord(todoItem.getId());

            // Removes the object from the array
            adapter.remove(todoItem);
            // Notifies t that the underlying data has changed
            adapter.notifyDataSetChanged();
        }
    }


    public void showMissingInfoAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getResources().getString(R.string.alert_title));
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
// set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.alert_message))
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
// if this button is clicked, close current activity
                        dialog.cancel();
                    }
                });
// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
// show it
        alertDialog.show();
    }


}
