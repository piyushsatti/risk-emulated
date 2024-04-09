package mvc.models;

import mvc.view.Logger;
import mvc.view.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Subject class represents the subject being observed.
 */
public class Subject {
    /**
     * List of viewers.
     */
    List<Logger> d_viewList = new ArrayList<>();

    /**
     * Notifies all attached views.
     *
     * @param model The subject model
     */
    public void notifyAllViews(Subject model) {
        for (Logger l_logwriter : d_viewList) {
            l_logwriter.update(model);
        }
    }

    /**
     * Attaches a view to the subject.
     *
     * @param p_view The view to attach
     */
    public void attachView(Observer p_view) {
        d_viewList.add((Logger) p_view);
    }

    /**
     * Detaches a view from the subject.
     *
     * @param p_view The view to detach
     */
    public void detachView(Observer p_view) {
        if (!d_viewList.isEmpty()) {
            d_viewList.remove((Logger) p_view);
        }
    }
}
