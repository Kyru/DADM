package fyq.example.labdadm.labs.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import fyq.example.labdadm.labs.Quotation;

@Database(version = 1, entities = {Quotation.class})
public abstract class QuotationDatabase extends RoomDatabase {

    public abstract QuotationDAO quotationDAO();

    private static QuotationDatabase quotationDatabase;


    public synchronized static QuotationDatabase getInstance(Context context) {
        if (quotationDatabase == null) {
            quotationDatabase = Room
                    .databaseBuilder(context, QuotationDatabase.class, "quotation_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return quotationDatabase;
    }

}
