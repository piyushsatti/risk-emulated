package models;

import view.Logger;
import view.Observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    List<Logger> viewList = new ArrayList<>();
    public void notifyAllViews(Subject model)
    {
        for(Logger logwriter : viewList)
        {
            logwriter.update(model);
        }
    }
    public void attachView(Observer view)
    {
        viewList.add((Logger) view);
    }
    public void detachView(Observer view)
    {
        if(!viewList.isEmpty())
        {
            viewList.remove((Logger) view);
        }
    }
}
