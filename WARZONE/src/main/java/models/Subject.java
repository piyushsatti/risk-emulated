package models;

import view.Logger;
import view.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Subject class represents the subject being observed.
 */
public class Subject {
    /**
     * List of viewers.
     */
    List<Logger> viewList = new ArrayList<>();
    /**
     * Notifies all attached views.
     *
     * @param model The subject model
     */
    public void notifyAllViews(Subject model)
    {
        for(Logger logwriter : viewList)
        {
            logwriter.update(model);
        }
    }
    /**
     * Attaches a view to the subject.
     *
     * @param view The view to attach
     */
    public void attachView(Observer view)
    {
        viewList.add((Logger) view);
    }
    /**
     * Detaches a view from the subject.
     *
     * @param view The view to detach
     */
    public void detachView(Observer view)
    {
        if(!viewList.isEmpty())
        {
            viewList.remove((Logger) view);
        }
    }
}
