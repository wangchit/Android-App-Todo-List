package com.chitingwang.todolist;

public class TodoItem {

    public final static String URGENT = "urgent";
    public final static String NORMAL = "normal";
    public final static String SCARY = "scary";

    private String taskTitle ="";
    private String month = "";
    private String type="";
    private String date ="";
    private String description ="";
    private String additionDescription = "";
    protected long id = 0;

    public long getId() {
        return(id);
    }
    public void setId(long id) {
        this.id=id;
    }

    public String getTaskTitle() {
        return(taskTitle);
    }
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getMonth() {
        return(month);
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return(type);
    }
    public void setType(String type) {
        this.type=type;
    }

    public String getDate() {
        return(date);
    }
    public void setDate(String date) {
        this.date=date;
    }

    public String getDescription() {
        return(description);
    }
    public void setDescription(String description) {
        this.description=description;
    }

    public String getAdditionDescription() {
        return(additionDescription);
    }
    public void setAdditionDescription(String additionDescription) {
        this.additionDescription=additionDescription;
    }


    public String toString() {
        return(getTaskTitle());
    }
}


