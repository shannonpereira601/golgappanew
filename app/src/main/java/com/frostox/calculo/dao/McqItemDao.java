package com.frostox.calculo.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import com.frostox.calculo.Entities.Mcq;
import com.frostox.calculo.Entities.McqItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MCQ_ITEM".
*/
public class McqItemDao extends AbstractDao<McqItem, Long> {

    public static final String TABLENAME = "MCQ_ITEM";

    /**
     * Properties of entity McqItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Question = new Property(1, String.class, "question", false, "QUESTION");
        public final static Property OptionA = new Property(2, String.class, "optionA", false, "OPTION_A");
        public final static Property OptionB = new Property(3, String.class, "optionB", false, "OPTION_B");
        public final static Property OptionC = new Property(4, String.class, "optionC", false, "OPTION_C");
        public final static Property OptionD = new Property(5, String.class, "optionD", false, "OPTION_D");
        public final static Property Answer = new Property(6, String.class, "answer", false, "ANSWER");
        public final static Property McqId = new Property(7, Long.class, "mcqId", false, "MCQ_ID");
    };

    private DaoSession daoSession;


    public McqItemDao(DaoConfig config) {
        super(config);
    }
    
    public McqItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MCQ_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"QUESTION\" TEXT NOT NULL ," + // 1: question
                "\"OPTION_A\" TEXT NOT NULL ," + // 2: optionA
                "\"OPTION_B\" TEXT NOT NULL ," + // 3: optionB
                "\"OPTION_C\" TEXT NOT NULL ," + // 4: optionC
                "\"OPTION_D\" TEXT NOT NULL ," + // 5: optionD
                "\"ANSWER\" TEXT NOT NULL ," + // 6: answer
                "\"MCQ_ID\" INTEGER);"); // 7: mcqId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MCQ_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, McqItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getQuestion());
        stmt.bindString(3, entity.getOptionA());
        stmt.bindString(4, entity.getOptionB());
        stmt.bindString(5, entity.getOptionC());
        stmt.bindString(6, entity.getOptionD());
        stmt.bindString(7, entity.getAnswer());
 
        Long mcqId = entity.getMcqId();
        if (mcqId != null) {
            stmt.bindLong(8, mcqId);
        }
    }

    @Override
    protected void attachEntity(McqItem entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public McqItem readEntity(Cursor cursor, int offset) {
        McqItem entity = new McqItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // question
            cursor.getString(offset + 2), // optionA
            cursor.getString(offset + 3), // optionB
            cursor.getString(offset + 4), // optionC
            cursor.getString(offset + 5), // optionD
            cursor.getString(offset + 6), // answer
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // mcqId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, McqItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQuestion(cursor.getString(offset + 1));
        entity.setOptionA(cursor.getString(offset + 2));
        entity.setOptionB(cursor.getString(offset + 3));
        entity.setOptionC(cursor.getString(offset + 4));
        entity.setOptionD(cursor.getString(offset + 5));
        entity.setAnswer(cursor.getString(offset + 6));
        entity.setMcqId(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(McqItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(McqItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMcqDao().getAllColumns());
            builder.append(" FROM MCQ_ITEM T");
            builder.append(" LEFT JOIN MCQ T0 ON T.\"MCQ_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected McqItem loadCurrentDeep(Cursor cursor, boolean lock) {
        McqItem entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Mcq mcq = loadCurrentOther(daoSession.getMcqDao(), cursor, offset);
        entity.setMcq(mcq);

        return entity;    
    }

    public McqItem loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<McqItem> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<McqItem> list = new ArrayList<McqItem>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<McqItem> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<McqItem> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
