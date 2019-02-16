package fyq.example.labdadm.labs.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fyq.example.labdadm.labs.Quotation;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    // Patrón singletton
    private static MySQLiteOpenHelper instance;

    public static synchronized MySQLiteOpenHelper getInstance(Context context){
        if (instance == null) {
            instance = new MySQLiteOpenHelper(context);
        }
        return instance;
    }

    // Constructor para crear objetos tipo Helper para manejar la BBDD
    private MySQLiteOpenHelper(Context context){
        super(context,"quotation_database",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query SQL para crear "quotation_table" con una primary key autoincremental (y de tipo integer)
        db.execSQL("CREATE TABLE quotation_table (id INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, quote TEXT NOT NULL, author TEXT, UNIQUE(quote));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Quotation> getQuotations(){
        List<Quotation> res = new ArrayList<>();
        Quotation q;
        // Accedemos a la BBDD en modo lectura
        SQLiteDatabase db = getReadableDatabase();
        // Query para sacar en dos "columnas" de strings el texto de la quote y el autor
        Cursor cursor1 = db.query("quotation_table", new String[]{"quote",
                "author"}, null, null, null, null, null);
        // Recorremos por la query las columnas y las añadimos a la lista res
        while (cursor1.moveToNext()){
            q = new Quotation(cursor1.getString(0),cursor1.getString(1));
            res.add(q);
        }
        // Cerramos el cursor de la query y la BBDD
        cursor1.close(); db.close();
        return res;
    }

    public boolean isInDatabase(String quotation){
        boolean res=false;
        // Accedemos a la BBDD en modo lectura
        SQLiteDatabase db = getReadableDatabase();
        // Query para buscar la quote que nos pasan como parámetro en la BBDD
        Cursor cursor1 = db.query("quotation_table", null, "quote=?", new String[]{quotation}, null, null, null, null);
        // Si el número de quotes de la query es 0, es que no está en la base de datos, si no, sí está
        if(cursor1.getCount()>0) res= true;
        // Cerramos el cursor de la query y la BBDD
        cursor1.close(); db.close();
        return res;
    }


    public void addQuotation(Quotation quotation) {
        // Accedemos a la BBDD en modo escritura
        SQLiteDatabase db = getWritableDatabase();
        // Insertamos la nueva quote
        ContentValues values = new ContentValues();
        values.put("quote", quotation.getQuoteText());
        values.put("author", quotation.getQuoteAuthor());
        db.insert("quotation_table", null, values);
        // Cerramos la BBDD
        db.close();
    }

    public void deleteAll(){
        // Accedemos a la BBDD en modo escritura
        SQLiteDatabase db = getWritableDatabase();
        // Borramos toda la tabla
        db.delete("quotation_table", null, null);
        // Cerramos la BBDD
        db.close();
    }

    public void deleteQuotation(String quote) {
        // Accedemos a la BBDD en modo escritura
        SQLiteDatabase db = getWritableDatabase();
        // Borramos la quote de la BBDD
        db. delete("quotation_table", "quote=?", new
                String[]{quote});
        // Cerramos la BBDD
        db.close();
    }
}
