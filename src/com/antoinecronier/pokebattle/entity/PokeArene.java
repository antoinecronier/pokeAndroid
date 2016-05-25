package com.antoinecronier.pokebattle.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.OneToOne;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Entity
@Rest
public class PokeArene  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@OneToOne
	private PokeNpc maitre;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokeNpc> dresseurs;
	
	@OneToOne
	private PokeBadge badge;
	
	@ManyToOne
	private PokeZone zone;
	
	@ManyToOne
	private PokePosition position;

    /**
     * Default constructor.
     */
    public PokeArene() {

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
     * Get the Maitre.
     * @return the maitre
     */
    public PokeNpc getMaitre() {
         return this.maitre;
    }
     /**
     * Set the Maitre.
     * @param value the maitre to set
     */
    public void setMaitre(final PokeNpc value) {
         this.maitre = value;
    }
     /**
     * Get the Dresseurs.
     * @return the dresseurs
     */
    public ArrayList<PokeNpc> getDresseurs() {
         return this.dresseurs;
    }
     /**
     * Set the Dresseurs.
     * @param value the dresseurs to set
     */
    public void setDresseurs(final ArrayList<PokeNpc> value) {
         this.dresseurs = value;
    }
     /**
     * Get the Badge.
     * @return the badge
     */
    public PokeBadge getBadge() {
         return this.badge;
    }
     /**
     * Set the Badge.
     * @param value the badge to set
     */
    public void setBadge(final PokeBadge value) {
         this.badge = value;
    }
     /**
     * Get the Zone.
     * @return the zone
     */
    public PokeZone getZone() {
         return this.zone;
    }
     /**
     * Set the Zone.
     * @param value the zone to set
     */
    public void setZone(final PokeZone value) {
         this.zone = value;
    }
     /**
     * Get the Position.
     * @return the position
     */
    public PokePosition getPosition() {
         return this.position;
    }
     /**
     * Set the Position.
     * @param value the position to set
     */
    public void setPosition(final PokePosition value) {
         this.position = value;
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
        if (this.getMaitre() != null
                    && !this.parcelableParents.contains(this.getMaitre())) {
            this.getMaitre().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }

        if (this.getDresseurs() != null) {
            dest.writeInt(this.getDresseurs().size());
            for (PokeNpc item : this.getDresseurs()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        if (this.getBadge() != null
                    && !this.parcelableParents.contains(this.getBadge())) {
            this.getBadge().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getZone() != null
                    && !this.parcelableParents.contains(this.getZone())) {
            this.getZone().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getPosition() != null
                    && !this.parcelableParents.contains(this.getPosition())) {
            this.getPosition().writeToParcel(this.parcelableParents, dest, flags);
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
        int nomBool = parc.readInt();
        if (nomBool == 1) {
            this.setNom(parc.readString());
        }
        this.setMaitre((PokeNpc) parc.readParcelable(PokeNpc.class.getClassLoader()));

        int nbDresseurs = parc.readInt();
        if (nbDresseurs > -1) {
            ArrayList<PokeNpc> items =
                new ArrayList<PokeNpc>();
            for (int i = 0; i < nbDresseurs; i++) {
                items.add((PokeNpc) parc.readParcelable(
                        PokeNpc.class.getClassLoader()));
            }
            this.setDresseurs(items);
        }
        this.setBadge((PokeBadge) parc.readParcelable(PokeBadge.class.getClassLoader()));
        this.setZone((PokeZone) parc.readParcelable(PokeZone.class.getClassLoader()));
        this.setPosition((PokePosition) parc.readParcelable(PokePosition.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public PokeArene(Parcel parc) {
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
    public static final Parcelable.Creator<PokeArene> CREATOR
        = new Parcelable.Creator<PokeArene>() {
        public PokeArene createFromParcel(Parcel in) {
            return new PokeArene(in);
        }
        
        public PokeArene[] newArray(int size) {
            return new PokeArene[size];
        }
    };

}
