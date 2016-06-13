package com.mozhuowen.rxandroidframework.context;

import android.app.Application;

import net.rehacktive.waspdb.WaspDb;
import net.rehacktive.waspdb.WaspFactory;
import net.rehacktive.waspdb.WaspHash;
import net.rehacktive.waspdb.WaspListener;

/**
 * Created by Awen on 16/6/13.
 * Email:mozhuowen@gmail.com
 */
public class App extends Application {

    static WaspDb db;
    static WaspHash dbHash;

    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
    }

    public void initDb() {
        String path = getFilesDir().getPath();
        String databaseName = "awen";
        String password = "0603";

        WaspFactory.openOrCreateDatabase(path,databaseName,password,new WaspListener<WaspDb>() {
            @Override
            public void onDone(WaspDb waspDb) {
                db = waspDb;
                dbHash = db.openOrCreateHash("movies");
            }
        });
    }

    public static WaspDb getDb() {
        return db;
    }

    public static WaspHash getDbHash() {
        return dbHash;
    }
}
