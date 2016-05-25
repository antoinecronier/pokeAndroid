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
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Entity
@Rest
public class PokeNpc  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.ENUM)
	private PokeProfession profession;
	
	@Column(type = Type.TEXT)
	private String description;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokeObjet> objets;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokeBadge> badge;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokePokemon> pokemons;
	
	@OneToMany
	@Column(nullable = true)
	private ArrayList<PokePokemon> team;
	
	@ManyToOne
	@Column(nullable = true)
	private PokePosition position;
	
	@ManyToOne
	@Column(nullable = true)
	private PokeZone zone;

    /**
     * Default constructor.
     */
    public PokeNpc() {

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
     * Get the Profession.
     * @return the profession
     */
    public PokeProfession getProfession() {
         return this.profession;
    }
     /**
     * Set the Profession.
     * @param value the profession to set
     */
    public void setProfession(final PokeProfession value) {
         this.profession = value;
    }
     /**
     * Get the Description.
     * @return the description
     */
    public String getDescription() {
         return this.description;
    }
     /**
     * Set the Description.
     * @param value the description to set
     */
    public void setDescription(final String value) {
         this.description = value;
    }
     /**
     * Get the Objets.
     * @return the objets
     */
    public ArrayList<PokeObjet> getObjets() {
         return this.objets;
    }
     /**
     * Set the Objets.
     * @param value the objets to set
     */
    public void setObjets(final ArrayList<PokeObjet> value) {
         this.objets = value;
    }
     /**
     * Get the Badge.
     * @return the badge
     */
    public ArrayList<PokeBadge> getBadge() {
         return this.badge;
    }
     /**
     * Set the Badge.
     * @param value the badge to set
     */
    public void setBadge(final ArrayList<PokeBadge> value) {
         this.badge = value;
    }
     /**
     * Get the Pokemons.
     * @return the pokemons
     */
    public ArrayList<PokePokemon> getPokemons() {
         return this.pokemons;
    }
     /**
     * Set the Pokemons.
     * @param value the pokemons to set
     */
    public void setPokemons(final ArrayList<PokePokemon> value) {
         this.pokemons = value;
    }
     /**
     * Get the Team.
     * @return the team
     */
    public ArrayList<PokePokemon> getTeam() {
         return this.team;
    }
     /**
     * Set the Team.
     * @param value the team to set
     */
    public void setTeam(final ArrayList<PokePokemon> value) {
         this.team = value;
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
        if (this.getProfession() != null) {
            dest.writeInt(1);
            dest.writeString(this.getProfession().name());
        } else {
            dest.writeInt(0);
        }
        if (this.getDescription() != null) {
            dest.writeInt(1);
            dest.writeString(this.getDescription());
        } else {
            dest.writeInt(0);
        }

        if (this.getObjets() != null) {
            dest.writeInt(this.getObjets().size());
            for (PokeObjet item : this.getObjets()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getBadge() != null) {
            dest.writeInt(this.getBadge().size());
            for (PokeBadge item : this.getBadge()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getPokemons() != null) {
            dest.writeInt(this.getPokemons().size());
            for (PokePokemon item : this.getPokemons()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getTeam() != null) {
            dest.writeInt(this.getTeam().size());
            for (PokePokemon item : this.getTeam()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        if (this.getPosition() != null
                    && !this.parcelableParents.contains(this.getPosition())) {
            this.getPosition().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getZone() != null
                    && !this.parcelableParents.contains(this.getZone())) {
            this.getZone().writeToParcel(this.parcelableParents, dest, flags);
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
        int professionBool = parc.readInt();
        if (professionBool == 1) {
            this.setProfession(PokeProfession.valueOf(parc.readString()));
        }
        int descriptionBool = parc.readInt();
        if (descriptionBool == 1) {
            this.setDescription(parc.readString());
        }

        int nbObjets = parc.readInt();
        if (nbObjets > -1) {
            ArrayList<PokeObjet> items =
                new ArrayList<PokeObjet>();
            for (int i = 0; i < nbObjets; i++) {
                items.add((PokeObjet) parc.readParcelable(
                        PokeObjet.class.getClassLoader()));
            }
            this.setObjets(items);
        }

        int nbBadge = parc.readInt();
        if (nbBadge > -1) {
            ArrayList<PokeBadge> items =
                new ArrayList<PokeBadge>();
            for (int i = 0; i < nbBadge; i++) {
                items.add((PokeBadge) parc.readParcelable(
                        PokeBadge.class.getClassLoader()));
            }
            this.setBadge(items);
        }

        int nbPokemons = parc.readInt();
        if (nbPokemons > -1) {
            ArrayList<PokePokemon> items =
                new ArrayList<PokePokemon>();
            for (int i = 0; i < nbPokemons; i++) {
                items.add((PokePokemon) parc.readParcelable(
                        PokePokemon.class.getClassLoader()));
            }
            this.setPokemons(items);
        }

        int nbTeam = parc.readInt();
        if (nbTeam > -1) {
            ArrayList<PokePokemon> items =
                new ArrayList<PokePokemon>();
            for (int i = 0; i < nbTeam; i++) {
                items.add((PokePokemon) parc.readParcelable(
                        PokePokemon.class.getClassLoader()));
            }
            this.setTeam(items);
        }
        this.setPosition((PokePosition) parc.readParcelable(PokePosition.class.getClassLoader()));
        this.setZone((PokeZone) parc.readParcelable(PokeZone.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public PokeNpc(Parcel parc) {
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
    public static final Parcelable.Creator<PokeNpc> CREATOR
        = new Parcelable.Creator<PokeNpc>() {
        public PokeNpc createFromParcel(Parcel in) {
            return new PokeNpc(in);
        }
        
        public PokeNpc[] newArray(int size) {
            return new PokeNpc[size];
        }
    };

}
