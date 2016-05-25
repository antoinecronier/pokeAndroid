package com.antoinecronier.pokebattle.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Entity
@Rest
public class PokeType  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.INTEGER)
	private int modificateur;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokeType> typeFort;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokeType> typeFaible;

    /**
     * Default constructor.
     */
    public PokeType() {

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
     * Get the Nom.
     * @return the nom
     */
    public String getNom() {
         return this.nom;
    }
     /**
     * Set the Nom.
     * @param value the nom to set
     */
    public void setNom(final String value) {
         this.nom = value;
    }
     /**
     * Get the Modificateur.
     * @return the modificateur
     */
    public int getModificateur() {
         return this.modificateur;
    }
     /**
     * Set the Modificateur.
     * @param value the modificateur to set
     */
    public void setModificateur(final int value) {
         this.modificateur = value;
    }
     /**
     * Get the TypeFort.
     * @return the typeFort
     */
    public ArrayList<PokeType> getTypeFort() {
         return this.typeFort;
    }
     /**
     * Set the TypeFort.
     * @param value the typeFort to set
     */
    public void setTypeFort(final ArrayList<PokeType> value) {
         this.typeFort = value;
    }
     /**
     * Get the TypeFaible.
     * @return the typeFaible
     */
    public ArrayList<PokeType> getTypeFaible() {
         return this.typeFaible;
    }
     /**
     * Set the TypeFaible.
     * @param value the typeFaible to set
     */
    public void setTypeFaible(final ArrayList<PokeType> value) {
         this.typeFaible = value;
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
        if (this.getNom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getNom());
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.getModificateur());

        if (this.getTypeFort() != null) {
            dest.writeInt(this.getTypeFort().size());
            for (PokeType item : this.getTypeFort()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getTypeFaible() != null) {
            dest.writeInt(this.getTypeFaible().size());
            for (PokeType item : this.getTypeFaible()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
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
        int nomBool = parc.readInt();
        if (nomBool == 1) {
            this.setNom(parc.readString());
        }
        this.setModificateur(parc.readInt());

        int nbTypeFort = parc.readInt();
        if (nbTypeFort > -1) {
            ArrayList<PokeType> items =
                new ArrayList<PokeType>();
            for (int i = 0; i < nbTypeFort; i++) {
                items.add((PokeType) parc.readParcelable(
                        PokeType.class.getClassLoader()));
            }
            this.setTypeFort(items);
        }

        int nbTypeFaible = parc.readInt();
        if (nbTypeFaible > -1) {
            ArrayList<PokeType> items =
                new ArrayList<PokeType>();
            for (int i = 0; i < nbTypeFaible; i++) {
                items.add((PokeType) parc.readParcelable(
                        PokeType.class.getClassLoader()));
            }
            this.setTypeFaible(items);
        }
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public PokeType(Parcel parc) {
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
    public static final Parcelable.Creator<PokeType> CREATOR
        = new Parcelable.Creator<PokeType>() {
        public PokeType createFromParcel(Parcel in) {
            return new PokeType(in);
        }
        
        public PokeType[] newArray(int size) {
            return new PokeType[size];
        }
    };

}
