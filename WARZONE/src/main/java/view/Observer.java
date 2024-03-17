package view;

import models.Subject;

/**
 * The Observer interface defines the contract for objects that observe changes in a subject.
 */
public interface Observer {
    /**
     * Update method called by the subject to notify the observer of a change.
     *
     * @param model The subject that triggered the update
     */
    void update(Subject model);
}