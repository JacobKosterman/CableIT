package skycom.cableit.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Company {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;


    public Company(String name) {
        this.name = name;

    }
}