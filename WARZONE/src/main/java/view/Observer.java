package view;

import models.Subject;

public interface Observer {
    void update(Subject model);
}