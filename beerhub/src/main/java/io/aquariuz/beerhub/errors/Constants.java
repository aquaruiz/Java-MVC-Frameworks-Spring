package io.aquariuz.beerhub.errors;

import java.io.File;

public class Constants {
    public static final String FOLDER_PATH = "src" + File.separator +
            "main" + File.separator +
            "resources" + File.separator +
            "files" + File.separator +
            "xmls";
    public static final String COMPANIES_PATH_IMPORT = FOLDER_PATH + File.separator +
            "companies.xml";
    public static final String PROJECTS_PATH_IMPORT = FOLDER_PATH + File.separator +
            "projects.xml";
    public static final String EMPLOYEES_PATH_IMPORT = FOLDER_PATH + File.separator +
            "employees.xml";
    public static final String EXPORT_FINISHED_PROJECTS_PATH = FOLDER_PATH + File.separator +
            "finished-projects.xml";
    public static final String EXPORT_25_AGE_EMPLOYEES_PATH = FOLDER_PATH + File.separator +
            "employees-above-25.xml";


    public final static String USERNAME_NOT_FOUND = "Username is not found";

}