package viceCity.core;

import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.*;
import java.util.stream.Collectors;

import static viceCity.common.ConstantMessages.*;

public class ControllerImpl implements Controller {
    protected Deque<Gun> guns;
    protected Collection<Player> civilPlayers;
    protected MainPlayer mainPlayer;


    public ControllerImpl() {
        this.guns = new ArrayDeque<>();
        this.civilPlayers = new ArrayList<>();
        this.mainPlayer = new MainPlayer();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        civilPlayers.add(player);
        return String.format(PLAYER_ADDED, player.getName());
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun;
        if ("Pistol".equals(type)) {
            gun = new Pistol(name);
        } else if ("Rifle".equals(type)) {
            gun = new Rifle(name);
        } else {
            return GUN_TYPE_INVALID;
        }
        guns.push(gun);

        return String.format(GUN_ADDED, name, type);
    }

    @Override
    public String addGunToPlayer(String name) {
        if (guns.isEmpty()) {
            return GUN_QUEUE_IS_EMPTY;
        }
        if (name.equals("Vercetti")) {
            Gun gun = guns.poll();
            mainPlayer.getGunRepository().add(gun);

            return String.format(GUN_ADDED_TO_MAIN_PLAYER, gun.getName(), mainPlayer.getName());
        }
        Player civilPlayer = civilPlayers.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
        if (civilPlayer != null) {
            Gun gun = guns.poll();
            civilPlayer.getGunRepository().add(gun);
            return String.format(GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), civilPlayer.getName());
        }

        return CIVIL_PLAYER_DOES_NOT_EXIST;
    }

    @Override
    public String fight() {
        GangNeighbourhood neighbourhood = new GangNeighbourhood();
        neighbourhood.action(mainPlayer, civilPlayers);
        StringBuilder sb = new StringBuilder();

        if (mainPlayer.getLifePoints() == 100 && civilPlayers.stream().allMatch(Player::isAlive)) {
            return FIGHT_HOT_HAPPENED;
        } else {
            List<Player> deadPlayers = civilPlayers.stream().filter(player -> !player.isAlive()).collect(Collectors.toList());
            int leftPlayers = civilPlayers.size() - deadPlayers.size();
            sb.append(FIGHT_HAPPENED).append(System.lineSeparator());
            sb.append(String.format(MAIN_PLAYER_LIVE_POINTS_MESSAGE, mainPlayer.getLifePoints())).append(System.lineSeparator());
            sb.append(String.format(MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, deadPlayers.size())).append(System.lineSeparator());
            sb.append(String.format(CIVIL_PLAYERS_LEFT_MESSAGE, leftPlayers));

        }


        return sb.toString().trim();
    }
}
