package fyq.example.labdadm.labs.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fyq.example.labdadm.labs.Quotation;

@Dao
public interface QuotationDAO {

    @Insert
    void addQuotation(Quotation quotation);

    @Delete
    void deleteQuotation(Quotation quotation);

    @Query("SELECT * FROM quotation_database")
    List<Quotation> getAllQuotation();

    @Query("SELECT * FROM quotation_database WHERE quote = :quotationText")
    Quotation getQuotation(String quotationText);

    @Query("DELETE FROM quotation_database")
    void deleteAllQuotations();
}
