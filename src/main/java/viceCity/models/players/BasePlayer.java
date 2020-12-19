package viceCity.models.players;

import viceCity.models.guns.Gun;
import viceCity.repositories.GunRepository;
import viceCity.repositories.interfaces.Repository;

import java.util.ArrayList;

import static viceCity.common.ExceptionMessages.*;

public abstract class BasePlayer implements Player {
    protected String name;
    protected int lifePoints;
    protected Repository<Gun> gunRepository;

    public BasePlayer(String name, int lifePoints) {
        setName(name);
        setLifePoints(lifePoints);
         this.gunRepository = new GunRepository();
    }


    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(NAME_NULL);
        }

        this.name = name;
    }

    public void setLifePoints(int lifePoints) {
        if(lifePoints < 0){
            throw new IllegalArgumentException(PLAYER_LIFE_POINTS_LESS_THAN_ZERO);
        }


        this.lifePoints = lifePoints;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLifePoints() {
        return this.lifePoints;
    }

    @Override
    public boolean isAlive() {
        return getLifePoints() > 0;
    }

    @Override
    public Repository<Gun> getGunRepository() {
        return this.gunRepository;
    }

    @Override
    public void takeLifePoints(int points) {
        int result;
        result = this.getLifePoints() - points;
        if(result < 0){
            result=0;
        }
        setLifePoints(result);
    }
}
