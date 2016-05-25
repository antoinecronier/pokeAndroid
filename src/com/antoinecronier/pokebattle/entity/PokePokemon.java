package com.antoinecronier.pokebattle.entity;

import org.joda.time.format.ISODateTimeFormat;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Entity
@Rest
public class PokePokemon  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String surnom;
	
	@Column(type = Type.INTEGER)
	private int niveau;
	
	@Column(type = Type.DATETIME, nullable = true)
	private DateTime capture;
	
	@ManyToOne
	@Column(nullable = true)
	private PokeTypePokemon type;
	
	@ManyToOne
	@Column(nullable = true)
	private PokeAttaque attaque1;
	
	@ManyToOne
	@Column(nullable = true)
	private PokeAttaque attaque2;
	
	@ManyToOne
	@Column(nullable = true)
	private PokeAttaque attaque3;
	
	@ManyToOne
	@Column(nullable = true)
	private PokeAttaque attaque4;

    /**
     * Default constructor.
     */
    public PokePokemon() {

    }

     /**
     * Get the Id.
     * @return the id
     */
    public int getId() {
         return this.id;
    }
     /**
     * Set the Id.
     * @param value the id to set
     */
    public void setId(final int value) {
         this.id = value;
    }
     /**
     * Get the Surnom.
     * @return the surnom
     */
    public String getSurnom() {
         return this.surnom;
    }
     /**
     * Set the Surnom.
     * @param value the surnom to set
     */
    public void setSurnom(final String value) {
         this.surnom = value;
    }
     /**
     * Get the Niveau.
     * @return the niveau
     */
    public int getNiveau() {
         return this.niveau;
    }
     /**
     * Set the Niveau.
     * @param value the niveau to set
     */
    public void setNiveau(final int value) {
         this.niveau = value;
    }
     /**
     * Get the Capture.
     * @return the capture
     */
    public DateTime getCapture() {
         return this.capture;
    }
     /**
     * Set the Capture.
     * @param value the capture to set
     */
    public void setCapture(final DateTime value) {
         this.capture = value;
    }
     /**
     * Get the Type.
     * @return the type
     */
    public PokeTypePokemon getType() {
         return this.type;
    }
     /**
     * Set the Type.
     * @param value the type to set
     */
    public void setType(final PokeTypePokemon value) {
         this.type = value;
    }
     /**
     * Get the Attaque1.
     * @return the attaque1
     */
    public PokeAttaque getAttaque1() {
         return this.attaque1;
    }
     /**
     * Set the Attaque1.
     * @param value the attaque1 to set
     */
    public void setAttaque1(final PokeAttaque value) {
         this.attaque1 = value;
    }
     /**
     * Get the Attaque2.
     * @return the attaque2
     */
    public PokeAttaque getAttaque2() {
         return this.attaque2;
    }
     /**
     * Set the Attaque2.
     * @param value the attaque2 to set
     */
    public void setAttaque2(final PokeAttaque value) {
         this.attaque2 = value;
    }
     /**
     * Get the Attaque3.
     * @return the attaque3
     */
    public PokeAttaque getAttaque3() {
         return this.attaque3;
    }
     /**
     * Set the Attaque3.
     * @param value the attaque3 to set
     */
    public void setAttaque3(final PokeAttaque value) {
         this.attaque3 = value;
    }
     /**
     * Get the Attaque4.
     * @return the attaque4
     */
    public PokeAttaque getAttaque4() {
         return this.attaque4;
    }
     /**
     * Set the Attaque4.
     * @param value the attaque4 to set
     */
    public void setAttaque4(final PokeAttaque value) {
         this.attaque4 = value;
    }
    /**
     * This stub of code is regenerated. DO NOT MODIFY.
     * 
     * @param dest Destination parcel
     * @param flags flags
     */
    public void writeToParcelRegen(Parcel dest, int flags) {
        if (this.parcelableParents == null) {
            this.parcelableParents = new ArrayList<Parcelable>();
        }
        if (!this.parcelableParents.contains(this)) {
            this.parcelableParents.add(this);
        }
        dest.writeInt(this.getId());
        if (this.getSurnom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getSurnom());
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.getNiveau());
        if (this.getCapture() != null) {
            dest.writeInt(1);
            dest.writeString(ISODateTimeFormat.dateTime().print(
                    this.getCapture()));
        } else {
            dest.writeInt(0);
        }
        if (this.getType() != null
                    && !this.parcelableParents.contains(this.getType())) {
            this.getType().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getAttaque1() != null
                    && !this.parcelableParents.contains(this.getAttaque1())) {
            this.getAttaque1().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getAttaque2() != null
                    && !this.parcelableParents.contains(this.getAttaque2())) {
            this.getAttaque2().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getAttaque3() != null
                    && !this.parcelableParents.contains(this.getAttaque3())) {
            this.getAttaque3().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getAttaque4() != null
                    && !this.parcelableParents.contains(this.getAttaque4())) {
            this.getAttaque4().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
    }

    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        this.setId(parc.readInt());
        int surnomBool = parc.readInt();
        if (surnomBool == 1) {
            this.setSurnom(parc.readString());
        }
        this.setNiveau(parc.readInt());
        if (parc.readInt() == 1) {
            this.setCapture(
                    ISODateTimeFormat.dateTimeParser()
                            .withOffsetParsed().parseDateTime(
                                    parc.readString()));
        }
        this.setType((PokeTypePokemon) parc.readParcelable(PokeTypePokemon.class.getClassLoader()));
        this.setAttaque1((PokeAttaque) parc.readParcelable(PokeAttaque.class.getClassLoader()));
        this.setAttaque2((PokeAttaque) parc.readParcelable(PokeAttaque.class.getClassLoader()));
        this.setAttaque3((PokeAttaque) parc.readParcelable(PokeAttaque.class.getClassLoader()));
        this.setAttaque4((PokeAttaque) parc.readParcelable(PokeAttaque.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public PokePokemon(Parcel parc) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);

        // You can  implement your own parcel mechanics here.

    }

    /* This method is not regenerated. You can implement your own parcel mechanics here. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.writeToParcelRegen(dest, flags);
        // You can  implement your own parcel mechanics here.
    }

    /**
     * Use this method to write this entity to a parcel from another entity.
     * (Useful for relations)
     *
     * @param parent The entity being parcelled that need to parcel this one
     * @param dest The destination parcel
     * @param flags The flags
     */
    public synchronized void writeToParcel(List<Parcelable> parents, Parcel dest, int flags) {
        this.parcelableParents = new ArrayList<Parcelable>(parents);
        dest.writeParcelable(this, flags);
        this.parcelableParents = null;
    }

    @Override
    public int describeContents() {
        // This should return 0 
        // or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
        return 0;
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<PokePokemon> CREATOR
        = new Parcelable.Creator<PokePokemon>() {
        public PokePokemon createFromParcel(Parcel in) {
            return new PokePokemon(in);
        }
        
        public PokePokemon[] newArray(int size) {
            return new PokePokemon[size];
        }
    };

}
