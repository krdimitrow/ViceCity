package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood{


    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Player civilPlayer : civilPlayers) {
            for (Gun gun : mainPlayer.getGunRepository().getModels()) {
                while (gun.canFire() && civilPlayer.isAlive()){
                    int fire = gun.fire();
                    civilPlayer.takeLifePoints(fire);
                }
            }
        }
        for (Player civilPlayer : civilPlayers) {
            if(civilPlayer.isAlive()){
                for (Gun gun : civilPlayer.getGunRepository().getModels()) {
                    while (gun.canFire() && mainPlayer.isAlive()){
                        int fire = gun.fire();
                        mainPlayer.takeLifePoints(fire);
                    }
                }
            }
        }
    }
}
