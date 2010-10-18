package com.workhabit.drupal.publisher;

import com.google.inject.Module;
import roboguice.application.RoboApplication;

import java.util.List;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: Oct 11, 2010, 4:29:53 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class AnDrupalApplication extends RoboApplication {
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new DrupalConfigModule());
    }
}
