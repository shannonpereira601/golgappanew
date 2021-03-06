package com.frostox.calculo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.frostox.calculo.dao.UserDao;
import com.frostox.calculo.dao.LoggedDao;
import com.frostox.calculo.dao.StandardDao;
import com.frostox.calculo.dao.SubjectDao;
import com.frostox.calculo.dao.TopicDao;
import com.frostox.calculo.dao.McqDao;
import com.frostox.calculo.dao.McqItemDao;
import com.frostox.calculo.dao.NoteDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        UserDao.createTable(db, ifNotExists);
        LoggedDao.createTable(db, ifNotExists);
        StandardDao.createTable(db, ifNotExists);
        SubjectDao.createTable(db, ifNotExists);
        TopicDao.createTable(db, ifNotExists);
        McqDao.createTable(db, ifNotExists);
        McqItemDao.createTable(db, ifNotExists);
        NoteDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        UserDao.dropTable(db, ifExists);
        LoggedDao.dropTable(db, ifExists);
        StandardDao.dropTable(db, ifExists);
        SubjectDao.dropTable(db, ifExists);
        TopicDao.dropTable(db, ifExists);
        McqDao.dropTable(db, ifExists);
        McqItemDao.dropTable(db, ifExists);
        NoteDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(UserDao.class);
        registerDaoClass(LoggedDao.class);
        registerDaoClass(StandardDao.class);
        registerDaoClass(SubjectDao.class);
        registerDaoClass(TopicDao.class);
        registerDaoClass(McqDao.class);
        registerDaoClass(McqItemDao.class);
        registerDaoClass(NoteDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
