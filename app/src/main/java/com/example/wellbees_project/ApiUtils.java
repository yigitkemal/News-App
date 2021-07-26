package com.example.wellbees_project;

public class ApiUtils {

    public static final String BASE_URL = "https://newsapi.org";

    public static SourcesDAOInterface getSourcesDaoInterface(){
        return RetrofitClient.getClient(BASE_URL).create(SourcesDAOInterface.class);
    }

}
